package io.ningelschlingel.pragmaticcleanarchitecture.user.core.application.finduser.failure;

import io.ningelschlingel.pragmaticcleanarchitecture.user.core.domain.UserFailure;

public sealed interface FindUserFailure extends UserFailure permits UserNotFound {}
