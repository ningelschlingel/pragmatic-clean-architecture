package io.ningelschlingel.pca.userprofile.infrastructure.web;

import java.util.UUID;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import io.ningelschlingel.pca.userprofile.core.application.finduserprofile.FindUserByIdUseCase;
import io.ningelschlingel.pca.userprofile.core.application.finduserprofile.failure.UserProfileNotFound;
import io.ningelschlingel.pca.shared.core.domain.UserId;
import io.ningelschlingel.pca.userprofile.infrastructure.web.finduser.FindUserHttpMapper;
import io.ningelschlingel.pca.userprofile.infrastructure.web.finduser.FindUserResponse;
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
    private final FindUserByIdUseCase findUserByIdUseCase;

    @GetMapping("/{userId}")
    public ResponseEntity<FindUserResponse> findUser(@PathVariable UUID userId) {

        return findUserByIdUseCase.execute(UserId.of(userId))
                .map(user -> ResponseEntity.ok(FindUserHttpMapper.toResponse(user)))
                .getOrElseGet(failure -> switch (failure) {
                    case UserProfileNotFound _ -> ResponseEntity.status(404).build();
                });
    }
}
