package io.ningelschlingel.pca.userauth.core.domain;

import io.ningelschlingel.pca.shared.core.domain.UserId;
import lombok.Getter;

@Getter
public class UserAuth {
    private final UserId id;
    private final String email;
    private final String passwordHash;

    public UserAuth(UserId id, String email, String passwordHash){
        this.id = id;
        this.email = email;
        this.passwordHash = passwordHash;
    }
}
