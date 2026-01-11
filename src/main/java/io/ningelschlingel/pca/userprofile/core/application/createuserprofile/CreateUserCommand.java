package io.ningelschlingel.pca.userprofile.core.application.createuserprofile;

import io.ningelschlingel.pca.shared.core.domain.UserId;

public record CreateUserCommand(
    UserId id,
    String email,
    String fullName
) {}
