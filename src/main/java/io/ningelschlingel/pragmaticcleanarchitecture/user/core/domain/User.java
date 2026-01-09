package io.ningelschlingel.pragmaticcleanarchitecture.user.core.domain;

import lombok.Getter;

import io.ningelschlingel.pragmaticcleanarchitecture.user.core.application.createuser.CreateUserCommand;

@Getter
public class User {
    private final UserId id;
    private String email;
    private String fullName;
    private UserStatus status;

    public User(UserId id, String email, String fullName) {
        this.id = id;
        this.email = email;
        this.fullName = fullName;
        this.status = UserStatus.PENDING_SYNC;
    }

    public User(CreateUserCommand command){
        this.id = command.id();
        this.email = command.email();
        this.fullName = command.fullName();
        this.status = UserStatus.PENDING_SYNC;
    }

    public void markAsSynced() {
        this.status = UserStatus.ACTIVE;
    }
}
