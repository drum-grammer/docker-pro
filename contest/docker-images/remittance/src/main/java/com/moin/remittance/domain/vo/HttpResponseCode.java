package com.moin.remittance.domain.vo;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum HttpResponseCode {

    // 회원 관련 성공 상태 코드
    /* 2xx */
    SUCCESS_MEMBER_SIGNUP(HttpStatus.OK.value(), "회원 가입 성공", "SUCCESS_MEMBER_SIGNUP"),
    SUCCESS_MEMBER_LOGIN(HttpStatus.OK.value(), "로그인 성공", "SUCCESS_MEMBER_LOGIN"),

    // 송금 관련 성공 상태 코드
    /* 2xx */
    SUCCESS_GET_REMITTANCE_QUOTE(HttpStatus.OK.value(), "회원님이 요청하신 송금 견적서를 가져왔습니다.", "SUCCESS_GET_REMITTANCE_QUOTE"),
    SUCCESS_REQUEST_REMITTANCE_ACCEPT(HttpStatus.OK.value(), "회원님의 송금 접수 요청이 완료되었습니다.", "SUCCESS_REQUEST_REMITTANCE_ACCEPT"),
    SUCCESS_GET_REMITTANCE_LOG(HttpStatus.OK.value(), "회원님의 송금 거래 이력입니다.", "SUCCESS_GET_REMITTANCE_LOG"),

    // 회원 관련 에러 코드
    /* 4xx */
    BAD_REQUEST_BODY_INVALID_ERROR(HttpStatus.BAD_REQUEST.value(), "잘못된 요청 바디 입니다.", "BAD_REQUEST_BODY_INVALID_ERROR"),
    BAD_INDIVIDUAL_MEMBER_INVALID_ID_VALUE(HttpStatus.BAD_REQUEST.value(), "개인 회원은 주민등록번호를 입력해 주시길 바랍니다.", "BAD_INDIVIDUAL_MEMBER_INVALID_ID_VALUE"),
    BAD_CORPORATION_MEMBER_INVALID_ID_VALUE(HttpStatus.BAD_REQUEST.value(), "법인 회원은 사업자등록 번호를 입력해 주시길 바랍니다.", "BAD_CORPORATION_MEMBER_INVALID_ID_VALUE"),
    BAD_NOT_FOUND_ID_TYPE(HttpStatus.NOT_FOUND.value(), "해당 회원의 타입이 없습니다.", "BAD_NOT_FOUND_ID_TYPE"),
    BAD_DUPLICATE_USERID_INVALID_USERID(HttpStatus.BAD_REQUEST.value(), "중복된 아이디가 존재합니다.", "BAD_DUPLICATE_USERID_INVALID_USERID"),
    BAD_NOT_MATCH_MEMBER(HttpStatus.BAD_REQUEST.value(), "일치하는 회원이 없습니다.", "BAD_NOT_MATCH_MEMBER"),

    // 인증 토큰 관련 에러 코드
    /* 4xx */
    UNAUTHORIZED(HttpStatus.UNAUTHORIZED.value(), "유효하지 않은 인증토큰 입니다.", "UNAUTHORIZED"),

    /**
     * 인증 토큰 관련 에러 코드: 4xx
     **/
    BAD_REQUEST_HEADER(HttpStatus.BAD_REQUEST.value(), "잘못된 요청 헤더 입니다.", "BAD_REQUEST_HEADER"),
    UNAUTHORIZED_PAYLOAD_MEMBER_DATA(HttpStatus.UNAUTHORIZED.value(), "토큰에 저장된 유저 정보가 잘못되었습니다.", "UNAUTHORIZED_PAYLOAD_MEMBER_DATA"),
    UNAUTHORIZED_EXPIRED_JWT(HttpStatus.UNAUTHORIZED.value(), "만료된 토큰 입니다.", "UNAUTHORIZED_EXPIRED_JWT"),
    UNAUTHORIZED_MALFORMED_JWT(HttpStatus.UNAUTHORIZED.value(), "잘못된 접근 토큰 입니다.", "UNAUTHORIZED_MALFORMED_JWT"),
    UNAUTHORIZED_NOT_EXIST_JWT(HttpStatus.UNAUTHORIZED.value(), "인증 토큰이 없습니다.", "UNAUTHORIZED_NOT_EXIST_JWT"),

    // 송금 견적서 요청 관련 에러코드
    BAD_NEGATIVE_AMOUNT(HttpStatus.BAD_REQUEST.value(), "송금액을 양수로 입력 해주세요.", "BAD_NEGATIVE_AMOUNT"),
    BAD_NEGATIVE_TARGET_AMOUNT(HttpStatus.BAD_REQUEST.value(), "받는 금액이 음수 입니다.", "BAD_NEGATIVE_TARGET_AMOUNT"),

    // 송금 접수 요청 관련 에러 코드
    /* 4xx */
    BAD_QUOTE_EXPIRATION_TIME_OVER(HttpStatus.UNAUTHORIZED.value(), "송금 견적서가 만료 되었습니다.", "BAD_QUOTE_EXPIRATION_TIME_OVER"),
    BAD_NOT_MATCH_QUOTATION(HttpStatus.BAD_REQUEST.value(), "유효한 견적서가 없습니다.", "BAD_NOT_MATCH_QUOTATION"),
    BAD_INDIVIDUAL_MEMBER_LIMIT_EXCESS(HttpStatus.BAD_REQUEST.value(), "개인 회원님의 1일 송금가능액 한도 초과 하였습니다.", "BAD_INDIVIDUAL_MEMBER_LIMIT_EXCESS"),
    BAD_CORPORATION_MEMBER_LIMIT_EXCESS(HttpStatus.BAD_REQUEST.value(), "법인 회원님의 1일 송금가능액 한도 초과 하였습니다.", "BAD_CORPORATION_MEMBER_LIMIT_EXCESS"),
    BAD_INDIVIDUAL_MEMBER_SUM_LIMIT_EXCESS(HttpStatus.BAD_REQUEST.value(), "합산 결과 개인 회원님의 1일 송금가능액 한도 초과 하였습니다.", "BAD_INDIVIDUAL_MEMBER_SUM_LIMIT_EXCESS"),
    BAD_CORPORATION_MEMBER_SUM_LIMIT_EXCESS(HttpStatus.BAD_REQUEST.value(), "합산 결과 법인 회원님의 1일 송금가능액 한도 초과 하였습니다.", "BAD_CORPORATION_MEMBER_SUM_LIMIT_EXCESS"),

    // 서버 에러
    /* 5xx */
    INTERNAL_SERVER_ERROR_EXCHANGE_RATE_DATA(HttpStatus.INTERNAL_SERVER_ERROR.value(), "환율 정보를 응답 받지 못했습니다.", "INTERNAL_SERVER_ERROR_EXCHANGE_RATE_DATA"),
    INTERNAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR.value(), "서버 에러", "INTERNAL_SERVER_ERROR");

    private final int statusCode;
    private final String message;
    private final String codeName;
}
