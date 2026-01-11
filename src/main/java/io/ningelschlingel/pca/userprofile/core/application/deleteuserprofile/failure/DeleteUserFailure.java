package io.ningelschlingel.pca.userprofile.core.application.deleteuserprofile.failure;

import io.ningelschlingel.pca.userprofile.core.domain.UserFailure;

public sealed interface DeleteUserFailure extends UserFailure permits DeleteUserNotAllowed {}
