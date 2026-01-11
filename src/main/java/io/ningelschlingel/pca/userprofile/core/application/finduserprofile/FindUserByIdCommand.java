package io.ningelschlingel.pca.userprofile.core.application.finduserprofile;

import io.ningelschlingel.pca.shared.core.domain.UserId;

public record FindUserByIdCommand(
    UserId id
) {}
