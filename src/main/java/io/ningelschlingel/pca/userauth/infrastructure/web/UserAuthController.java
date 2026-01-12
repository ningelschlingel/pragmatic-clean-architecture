package io.ningelschlingel.pca.userauth.infrastructure.web;

import java.net.URI;
import java.time.Duration;

import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import io.ningelschlingel.pca.userauth.core.application.login.LoginUseCase;
import io.ningelschlingel.pca.userauth.core.application.login.failure.UserCredentialsInvalid;
import io.ningelschlingel.pca.userauth.core.application.login.failure.UserNotFoundForLogin;
import io.ningelschlingel.pca.userauth.core.application.register.RegisterUseCase;
import io.ningelschlingel.pca.userauth.core.application.register.failure.AuthDataInvalid;
import io.ningelschlingel.pca.userauth.core.application.register.failure.UserAuthExists;
import io.ningelschlingel.pca.userauth.infrastructure.web.payload.LoginRequest;
import io.ningelschlingel.pca.userauth.infrastructure.web.payload.RegisterRequest;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/auth")
public class UserAuthController {

    private final RegisterUseCase registerUseCase;
    private final LoginUseCase loginUseCase;
    private final UserAuthHttpMapper userAuthHttpMapper;

    @PostMapping("/register") // TODO the usecase needs to delegate user-profile creation and a nice pattern to get the created-location is needed
    public ResponseEntity<URI> register(@RequestBody RegisterRequest request) {
        return registerUseCase.execute(userAuthHttpMapper.toCommand(request))
            .map(result -> {
                URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                            .path("/{id}")
                            .buildAndExpand(result.userId().value())
                            .toUri();
                return ResponseEntity
                .created(location)
                .headers(createJwtCookieHeaders(result.jwtToken()))
                .<URI>build();
            })
            .getOrElseGet(failure -> switch (failure) {
                case UserAuthExists _ -> ResponseEntity.status(409).build();
                case AuthDataInvalid _ -> ResponseEntity.status(409).build();
            });
    }

    @PostMapping("/login")
    public ResponseEntity<Void> login(@RequestBody LoginRequest request) {
        return loginUseCase.execute(userAuthHttpMapper.toCommand(request))
            .map(token -> ResponseEntity
                    .noContent()
                    .headers(createJwtCookieHeaders(token))
                    .<Void>build())
            .getOrElseGet(failure -> switch (failure) {
                case UserNotFoundForLogin _ -> ResponseEntity.status(403).build();
                case UserCredentialsInvalid _ -> ResponseEntity.status(403).build();
            });
    }

    private HttpHeaders createJwtCookieHeaders(String token) {
        ResponseCookie cookie = ResponseCookie.from("auth_token", token)
            .httpOnly(true)
            .secure(true)
            .path("/")
            .maxAge(Duration.ofHours(24))
            .sameSite("Lax")
            .build();
    
        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.SET_COOKIE, cookie.toString());
        return headers;
    }
    
}
