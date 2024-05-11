package com.moin.remittance.core.configration;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class JwtConfigProps {

    @Schema(description = "jwt 암호화 키")
    @Value("${spring.jwt.SECRET_KEY}")
    public String SECRET_KEY;

    @Value("${spring.jwt.AUTH_LOGIN_END_POINT}")
    public String AUTH_LOGIN_END_POINT;

    @Value("${spring.jwt.AUTH_TOKEN_HEADER}")
    public String AUTH_TOKEN_HEADER;

    @Value("${spring.jwt.AUTH_TOKEN_PREFIX}")
    public String AUTH_TOKEN_PREFIX;

    @Value("${spring.jwt.AUTH_TOKEN_TYPE}")
    public String AUTH_TOKEN_TYPE;
}
