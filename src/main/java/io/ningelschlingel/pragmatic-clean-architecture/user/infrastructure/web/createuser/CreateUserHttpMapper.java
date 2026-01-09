package io.ningelschlingel.pragmaticcleanarchitecture.user.infrastructure.web.createuser;

import io.ningelschlingel.pragmaticcleanarchitecture.user.core.application.createuser.CreateUserCommand;
import io.ningelschlingel.pragmaticcleanarchitecture.user.core.domain.User;
import io.ningelschlingel.pragmaticcleanarchitecture.user.core.domain.UserId;

public class CreateUserHttpMapper {

    public static CreateUserCommand fromRequest(CreateUserRequest request) {
        return new CreateUserCommand(new UserId(), request.email(), request.fullName());
    }

    public static CreateUserResponse toResponse(User user) {
        return new CreateUserResponse(user.getId().value(), user.getEmail(), user.getFullName());
    }
}
