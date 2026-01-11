package io.ningelschlingel.pca.userprofile.core.application.createuserprofile.failure;

import io.ningelschlingel.pca.userprofile.core.domain.UserFailure;

public sealed interface CreateUserFailure extends UserFailure permits UserExistsAlready, UserDataInvalid {}
