package com.moin.remittance.core.jwt.filter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.moin.remittance.core.configration.JwtConfigProps;
import com.moin.remittance.core.jwt.provider.AuthUserDetailsProvider;
import com.moin.remittance.core.jwt.provider.JwtTokenProvider;
import com.moin.remittance.domain.dto.responsebody.HttpResponseBody;
import com.moin.remittance.exception.NotFoundMemberException;
import com.moin.remittance.exception.dto.ErrorResponseDTO;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.stereotype.Component;


import java.io.IOException;
import java.util.Iterator;

import static com.moin.remittance.domain.vo.HttpResponseCode.BAD_NOT_MATCH_MEMBER;
import static com.moin.remittance.domain.vo.HttpResponseCode.SUCCESS_MEMBER_LOGIN;


@Slf4j
@Component
public class JwtAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    @Autowired
    private final AuthenticationManager authenticationManager;

    private final JwtTokenProvider jwtTokenProvider;

    private final JwtConfigProps jwtConfigProps;

    private final ObjectMapper objectMapper;

    public JwtAuthenticationFilter(AuthenticationManager authenticationManager,
                                   JwtTokenProvider jwtTokenProvider,
                                   JwtConfigProps jwtConfigProps, ObjectMapper objectMapper
    ) {
        this.authenticationManager = authenticationManager;
        this.jwtTokenProvider = jwtTokenProvider;
        this.jwtConfigProps = jwtConfigProps;
        this.objectMapper = objectMapper;

        super.setAuthenticationManager(authenticationManager);

        log.info("authenticationManager" + authenticationManager);
        setFilterProcessesUrl(jwtConfigProps.AUTH_LOGIN_END_POINT);
    }

    /**
     * 🔐 인증 시도 메소드
     * /login 엔드포인트로 요청 => 필터 => 인증 시도
     */
    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {

        String userId = obtainUsername(request);
        String password = obtainPassword(request);

        log.info("userId: " + userId);
        log.info("password: " + password);

        // 인증정보 객체 생성: 스프링 시큐리티에서 username, password 검증하기 위해서는 token에 담아야 함
        Authentication authentication = new UsernamePasswordAuthenticationToken(userId, password);
        log.info("authentication: " + authentication);

        // 사용자 인증 여부를 판단하는 객체
        authentication = authenticationManager.authenticate(authentication);

        log.info("인증 여부: " + authentication.isAuthenticated());

        if (!authentication.isAuthenticated()) {
            log.info("인증 실패!!!");
            throw new NotFoundMemberException(BAD_NOT_MATCH_MEMBER);
        }

        return authentication;
    }

    @Override
    protected void successfulAuthentication(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain,
            Authentication authentication
    ) throws IOException, ServletException {
        AuthUserDetailsProvider member = (AuthUserDetailsProvider) authentication.getPrincipal();

        Iterator<? extends GrantedAuthority> iterator = member.getAuthorities().iterator();
        String idType = iterator.next().getAuthority();
        String userId = member.getUsername();

        String jwt = jwtTokenProvider.createAuthorizationToken(userId, idType, 60 * 60 * 1000);

        log.info("jwt: " + jwt);


        //스프링 시큐리티 인증 토큰 생성
        Authentication authToken = new UsernamePasswordAuthenticationToken(member, null);

        SecurityContextHolder.getContext().setAuthentication(authToken);

        // JSON 으로 변환하여 응답 본문에 작성
        response.addHeader(jwtConfigProps.AUTH_TOKEN_HEADER, jwtConfigProps.AUTH_TOKEN_PREFIX + jwt);
        response.setStatus(SUCCESS_MEMBER_LOGIN.getStatusCode());
        response.setContentType("application/json;charset=UTF-8");
        response.getWriter().write(objectMapper.writeValueAsString(HttpResponseBody.builder()
                        .statusCode(SUCCESS_MEMBER_LOGIN.getStatusCode())
                        .message(SUCCESS_MEMBER_LOGIN.getMessage())
                        .codeName(SUCCESS_MEMBER_LOGIN.getCodeName())
                        .token(jwt)
                        .build()
                )
        );
    }

    @Override
    protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response, AuthenticationException failed) throws IOException {
        // JSON 으로 변환하여 응답 본문에 작성
        response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
        response.setContentType("application/json;charset=UTF-8");
        response.getWriter().write(objectMapper.writeValueAsString(ErrorResponseDTO.builder()
                        .code(BAD_NOT_MATCH_MEMBER.getStatusCode())
                        .message(BAD_NOT_MATCH_MEMBER.getMessage())
                        .codeName(BAD_NOT_MATCH_MEMBER.getCodeName())
                        .build()
                )
        );
    }
}
