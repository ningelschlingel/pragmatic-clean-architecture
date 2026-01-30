package io.ningelschlingel.pca.userprofile.infrastructure.web;

import java.util.UUID;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.ningelschlingel.pca.userprofile.core.application.FindUserProfileUseCase;
import io.ningelschlingel.pca.userprofile.core.domain.UserProfile;
import io.ningelschlingel.pca.shared.core.domain.UserId;
import io.ningelschlingel.pca.userauth.infrastructure.web.UserAuthController;
import lombok.RequiredArgsConstructor;

/**
 * Api for User Profile Management
 * Creation and deletion is handled by {@link UserAuthController}.
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/users")
public class UserProfileController {

    // UseCases
    private final FindUserProfileUseCase findUserByIdUseCase;

    /**
     * Find user profile
     * @param userId
     * @return
     */
    @GetMapping("/{userId}")
    public ResponseEntity<FindUserResponse> findUser(@PathVariable UUID userId) {

        return findUserByIdUseCase.execute(UserId.of(userId))
                .map(user -> ResponseEntity.ok(toResponse(user)))
                .getOrElseGet(failure -> switch (failure) {
                    case FindUserProfileUseCase.UserProfileNotFound _ -> ResponseEntity.status(404).build();
                });
    }

    // Find user profile response & mapper
    private record FindUserResponse(UUID id, String email, String fullName) {}
    private FindUserResponse toResponse(UserProfile user) {
        return new FindUserResponse(user.getId().value(), user.getEmail(), user.getFullName());
    }

}
