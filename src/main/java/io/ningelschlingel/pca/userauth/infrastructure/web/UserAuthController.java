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

import io.ningelschlingel.pca.shared.core.domain.UserId;
import io.ningelschlingel.pca.userauth.core.application.LoginUseCase;
import io.ningelschlingel.pca.userauth.core.application.RegisterUseCase;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/auth")
public class UserAuthController {

    private final RegisterUseCase registerUseCase;
    private final LoginUseCase loginUseCase;

    /**
     * Register: Creates {@link UserAuthEntity} and also {@link UserEntity} both with identical id.
     * Handles creation of UserAuth internally, delegates creation of User to UserProfile-Slice.
     * @param request Register request, also containing minimal information for UserProfile creation.
     * @return Http-Created: Location of created ressource (User)
     */
    @PostMapping("/register")
    public ResponseEntity<Void> register(@RequestBody RegisterRequest request) {
        return registerUseCase.execute(toCommand(request))
            .map(result -> {
                URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                            .path("/{id}")
                            .buildAndExpand(result.userId().value())
                            .toUri();
                return ResponseEntity
                .created(location)
                .headers(createJwtCookieHeaders(result.jwtToken()))
                .<Void>build();
            })
            .getOrElseGet(failure -> switch (failure) {
                case RegisterUseCase.UserAuthExists _ -> ResponseEntity.status(409).build();
                case RegisterUseCase.AuthDataInvalid _ -> ResponseEntity.status(409).build();
            });
    }

    // Register: request-object & mapper
    private record RegisterRequest(String email, String rawPassword, String fullName) {}
    private RegisterUseCase.Command toCommand(RegisterRequest request) {
        return new RegisterUseCase.Command(request.email(), request.rawPassword(), request.fullName());
    }

    // Register: response-object & mapper
    public record RegisterResponse (UserId userId,String email) {}

    /**
     * Login
     * @param request
     * @return
     */
    @PostMapping("/login")
    public ResponseEntity<Void> login(@RequestBody LoginRequest request) {
        return loginUseCase.execute(toCommand(request))
            .map(result -> ResponseEntity
                    .noContent()
                    .headers(createJwtCookieHeaders(result.token()))
                    .<Void>build())
            .getOrElseGet(failure -> switch (failure) {
                case LoginUseCase.UserNotFoundForLogin _ -> ResponseEntity.status(403).build();
                case LoginUseCase.UserCredentialsInvalid _ -> ResponseEntity.status(403).build();
            });
    }

    // Login: request-object and mapper
    private record LoginRequest(String email, String rawPassword) {}
    private LoginUseCase.Command toCommand(LoginRequest request) {
        return new LoginUseCase.Command(request.email, request.rawPassword);
    }


    // Helper
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
