package io.ningelschlingel.pca.post.core.application.deletepost;

import io.ningelschlingel.pca.post.core.application.deletepost.failure.DeletePostFailure;
import io.ningelschlingel.pca.post.core.domain.PostId;
import io.ningelschlingel.pca.post.infrastructure.persistence.JpaPostRepository;
import io.vavr.control.Either;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class DeletePostByIdUseCase {

    private final JpaPostRepository jpaRepository;

    public Either<DeletePostFailure, Void> execute(PostId id){
        jpaRepository.deleteById(id);
        return Either.right(null);
    }
    
}
