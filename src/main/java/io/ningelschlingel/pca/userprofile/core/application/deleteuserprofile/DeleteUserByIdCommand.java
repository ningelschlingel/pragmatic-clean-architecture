package io.ningelschlingel.pca.userprofile.core.application.deleteuserprofile;

import io.ningelschlingel.pca.shared.core.domain.UserId;

public record DeleteUserByIdCommand(
    UserId id
) {}
