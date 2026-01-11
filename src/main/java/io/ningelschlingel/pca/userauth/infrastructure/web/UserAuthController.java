package io.ningelschlingel.pca.userauth.infrastructure.web;

import java.util.UUID;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.ningelschlingel.pca.shared.core.domain.UserId;
import io.ningelschlingel.pca.userauth.core.application.login.LoginUseCase;
import io.ningelschlingel.pca.userauth.core.application.login.failure.UserCredentialsInvalid;
import io.ningelschlingel.pca.userauth.core.application.login.failure.UserNotFoundForLogin;
import io.ningelschlingel.pca.userauth.core.application.register.RegisterUseCase;
import io.ningelschlingel.pca.userauth.core.application.register.failure.AuthDataInvalid;
import io.ningelschlingel.pca.userauth.core.application.register.failure.UserAuthExists;
import io.ningelschlingel.pca.userauth.infrastructure.web.login.LoginHttpMapper;
import io.ningelschlingel.pca.userauth.infrastructure.web.login.LoginRequest;
import io.ningelschlingel.pca.userauth.infrastructure.web.register.RegisterHttpMapper;
import io.ningelschlingel.pca.userauth.infrastructure.web.register.RegisterRequest;
import io.ningelschlingel.pca.userauth.infrastructure.web.register.RegisterResponse;
import lombok.AllArgsConstructor;

@RestController
@AllArgsConstructor
@RequestMapping("/api/v1/users")
public class UserAuthController {

    RegisterUseCase registerUseCase;
    LoginUseCase loginUseCase;

    @PostMapping("/register")
    public ResponseEntity<RegisterResponse> register(@RequestBody RegisterRequest request) {

        return registerUseCase.execute(RegisterHttpMapper.fromRequest(request))
            .map(user -> ResponseEntity.ok(RegisterHttpMapper.toResponse(user)))
            .getOrElseGet(failure -> switch (failure) {
                case UserAuthExists _ -> ResponseEntity.status(409).build();
                case AuthDataInvalid _ -> ResponseEntity.status(409).build();
            });
    }

    @DeleteMapping("/login")
    public ResponseEntity<Void> login(@RequestBody LoginRequest request) {
        return loginUseCase.execute(LoginHttpMapper.fromRequest(request))
            .map(success -> ResponseEntity.noContent().<Void>build()) 
            .getOrElseGet(failure -> switch (failure) {
                case UserNotFoundForLogin _ -> ResponseEntity.status(403).build();
                case UserCredentialsInvalid _ -> ResponseEntity.status(403).build();
            });
    }
    
}
