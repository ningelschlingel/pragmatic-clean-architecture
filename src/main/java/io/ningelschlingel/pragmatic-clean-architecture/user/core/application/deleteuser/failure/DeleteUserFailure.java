package io.ningelschlingel.pragmaticcleanarchitecture.user.core.application.deleteuser.failure;

import io.ningelschlingel.pragmaticcleanarchitecture.user.core.domain.UserFailure;

public sealed interface DeleteUserFailure extends UserFailure permits DeleteUserNotAllowed {}
