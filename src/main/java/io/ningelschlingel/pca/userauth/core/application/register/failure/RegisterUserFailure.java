package io.ningelschlingel.pca.userauth.core.application.register.failure;

import io.ningelschlingel.pca.userprofile.core.domain.UserFailure;

public sealed interface RegisterUserFailure extends UserFailure permits UserAuthExists, AuthDataInvalid {}
