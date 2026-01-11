package io.ningelschlingel.pca.userauth.infrastructure.web.login;

import io.ningelschlingel.pca.userauth.core.application.login.LoginCommand;
import io.ningelschlingel.pca.userauth.core.domain.UserAuth;

public class LoginHttpMapper {

    public static LoginCommand fromRequest(LoginRequest request) {
        return new LoginCommand(request.email(), request.rawPassword());
    }

    public static LoginResponse toResponse(UserAuth user) {
        return new LoginResponse(user.getId().value(), user.getEmail());
    }
}
