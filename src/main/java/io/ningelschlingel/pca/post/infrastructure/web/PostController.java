package io.ningelschlingel.pca.post.infrastructure.web;

import java.net.URI;
import java.util.UUID;

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

import io.ningelschlingel.pca.post.core.application.createpost.CreatePostUseCase;
import io.ningelschlingel.pca.post.core.application.createpost.failure.PostDataInvalid;
import io.ningelschlingel.pca.post.core.application.createpost.failure.PostNotAllowed;
import io.ningelschlingel.pca.post.core.application.deletepost.DeletePostByIdUseCase;
import io.ningelschlingel.pca.post.core.application.deletepost.failure.DeletePostNotAllowed;
import io.ningelschlingel.pca.post.core.application.findpost.FindPostByIdUseCase;
import io.ningelschlingel.pca.post.core.application.findpost.failure.PostNotFound;
import io.ningelschlingel.pca.post.core.application.togglelike.ToggleLikeCommand;
import io.ningelschlingel.pca.post.core.application.togglelike.ToggleLikeUseCase;
import io.ningelschlingel.pca.post.core.application.togglelike.failure.PostNotFoundForLike;
import io.ningelschlingel.pca.post.core.application.togglelike.failure.UserNotFoundForLike;
import io.ningelschlingel.pca.post.core.domain.PostId;
import io.ningelschlingel.pca.post.infrastructure.web.payload.CreatePostRequest;
import io.ningelschlingel.pca.post.infrastructure.web.payload.FindPostResponse;
import io.ningelschlingel.pca.shared.core.domain.AuthenticatedUser;
import lombok.AllArgsConstructor;

@RestController
@AllArgsConstructor
@RequestMapping("/api/v1/posts")
public class PostController {

    private final CreatePostUseCase createPostUseCase;
    private final FindPostByIdUseCase findPostByIdUseCase;
    private final DeletePostByIdUseCase deletePostByIdUseCase;
    private final ToggleLikeUseCase toggleLikeUseCase;
    private final PostHttpMapper postMapper;

    @PostMapping("/create")
    public ResponseEntity<Void> createPost(@RequestBody CreatePostRequest request) {
        return createPostUseCase.execute(postMapper.toCommand(request))
                .map(post -> {
                    URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                            .path("/{id}")
                            .buildAndExpand(post.getId().value())
                            .toUri();
                    return ResponseEntity.created(location).<Void>build();
                }).getOrElseGet(failure -> switch (failure) {
                    case PostDataInvalid _ -> ResponseEntity.status(422).build();
                    case PostNotAllowed _ -> ResponseEntity.status(403).build(); // Use 403 for Not Allowed
                });
    }

    @GetMapping("/{postId}")
    public ResponseEntity<FindPostResponse> findPost(@PathVariable UUID postId) {

        return findPostByIdUseCase.execute(PostId.of(postId))
                .map(post -> ResponseEntity.ok(postMapper.fromDomain(post)))
                .getOrElseGet(failure -> switch (failure) {
                    case PostNotFound _ -> ResponseEntity.status(409).build();
                });
    }

    @PostMapping("/{postId}/likes")
    public ResponseEntity<Void> toggleLike(@PathVariable UUID postId, @AuthenticationPrincipal AuthenticatedUser auth) {
        var command = new ToggleLikeCommand(auth.principalId(), PostId.of(postId));

        return toggleLikeUseCase.execute(command)
                .<ResponseEntity<Void>>map(result -> switch (result.toggleAction()) {
                    case CREATED -> ResponseEntity.created(ServletUriComponentsBuilder.fromCurrentRequest().build().toUri()).build();
                    case DELETED -> ResponseEntity.noContent().build();
                })
                .getOrElseGet(failure -> switch (failure) {
                    case PostNotFoundForLike _ -> ResponseEntity.status(422).build();
                    case UserNotFoundForLike _ -> ResponseEntity.status(401).build();
                });
    }

    @DeleteMapping("/{postId}")
    public ResponseEntity<Void> deletePost(@PathVariable UUID postId) {
        return deletePostByIdUseCase.execute(PostId.of(postId))
                .map(success -> ResponseEntity.noContent().<Void>build())
                .getOrElseGet(failure -> switch (failure) {
                    case DeletePostNotAllowed _ -> ResponseEntity.status(403).build();
                });
    }

}
