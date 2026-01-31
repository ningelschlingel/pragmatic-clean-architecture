package io.ningelschlingel.pca.post.infrastructure.web;

import java.net.URI;
import java.util.UUID;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import io.ningelschlingel.pca.post.core.application.CreatePostUseCase;
import io.ningelschlingel.pca.post.core.application.DeletePostUseCase;
import io.ningelschlingel.pca.post.core.application.FindPostUseCase;
import io.ningelschlingel.pca.post.core.application.ToggleLikeUseCase;
import io.ningelschlingel.pca.post.core.domain.Post;
import io.ningelschlingel.pca.post.core.domain.PostId;
import io.ningelschlingel.pca.shared.core.domain.AuthenticatedUser;
import lombok.AllArgsConstructor;

@RestController
@AllArgsConstructor
@RequestMapping("/api/v1/posts")
public class PostController {

    private final CreatePostUseCase createPostUseCase;
    private final FindPostUseCase findPostByIdUseCase;
    private final DeletePostUseCase deletePostByIdUseCase;
    private final ToggleLikeUseCase toggleLikeUseCase;

    /**
     * Create Post
     * @param request 
     * @return Location of created post if data was valid, else Http Error
     */
    @PostMapping("/create")
    public ResponseEntity<Void> createPost(@RequestBody CreatePostRequest request) {
        return createPostUseCase.execute(toCommand(request))
                .map(result -> {
                    URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                            .path("/{id}")
                            .buildAndExpand(result.postId())
                            .toUri();
                    return ResponseEntity.created(location).<Void>build();
                }).getOrElseGet(failure -> switch (failure) {
                    case CreatePostUseCase.PostDataInvalid _ -> ResponseEntity.status(HttpStatus.UNPROCESSABLE_CONTENT).build();
                    case CreatePostUseCase.PostNotAllowed _ -> ResponseEntity.status(HttpStatus.FORBIDDEN).build(); // Use 403 for Not Allowed
                });
    }

    // Create Post: request-obj and mapper
    private record CreatePostRequest(String title, String content) {}
    private CreatePostUseCase.Command toCommand(CreatePostRequest request) {
        return new CreatePostUseCase.Command(request.title(), request.title());
    }

    /**
     * Get Post with ID
     * @param postId of post to get
     * @return Post if found, else Http Error
     */
    @GetMapping("/{postId}")
    public ResponseEntity<FindPostResponse> findPost(@PathVariable UUID postId) {

        return findPostByIdUseCase.execute(PostId.of(postId))
                .map(post -> ResponseEntity.ok(toResponse(post)))
                .getOrElseGet(failure -> switch (failure) {
                    case FindPostUseCase.PostNotFound _ -> ResponseEntity.status(HttpStatus.NOT_FOUND).build();
                });
    }

    // Get Post: response-obj and mapper
    private record FindPostResponse(UUID id, String title, String content) {}
    private FindPostResponse toResponse(Post post) {
        return new FindPostResponse(post.getId().value(), post.getTitle(), post.getContent());
    }

    /**
     * Toggle like for post with user
     * @param postId of Post to toggle like on
     * @param auth like-toggling user
     * @return Like-list when toggled on, no-content when toggled off (deleted), Http Error when post or user not found
     */
    @PostMapping("/{postId}/likes")
    public ResponseEntity<Void> toggleLike(@PathVariable UUID postId, @AuthenticationPrincipal AuthenticatedUser auth) {
        var command = new ToggleLikeUseCase.Command(auth.principalId(), PostId.of(postId));

        return toggleLikeUseCase.execute(command)
                .<ResponseEntity<Void>>map(result -> switch (result.toggleAction()) {
                    case CREATED -> ResponseEntity.created(ServletUriComponentsBuilder.fromCurrentRequest().build().toUri()).build();
                    case DELETED -> ResponseEntity.noContent().build();
                })
                .getOrElseGet(failure -> switch (failure) {
                    case ToggleLikeUseCase.PostNotFoundForLike _ -> ResponseEntity.status(HttpStatus.UNPROCESSABLE_CONTENT).build();
                    case ToggleLikeUseCase.UserNotFoundForLike _ -> ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
                });
    }

    /**
     * Delete post
     * @param postId of Post to delete
     * @return
     */
    @DeleteMapping("/{postId}")
    public ResponseEntity<Void> deletePost(@PathVariable UUID postId) {
        return deletePostByIdUseCase.execute(PostId.of(postId))
                .map(success -> ResponseEntity.noContent().<Void>build())
                .getOrElseGet(failure -> switch (failure) {
                    case DeletePostUseCase.NotAllowed _ -> ResponseEntity.status(403).build();
                });
    }

}
