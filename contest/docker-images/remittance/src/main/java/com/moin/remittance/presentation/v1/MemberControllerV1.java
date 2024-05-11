package com.moin.remittance.presentation.v1;

import com.moin.remittance.domain.dto.responsebody.HttpResponseBody;
import com.moin.remittance.domain.dto.member.MemberDTO;
import com.moin.remittance.domain.dto.requestbody.MemberLoginRequestBodyDTO;
import com.moin.remittance.application.v1.MemberServiceV1;
import io.swagger.v3.oas.annotations.Hidden;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.*;

import static com.moin.remittance.domain.vo.HttpResponseCode.SUCCESS_MEMBER_LOGIN;
import static com.moin.remittance.domain.vo.HttpResponseCode.SUCCESS_MEMBER_SIGNUP;


@RestController
@RequiredArgsConstructor
@Hidden
@RequestMapping(value = "/api/v1/user")
public class MemberControllerV1 {

    private final MemberServiceV1 memberServiceV1;

    /*회원 가입
     * @RequestBody properties
     * userId: 유저 아이디(이메일 형식)
     * password : 비밀번호
     * name : 이름
     * */
    @PostMapping(value = "/signup")
    public ResponseEntity<HttpResponseBody> signup(@RequestBody @Valid MemberDTO memberDTO) {

        // 유저 추가
        memberServiceV1.saveUser(memberDTO);

        return ResponseEntity.status(SUCCESS_MEMBER_SIGNUP.getStatusCode()).body(
                HttpResponseBody.builder()
                        .statusCode(SUCCESS_MEMBER_SIGNUP.getStatusCode())
                        .message(SUCCESS_MEMBER_SIGNUP.getMessage())
                        .codeName(SUCCESS_MEMBER_SIGNUP.getCodeName())
                        .build()
        );
    }

    // 로그인
    @PostMapping(value = "/login")
    public ResponseEntity<HttpResponseBody> login(@RequestBody @Valid MemberLoginRequestBodyDTO memberDTO) {

        String token = memberServiceV1.getAuthToken(memberDTO.getUsername(), memberDTO.getPassword());

        return ResponseEntity.status(SUCCESS_MEMBER_LOGIN.getStatusCode()).body(
                HttpResponseBody.<String>builder()
                        .statusCode(SUCCESS_MEMBER_LOGIN.getStatusCode())
                        .message(SUCCESS_MEMBER_LOGIN.getMessage())
                        .codeName(SUCCESS_MEMBER_LOGIN.getCodeName())
                        .data(token)
                        .build()
        );
    }

}
