package io.ningelschlingel.pca.userprofile.infrastructure.web.createuser;

import java.util.UUID;

public record CreateUserResponse(
    UUID id,
    String email,
    String fullName
) {}
