package io.ningelschlingel.pca.userauth.infrastructure.integration;

import org.springframework.stereotype.Component;

import io.ningelschlingel.pca.shared.core.domain.UserId;
import io.ningelschlingel.pca.userauth.core.port.out.UserProfileDeleter;
import io.ningelschlingel.pca.userprofile.core.application.DeleteUserProfileUseCase;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class UserProfileDeleteUserProfile implements UserProfileDeleter {

    // Usecase
    private final DeleteUserProfileUseCase deleteUserProfileUseCase; // Foreign feature slice

    @Override
    public void deleteUserProfile(UserId userId) {
        deleteUserProfileUseCase.execute(userId);
    }
}
