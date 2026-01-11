package io.ningelschlingel.pca.userprofile.core.application.finduserprofile.failure;

import io.ningelschlingel.pca.userprofile.core.domain.UserFailure;

public sealed interface FindUserFailure extends UserFailure permits UserProfileNotFound {}
