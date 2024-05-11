package com.moin.remittance.core.jwt.provider;

import com.moin.remittance.core.configration.JwtConfigProps;
import com.moin.remittance.domain.entity.member.v2.MemberEntityV2;
import com.moin.remittance.exception.UnAuthorizationJwtException;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import java.util.Date;

import static com.moin.remittance.domain.vo.HttpResponseCode.*;


//
@Component
@RequiredArgsConstructor
@Slf4j
public class JwtTokenProvider {
    private final JwtConfigProps jwtConfigProps;

    /**
     * 1. 인증 토큰 생성
     *
     * @Param String userId: 유저 아이디
     * @Param long second: 시간 초
     * @Return String jwt: 인증 토큰(jsonwebtoken)
     * secretKey: 영문 문자열 64
     * HMAC-SHA512 알고리즘 사용
     * 영어문자 하나당 1bit => 512bit = 64byte = 8bit X 8bit => 문자열 길이 64
     */
    public String createAuthorizationToken(String userId, String idType,long milliSecond) {
        return Jwts.builder()
                // 영어문자 하나당 1bit => 512bit = 64byte = 8bit X 8bit => 문자열 길이 64
                .signWith(Keys.hmacShaKeyFor(jwtConfigProps.SECRET_KEY.getBytes()), Jwts.SIG.HS512)
                .header()
                .add("typ", jwtConfigProps.AUTH_TOKEN_TYPE)
                .and()
                .expiration(new Date(System.currentTimeMillis() + milliSecond))
                .claim("userId", userId)
                .claim("idType", idType)
                .compact();
    }


    /**
     * 2. 인증 토큰 파싱
     *
     * @Param String authHeader: 인증 요청 헤더
     * @Return UsernamePasswordAuthenticationToken token: 사용자 정보를 담은 인가 토큰 객체
     * secretKey: 영문 문자열 64
     * HMAC-SHA512 알고리즘 사용
     * 영어문자 하나당 1bit => 512bit = 64byte = 8bit X 8bit => 문자열 길이 64
     */
    public Authentication getAuthentication(String token) {
        try {
            log.info("token: " + token);

            // JWT Parsing
            Jws<Claims> parsedToken = Jwts.parser()
                    .verifyWith(Keys.hmacShaKeyFor(jwtConfigProps.SECRET_KEY.getBytes()))
                    .build()
                    .parseSignedClaims(token);

            String userId = parsedToken.getPayload().get("userId").toString();
            String idType = parsedToken.getPayload().get("idType").toString();

            if (userId == null || userId.isEmpty()) {
                throw new UnAuthorizationJwtException(UNAUTHORIZED_PAYLOAD_MEMBER_DATA);
            }

            AuthUserDetailsProvider user = new AuthUserDetailsProvider(
                    MemberEntityV2.builder()
                            .userId(userId)
                            .idType(idType)
                            .build()
            );

            return new UsernamePasswordAuthenticationToken(user, null);
        } catch (ExpiredJwtException e) {       // 토큰 만료
            throw new UnAuthorizationJwtException(UNAUTHORIZED_EXPIRED_JWT);
        } catch (MalformedJwtException | IllegalArgumentException e) {      // JWT 형태가 아닌 토큰 or 잘못된 토큰
            throw new UnAuthorizationJwtException(UNAUTHORIZED_MALFORMED_JWT);
        }
    }


    /**
     * 3. 인증 토큰 유효성 검사
     *
     * @Param String jwt: 인증 토큰
     * @Return boolean token: 사용자 정보를 담은 인가 토큰 객체
     * secretKey: 영문 문자열 64
     * HMAC-SHA512 알고리즘 사용
     * 영어문자 하나당 1bit => 512bit = 64byte = 8bit X 8bit => 문자열 길이 64
     */
    public boolean isExpiredJWT (String jwt) {
        try {
            // JWT Parsing
            Jws<Claims> parsedToken = Jwts.parser()
                    .verifyWith(Keys.hmacShaKeyFor(jwtConfigProps.SECRET_KEY.getBytes()))
                    .build()
                    .parseSignedClaims(jwt);

            System.out.println(parsedToken);
            Date exp = parsedToken.getPayload().getExpiration();

            // 만료시간, 현재시간 비교
            // true: 만료, false: 유효
            return exp.before(new Date());

        } catch (ExpiredJwtException e) {       // 토큰 만료
            throw new UnAuthorizationJwtException(UNAUTHORIZED_EXPIRED_JWT);
        } catch (MalformedJwtException | IllegalArgumentException e) {      // JWT 형태가 아닌 토큰 or 잘못된 토큰
            throw new UnAuthorizationJwtException(UNAUTHORIZED_MALFORMED_JWT);
        } catch (NullPointerException e) {
            throw new UnAuthorizationJwtException(UNAUTHORIZED_NOT_EXIST_JWT);
        }
    }
}
