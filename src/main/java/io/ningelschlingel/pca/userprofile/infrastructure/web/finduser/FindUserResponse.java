package io.ningelschlingel.pca.userprofile.infrastructure.web.finduser;

import java.util.UUID;

public record FindUserResponse(
    UUID id,
    String email,
    String fullName
) {}
