package io.ningelschlingel.pca.userprofile.infrastructure.web;

import java.net.URI;
import java.util.UUID;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import io.ningelschlingel.pca.userprofile.core.application.createuserprofile.CreateUserUseCase;
import io.ningelschlingel.pca.userprofile.core.application.createuserprofile.failure.UserDataInvalid;
import io.ningelschlingel.pca.userprofile.core.application.createuserprofile.failure.UserExistsAlready;
import io.ningelschlingel.pca.userprofile.core.application.deleteuserprofile.DeleteUserByIdUseCase;
import io.ningelschlingel.pca.userprofile.core.application.deleteuserprofile.failure.DeleteUserNotAllowed;
import io.ningelschlingel.pca.userprofile.core.application.finduserprofile.FindUserByIdUseCase;
import io.ningelschlingel.pca.userprofile.core.application.finduserprofile.failure.UserProfileNotFound;
import io.ningelschlingel.pca.shared.core.domain.UserId;
import io.ningelschlingel.pca.userprofile.infrastructure.web.createuser.CreateUserHttpMapper;
import io.ningelschlingel.pca.userprofile.infrastructure.web.createuser.CreateUserRequest;
import io.ningelschlingel.pca.userprofile.infrastructure.web.finduser.FindUserHttpMapper;
import io.ningelschlingel.pca.userprofile.infrastructure.web.finduser.FindUserResponse;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/users")
public class UserProfileController {

    private final CreateUserUseCase createUserUseCase;
    private final FindUserByIdUseCase findUserByIdUseCase;
    private final DeleteUserByIdUseCase deleteUserByIdUseCase;

    @PostMapping
    public ResponseEntity<Void> createUser(@RequestBody CreateUserRequest request) {

        return createUserUseCase.execute(CreateUserHttpMapper.fromRequest(request))
                .map(user -> {
                    URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                            .path("/{userId}")
                            .buildAndExpand(user.getId())
                            .toUri();
                    return ResponseEntity.created(location).<Void>build();
                })
                .getOrElseGet(failure -> switch (failure) {
                    case UserExistsAlready _ -> ResponseEntity.status(409).build();
                    case UserDataInvalid _ -> ResponseEntity.status(422).build();
                });
    }

    @GetMapping("/{userId}")
    public ResponseEntity<FindUserResponse> findUser(@PathVariable UUID userId) {

        return findUserByIdUseCase.execute(UserId.of(userId))
                .map(user -> ResponseEntity.ok(FindUserHttpMapper.toResponse(user)))
                .getOrElseGet(failure -> switch (failure) {
                    case UserProfileNotFound _ -> ResponseEntity.status(404).build();
                });
    }

    @DeleteMapping("/{userId}")
    public ResponseEntity<Void> deleteUser(@PathVariable UUID userId) {
        return deleteUserByIdUseCase.execute(UserId.of(userId))
                .map(success -> ResponseEntity.noContent().<Void>build())
                .getOrElseGet(failure -> switch (failure) {
                    case DeleteUserNotAllowed _ -> ResponseEntity.status(403).build();
                });
    }

}
