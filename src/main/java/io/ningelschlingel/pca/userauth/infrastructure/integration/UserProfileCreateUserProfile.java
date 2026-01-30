package io.ningelschlingel.pca.userauth.infrastructure.integration;

import org.springframework.stereotype.Component;

import io.ningelschlingel.pca.shared.core.domain.UserId;
import io.ningelschlingel.pca.userauth.core.port.out.UserProfileCreator;
import io.ningelschlingel.pca.userprofile.core.application.CreateUserProfileUseCase;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class UserProfileCreateUserProfile implements UserProfileCreator {

    // Usecase
    private final CreateUserProfileUseCase createUserUseCase; // Foreign feature slice

    @Override
    public void createInitialUserProfile(UserId userId, String email, String fullName) {
        createUserUseCase.execute(toCommand(userId, email, fullName));
    }

    private CreateUserProfileUseCase.Command toCommand(UserId userId, String email, String fullName) {
        return new CreateUserProfileUseCase.Command(userId, email, fullName);
    }
    
}
