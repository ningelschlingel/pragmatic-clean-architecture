package io.ningelschlingel.pca.userprofile.core.domain;

import io.ningelschlingel.pca.shared.core.domain.UserId;
import io.ningelschlingel.pca.userprofile.core.application.createuserprofile.CreateUserCommand;
import lombok.Getter;

@Getter
public class UserProfile {
    private final UserId id;
    private String email;
    private String fullName;
    private UserStatus status;

    public UserProfile(UserId id, String email, String fullName) {
        this.id = id;
        this.email = email;
        this.fullName = fullName;
        this.status = UserStatus.PENDING_SYNC;
    }

    public UserProfile(CreateUserCommand command){
        this.id = command.id();
        this.email = command.email();
        this.fullName = command.fullName();
        this.status = UserStatus.PENDING_SYNC;
    }

    public void markAsSynced() {
        this.status = UserStatus.ACTIVE;
    }
}
