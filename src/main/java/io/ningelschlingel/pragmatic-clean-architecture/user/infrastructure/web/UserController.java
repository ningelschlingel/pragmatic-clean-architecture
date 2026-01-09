package io.ningelschlingel.pragmaticcleanarchitecture.user.infrastructure.web;

import java.util.UUID;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.ningelschlingel.pragmaticcleanarchitecture.user.core.application.createuser.CreateUserUseCase;
import io.ningelschlingel.pragmaticcleanarchitecture.user.core.application.createuser.failure.UserDataInvalid;
import io.ningelschlingel.pragmaticcleanarchitecture.user.core.application.createuser.failure.UserExistsAlready;
import io.ningelschlingel.pragmaticcleanarchitecture.user.core.application.deleteuser.DeleteUserByIdUseCase;
import io.ningelschlingel.pragmaticcleanarchitecture.user.core.application.deleteuser.failure.DeleteUserNotAllowed;
import io.ningelschlingel.pragmaticcleanarchitecture.user.core.application.finduser.FindUserByIdUseCase;
import io.ningelschlingel.pragmaticcleanarchitecture.user.core.application.finduser.failure.UserNotFound;
import io.ningelschlingel.pragmaticcleanarchitecture.user.core.domain.UserId;
import io.ningelschlingel.pragmaticcleanarchitecture.user.infrastructure.web.createuser.CreateUserHttpMapper;
import io.ningelschlingel.pragmaticcleanarchitecture.user.infrastructure.web.createuser.CreateUserRequest;
import io.ningelschlingel.pragmaticcleanarchitecture.user.infrastructure.web.createuser.CreateUserResponse;
import io.ningelschlingel.pragmaticcleanarchitecture.user.infrastructure.web.finduser.FindUserHttpMapper;
import io.ningelschlingel.pragmaticcleanarchitecture.user.infrastructure.web.finduser.FindUserResponse;
import lombok.AllArgsConstructor;

@RestController
@AllArgsConstructor
@RequestMapping("/api/v1/user")
public class UserController {

    CreateUserUseCase createUserUseCase;
    FindUserByIdUseCase findUserByIdUseCase;
    DeleteUserByIdUseCase deleteUserByIdUseCase;

    @PostMapping("/create")
    public ResponseEntity<CreateUserResponse> createUser(@RequestBody CreateUserRequest request) {

        return createUserUseCase.execute(CreateUserHttpMapper.fromRequest(request))
            .map(user -> ResponseEntity.ok(CreateUserHttpMapper.toResponse(user)))
            .getOrElseGet(failure -> switch (failure) {
                case UserExistsAlready e -> ResponseEntity.status(409).build();
                case UserDataInvalid e -> ResponseEntity.status(409).build();
            });
    }

    @GetMapping("/{userId}")
    public ResponseEntity<FindUserResponse> findUser(@PathVariable UUID userId) {
        
        return findUserByIdUseCase.execute(new UserId(userId))
            .map(user -> ResponseEntity.ok(FindUserHttpMapper.toResponse(user)))
            .getOrElseGet(failure -> switch (failure) {
                case UserNotFound e -> ResponseEntity.status(409).build();
            });
    }

    @DeleteMapping("/{userId}")
    public ResponseEntity<Void> deleteUser(@PathVariable UUID userId) {
        return deleteUserByIdUseCase.execute(new UserId(userId))
            .map(success -> ResponseEntity.noContent().<Void>build()) 
            .getOrElseGet(failure -> switch (failure) {
                case DeleteUserNotAllowed e -> ResponseEntity.status(403).build();
            });
    }
    
}
