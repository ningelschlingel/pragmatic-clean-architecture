package io.ningelschlingel.pca.userauth.infrastructure.web;

import org.mapstruct.Mapper;

import io.ningelschlingel.pca.userauth.core.application.login.LoginCommand;
import io.ningelschlingel.pca.userauth.core.application.register.RegisterCommand;
import io.ningelschlingel.pca.userauth.infrastructure.web.payload.LoginRequest;
import io.ningelschlingel.pca.userauth.infrastructure.web.payload.RegisterRequest;

@Mapper
public interface UserAuthHttpMapper {

    RegisterCommand toCommand(RegisterRequest request);

    LoginCommand toCommand(LoginRequest request);
}
