package io.ningelschlingel.pragmaticcleanarchitecture.user.infrastructure.web.finduser;

import io.ningelschlingel.pragmaticcleanarchitecture.user.core.domain.User;

public class FindUserHttpMapper {

    public static FindUserResponse toResponse(User user) {
        return new FindUserResponse(user.getId().value(), user.getEmail(), user.getFullName());
    }
    
}
