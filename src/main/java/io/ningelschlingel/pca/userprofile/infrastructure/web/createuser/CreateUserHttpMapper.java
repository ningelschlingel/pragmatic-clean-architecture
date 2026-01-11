package io.ningelschlingel.pca.userprofile.infrastructure.web.createuser;

import io.ningelschlingel.pca.userprofile.core.application.createuserprofile.CreateUserCommand;
import io.ningelschlingel.pca.userprofile.core.domain.UserProfile;
import io.ningelschlingel.pca.shared.core.domain.UserId;

public class CreateUserHttpMapper {

    public static CreateUserCommand fromRequest(CreateUserRequest request) {
        return new CreateUserCommand(UserId.generate(), request.email(), request.fullName());
    }

    public static CreateUserResponse toResponse(UserProfile user) {
        return new CreateUserResponse(user.getId().value(), user.getEmail(), user.getFullName());
    }
}
