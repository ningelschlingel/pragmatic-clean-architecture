package io.ningelschlingel.pca.userauth.infrastructure.integration;

import org.springframework.stereotype.Component;

import io.ningelschlingel.pca.shared.core.domain.UserId;
import io.ningelschlingel.pca.userauth.core.port.out.UserProfileCreator;
import io.ningelschlingel.pca.userprofile.core.application.CreateUserUseCase;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class UserProfileCreateUserProfile implements UserProfileCreator {

    private final CreateUserUseCase createUserUseCase;

    @Override
    public void createInitialUserProfile(UserId userId, String email, String fullName) {
        createUserUseCase.execute(toCommand(userId, email, fullName));
    }

    private CreateUserUseCase.Command toCommand(UserId userId, String email, String fullName) {
        return new CreateUserUseCase.Command(userId, email, fullName);
    }
    
}
