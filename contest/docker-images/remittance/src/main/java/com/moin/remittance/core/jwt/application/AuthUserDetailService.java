package com.moin.remittance.core.jwt.application;


import com.moin.remittance.core.jwt.provider.AuthUserDetailsProvider;
import com.moin.remittance.domain.entity.member.v2.MemberEntityV2;
import com.moin.remittance.exception.NotFoundMemberException;
import com.moin.remittance.repository.v2.MemberRepositoryV2;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

import static com.moin.remittance.domain.vo.HttpResponseCode.BAD_NOT_MATCH_MEMBER;

@Slf4j
@Service
@RequiredArgsConstructor
public class AuthUserDetailService implements UserDetailsService {

    private final MemberRepositoryV2 memberRepositoryV2;

    /**
     * Spring Security => 유저 정보 조회 => 인증 처리
     * */
    @Override
    public UserDetails loadUserByUsername(String userId) throws InternalAuthenticationServiceException {
        log.info("'" + userId + "' 조회 중......");

        MemberEntityV2 member = memberRepositoryV2.findByUserId(userId);

        /*
        * @Exception NotFoundMemberException: DB에 일치하는 유저 없는 경우
        * */
        if(member == null) {
            throw new NotFoundMemberException(BAD_NOT_MATCH_MEMBER);
        }

        log.info("'" + userId + "' 존재");

        return new AuthUserDetailsProvider(member);
    }
}
