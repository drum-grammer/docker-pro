package com.moin.remittance.presentation.v2;

import com.moin.remittance.application.v2.user.MemberServiceV2;
import com.moin.remittance.domain.dto.member.MemberDTO;
import com.moin.remittance.domain.dto.requestbody.MemberLoginRequestBodyDTO;
import com.moin.remittance.domain.dto.responsebody.HttpResponseBody;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static com.moin.remittance.domain.vo.HttpResponseCode.SUCCESS_MEMBER_LOGIN;
import static com.moin.remittance.domain.vo.HttpResponseCode.SUCCESS_MEMBER_SIGNUP;


@RestController
@RequiredArgsConstructor
@Tag(name = "MemberControllerV2", description = "회원 관련 컨트롤러: 회원가입")
@RequestMapping(value = "/api/v2/user")
public class MemberControllerV2 {

    private final MemberServiceV2 memberService;

    /**
     * 회원 가입
     *
     * @RequestBody properties
     * userId: 유저 아이디(이메일 형식)
     * password : 비밀번호
     * name : 이름
     */
    @Operation(summary = "회원 가입")
    @PostMapping(value = "/signup", headers = "API-Version=2")
    public ResponseEntity<HttpResponseBody<?>> signup(@RequestBody @Valid MemberDTO memberDTO) {

        // 유저 추가
        memberService.saveUser(memberDTO);

        return ResponseEntity.status(SUCCESS_MEMBER_SIGNUP.getStatusCode()).body(
                HttpResponseBody.builder()
                        .statusCode(SUCCESS_MEMBER_SIGNUP.getStatusCode())
                        .message(SUCCESS_MEMBER_SIGNUP.getMessage())
                        .codeName(SUCCESS_MEMBER_SIGNUP.getCodeName())
                        .build()
        );
    }

    @Operation(summary = "로그인 엔드포인트(swagger용 엔드포인트: 서블릿 필터에서 응답함)")
    @PostMapping(value = "/login",
            headers = "API-Version=2",
            consumes = MediaType.MULTIPART_FORM_DATA_VALUE, produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<HttpResponseBody<?>> login(@RequestBody @Valid MemberLoginRequestBodyDTO memberDTO) {
        return ResponseEntity.status(SUCCESS_MEMBER_LOGIN.getStatusCode()).body(
                HttpResponseBody.builder()
                        .statusCode(SUCCESS_MEMBER_LOGIN.getStatusCode())
                        .message(SUCCESS_MEMBER_LOGIN.getMessage())
                        .codeName(SUCCESS_MEMBER_LOGIN.getCodeName())
                        .token("토큰")
                        .build()
        );
    }
}
