package io.ningelschlingel.pca.userauth.core.application.login.failure;

import io.ningelschlingel.pca.userprofile.core.domain.UserFailure;

public sealed interface LoginUserFailure extends UserFailure permits UserCredentialsInvalid, UserNotFoundForLogin {}
