package io.ningelschlingel.pca.userauth.core.application.register;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Size;

public record RegisterCommand(
    @Email String email,
    @Size(min = 8) String rawPassword
) {}
