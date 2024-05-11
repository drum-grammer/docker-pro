package com.moin.remittance.core.configration;


import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class SwaggerConfig {

    private final JwtConfigProps jwtConfigProps;

    @Bean
    public OpenAPI openAPI() {
        Info info = new Info()
                .version("ver.2.0.0")
                .title("기업 과제 테스트: 📚 모인 백엔드 API ")
                .description("해외 송금앱 백엔드 서버: 수수료 정책 적용");

        SecurityRequirement securityRequirement = new SecurityRequirement().addList(jwtConfigProps.AUTH_TOKEN_TYPE); // 헤더에 토큰 포함
        Components components = new Components().addSecuritySchemes(jwtConfigProps.AUTH_TOKEN_TYPE, new SecurityScheme()
                .name(jwtConfigProps.AUTH_TOKEN_TYPE)
                .type(SecurityScheme.Type.HTTP)
                .scheme("Bearer")
                .bearerFormat(jwtConfigProps.AUTH_TOKEN_TYPE)
        );

        return new OpenAPI()
                .info(info)
                .addSecurityItem(securityRequirement)
                .components(components);
    }
}
