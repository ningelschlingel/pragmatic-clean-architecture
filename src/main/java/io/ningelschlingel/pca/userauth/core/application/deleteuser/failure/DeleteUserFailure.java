package io.ningelschlingel.pca.userauth.core.application.deleteuser.failure;

import io.ningelschlingel.pca.userprofile.core.domain.UserFailure;

public sealed interface DeleteUserFailure extends UserFailure permits DeleteUserNotAllowed {}
