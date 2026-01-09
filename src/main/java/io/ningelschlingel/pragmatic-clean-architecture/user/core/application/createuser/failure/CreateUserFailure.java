package io.ningelschlingel.pragmaticcleanarchitecture.user.core.application.createuser.failure;

import io.ningelschlingel.pragmaticcleanarchitecture.user.core.domain.UserFailure;

public sealed interface CreateUserFailure extends UserFailure permits UserExistsAlready, UserDataInvalid {}
