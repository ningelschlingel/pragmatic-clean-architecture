package io.ningelschlingel.pca.userauth.infrastructure.web.register;

import io.ningelschlingel.pca.userauth.core.application.register.RegisterCommand;
import io.ningelschlingel.pca.userauth.core.domain.UserAuth;

public class RegisterHttpMapper {

    public static RegisterCommand fromRequest(RegisterRequest request) {
        return new RegisterCommand(request.email(), request.rawPassword());
    }

    public static RegisterResponse toResponse(UserAuth user) {
        return new RegisterResponse(user.getId().value(), user.getEmail());
    }
}
