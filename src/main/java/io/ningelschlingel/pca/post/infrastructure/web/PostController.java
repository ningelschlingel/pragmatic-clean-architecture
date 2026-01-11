package io.ningelschlingel.pca.post.infrastructure.web;

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
import io.ningelschlingel.pca.post.infrastructure.web.createpost.CreatePostHttpMapper;
import io.ningelschlingel.pca.post.infrastructure.web.createpost.CreatePostRequest;
import io.ningelschlingel.pca.post.infrastructure.web.createpost.CreatePostResponse;
import io.ningelschlingel.pca.post.infrastructure.web.findpost.FindPostHttpMapper;
import io.ningelschlingel.pca.post.infrastructure.web.findpost.FindPostResponse;
import io.ningelschlingel.pca.post.infrastructure.web.likepost.LikePostHttpMapper;
import io.ningelschlingel.pca.post.infrastructure.web.likepost.LikePostResponse;
import io.ningelschlingel.pca.shared.core.domain.AuthenticatedUser;
import lombok.AllArgsConstructor;

@RestController
@AllArgsConstructor
@RequestMapping("/api/v1/posts")
public class PostController {

    CreatePostUseCase createPostUseCase;
    FindPostByIdUseCase findPostByIdUseCase;
    DeletePostByIdUseCase deletePostByIdUseCase;
    ToggleLikeUseCase toggleLikeUseCase;

    @PostMapping("/create")
    public ResponseEntity<CreatePostResponse> createPost(@RequestBody CreatePostRequest request) {

        return createPostUseCase.execute(CreatePostHttpMapper.fromRequest(request))
            .map(post -> ResponseEntity.ok(CreatePostHttpMapper.toResponse(post)))
            .getOrElseGet(failure -> switch (failure) {
                case PostDataInvalid _ -> ResponseEntity.status(422).build();
                case PostNotAllowed _ -> ResponseEntity.status(400).build();
            });
    }

    @GetMapping("/{postId}")
    public ResponseEntity<FindPostResponse> findPost(@PathVariable UUID postId) {
        
        return findPostByIdUseCase.execute(PostId.of(postId))
            .map(post -> ResponseEntity.ok(FindPostHttpMapper.toResponse(post)))
            .getOrElseGet(failure -> switch (failure) {
                case PostNotFound _ -> ResponseEntity.status(409).build();
            });
    }

    @GetMapping("/{postId}/togglelike")
    public ResponseEntity<LikePostResponse> likePost(@AuthenticationPrincipal AuthenticatedUser auth, @PathVariable UUID postId) {
        
        return toggleLikeUseCase.execute(new ToggleLikeCommand(auth.principalId(), PostId.of(postId)))
            .map(like -> ResponseEntity.ok(LikePostHttpMapper.toResponse(like)))
            .getOrElseGet(failure -> switch (failure) {
                case PostNotFoundForLike _ -> ResponseEntity.status(404).build();
                case UserNotFoundForLike _ -> ResponseEntity.status(404).build();
            });
    }

    @DeleteMapping("/{postId}")
    public ResponseEntity<Void> deleteLike(@PathVariable UUID postId) {
        return deletePostByIdUseCase.execute(PostId.of(postId))
            .map(success -> ResponseEntity.noContent().<Void>build()) 
            .getOrElseGet(failure -> switch (failure) {
                case DeletePostNotAllowed _ -> ResponseEntity.status(403).build();
            });
    }
    
}
