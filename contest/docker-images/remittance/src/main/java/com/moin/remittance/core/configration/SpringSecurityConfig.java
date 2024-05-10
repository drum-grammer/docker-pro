package com.moin.remittance.core.configration;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.moin.remittance.core.jwt.application.AuthUserDetailService;
import com.moin.remittance.core.jwt.filter.JwtAuthenticationFilter;
import com.moin.remittance.core.jwt.filter.JwtRequestFilter;
import com.moin.remittance.core.jwt.provider.JwtTokenProvider;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.HeadersConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;

import java.util.Collections;
import java.util.List;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SpringSecurityConfig {

    private final AuthenticationConfiguration authenticationConfiguration;
    private final JwtTokenProvider jwtTokenProvider;
    private final AuthUserDetailService authUserDetailService;
    private final JwtConfigProps jwtConfigProps;
    private final ObjectMapper objectMapper;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {

        // 폼 로그인 비활성화
        httpSecurity.formLogin(AbstractHttpConfigurer::disable);

        // 세션 생성 정책 설정 => JWT 인증 할 거기 때문에 비활성화
        httpSecurity.sessionManagement(management -> management.sessionCreationPolicy(SessionCreationPolicy.STATELESS));

        /*
         * 세션을 사용하지 않는다면 CSRF 필요하지 않습니다.
         * 즉, 상태를 저장하지 않는 Rest API 같은 경우에는 CSRF 필요하지 않습니다.
         * 그래서 비활성화 해줍니다.
         * */
        httpSecurity.csrf(AbstractHttpConfigurer::disable);

        /*
         * Spring Security 에서의 cors 설정 입니다.
         * 만약 frontend 서버 포트가 3000번 이라면 3000번 포트를 허용해야 합니다.
         * 그래야 요청이 허용됩니다.
         * */
        httpSecurity.cors(cors -> cors.configurationSource(new CorsConfigurationSource() {
                    @Override
                    public CorsConfiguration getCorsConfiguration(HttpServletRequest request) {

                        CorsConfiguration config = new CorsConfiguration();

                        config.setAllowedOrigins(Collections.singletonList("http://localhost:8080"));
                        config.setAllowedMethods(List.of("GET", "POST", "PUT", "PATCH", "OPTIONS"));
                        config.setAllowCredentials(true);
                        config.setAllowedHeaders(Collections.singletonList("*"));
                        config.setMaxAge(3600L);

                        config.setExposedHeaders(List.of("Authorization"));

                        return config;
                    }
                })
        );

        /*
         * Spring Security 는 기본적으로 iframe이 비활성화 상태입니다.
         * iframe 허용하려면 활성상태로 설정 해야합니다.
         * */
        httpSecurity.headers(header -> header.frameOptions(HeadersConfigurer.FrameOptionsConfig::sameOrigin));

        /*
         * 허용할 엔드포인트와 허용하지 않을 엔드포인트를 설정합니다.
         * 로그인과 회원가입은 허용해주어야 인증되지 않은 회원이 서비스를 이용할 수 있도록 하는 엔드포인트 이기때문에 이 두개의 엔드포인트는 허용해줍니다.
         * */
        httpSecurity.authorizeHttpRequests(req -> req
                .requestMatchers("/api/v2/user/login", "/api/v2/user/signup", "/api/v2/moin-api", "/swagger-ui/**").permitAll()
                .requestMatchers(PathRequest.toH2Console()).permitAll()
                .anyRequest().authenticated()
        );

        /*
         * 인증 방식 설정
         *   - 인메모리 방식
         *   - JDBC 방식
         *   - 커스텀 방식
         * 적용: 커스텀 방식
         * */
        httpSecurity.userDetailsService(authUserDetailService);

        /*
         * 필터 등록
         * */
        httpSecurity
                .addFilterBefore(new JwtRequestFilter(jwtTokenProvider, jwtConfigProps), UsernamePasswordAuthenticationFilter.class)
                .addFilterAt(
                        new JwtAuthenticationFilter(authenticationManager(authenticationConfiguration), jwtTokenProvider, jwtConfigProps, objectMapper),
                        UsernamePasswordAuthenticationFilter.class);

        return httpSecurity.build();
    }

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }
}

