package io.ningelschlingel.pragmaticcleanarchitecture.user.infrastructure.web.finduser;

import java.util.UUID;

public record FindUserResponse(
    UUID id,
    String email,
    String fullName
) {}
