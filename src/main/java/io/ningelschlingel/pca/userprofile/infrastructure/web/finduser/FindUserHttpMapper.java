package io.ningelschlingel.pca.userprofile.infrastructure.web.finduser;

import io.ningelschlingel.pca.userprofile.core.domain.UserProfile;

public class FindUserHttpMapper {

    public static FindUserResponse toResponse(UserProfile user) {
        return new FindUserResponse(user.getId().value(), user.getEmail(), user.getFullName());
    }
    
}
