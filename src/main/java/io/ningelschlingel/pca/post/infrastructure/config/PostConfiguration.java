package io.ningelschlingel.pca.post.infrastructure.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.ningelschlingel.pca.post.core.application.CreatePostUseCase;
import io.ningelschlingel.pca.post.core.application.DeletePostByIdUseCase;
import io.ningelschlingel.pca.post.core.application.FindPostByIdUseCase;
import io.ningelschlingel.pca.post.core.application.ToggleLikeUseCase;
import io.ningelschlingel.pca.post.core.port.out.LikerExistencePort;
import io.ningelschlingel.pca.post.infrastructure.persistence.JpaLikeRepository;
import io.ningelschlingel.pca.post.infrastructure.persistence.JpaPostRepository;
import io.ningelschlingel.pca.shared.core.domain.UserId;
import io.ningelschlingel.pca.userprofile.core.application.finduserprofile.FindUserByIdUseCase;

@Configuration
public class PostConfiguration {

    @Bean
    public LikerExistencePort likerExistencePort(FindUserByIdUseCase userUseCase) {
        return id -> userUseCase.execute(UserId.of(id.value())).isRight();
    }

    @Bean
    public CreatePostUseCase createPostUseCase(JpaPostRepository jpaPostRepository) {
        return new CreatePostUseCase(jpaPostRepository);
    }

    @Bean
    public FindPostByIdUseCase findPostByIdUseCase(JpaPostRepository jpaPostRepository) {
        return new FindPostByIdUseCase(jpaPostRepository);
    }

    @Bean
    public DeletePostByIdUseCase deletePostUseCase(JpaPostRepository jpaPostRepository) {
        return new DeletePostByIdUseCase(jpaPostRepository);
    }

    @Bean
    public ToggleLikeUseCase likePostUseCase(JpaPostRepository jpaPostRepository, JpaLikeRepository jpaLikeRepository, LikerExistencePort likerExistancePort) {
        return new ToggleLikeUseCase(jpaPostRepository, jpaLikeRepository, likerExistancePort);
    }
}
