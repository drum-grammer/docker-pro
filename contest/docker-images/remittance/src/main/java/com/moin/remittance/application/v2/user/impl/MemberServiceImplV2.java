package com.moin.remittance.application.v2.user.impl;

import com.moin.remittance.domain.entity.member.v2.MemberEntityV2;
import com.moin.remittance.exception.DuplicateUserIdException;
import com.moin.remittance.exception.InValidPatternTypeException;
import com.moin.remittance.exception.NotFoundMemberException;
import com.moin.remittance.domain.dto.member.MemberDTO;
import com.moin.remittance.application.v2.user.MemberServiceV2;
import com.moin.remittance.repository.v2.MemberRepositoryV2;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;


import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static com.moin.remittance.domain.vo.HttpResponseCode.*;


@Service
@RequiredArgsConstructor
public class MemberServiceImplV2 implements MemberServiceV2 {
    private final MemberRepositoryV2 memberRepositoryV2;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    @Value("${jwt.token.secret}")
    private String key;

    @Override
    public void saveUser(MemberDTO member) {
        // 1. id type에 따른 id value 정규표현식 체크
        String RESIDENT_NUMBER_REGEX = "\\d{6}-\\d{7}";
        String BUSINESS_NUMBER_REGEX = "\\d{3}-\\d{2}-\\d{5}";

        Pattern residentPattern = Pattern.compile(RESIDENT_NUMBER_REGEX);
        Pattern businessPattern = Pattern.compile(BUSINESS_NUMBER_REGEX);

        Matcher targetMatcher;
        switch (member.getIdType()) { // type 매칭 -> 패턴 체크
            case "reg_no":
                targetMatcher = residentPattern.matcher(member.getIdValue());
                if (!targetMatcher.matches()) {
                    throw new InValidPatternTypeException(BAD_INDIVIDUAL_MEMBER_INVALID_ID_VALUE);
                }
                break;
            case "business_no":
                targetMatcher = businessPattern.matcher(member.getIdValue());
                if (!targetMatcher.matches()) {
                    throw new InValidPatternTypeException(BAD_CORPORATION_MEMBER_INVALID_ID_VALUE);
                }
                break;
            default:
                throw new InValidPatternTypeException(BAD_REQUEST_BODY_INVALID_ERROR);// @valid에서 체킹 하지만 혹시 모를 예외 상황 대비
        }

        // 2. 회원 중복 조회
        boolean isExistingUserId = memberRepositoryV2.existsByUserId(member.getUserId());

        if (isExistingUserId) {
            throw new DuplicateUserIdException(BAD_DUPLICATE_USERID_INVALID_USERID);
        }

        // 3. 비밀번호, 주민등록번호 or 사업자등록번호 암호화해서 저장
        memberRepositoryV2.saveAndFlush(MemberEntityV2.builder()
                .userId(member.getUserId())
                .password(bCryptPasswordEncoder.encode(member.getPassword()))
                .name(member.getName())
                .idType(member.getIdType())
                .idValue(bCryptPasswordEncoder.encode(member.getIdValue()))
                .build());
    }
}
