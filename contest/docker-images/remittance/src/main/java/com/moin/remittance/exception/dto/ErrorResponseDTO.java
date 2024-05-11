package com.moin.remittance.exception.dto;


import com.moin.remittance.domain.vo.HttpResponseCode;
import com.moin.remittance.exception.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import org.springframework.stereotype.Component;

@Data
@Builder
@AllArgsConstructor
public class ErrorResponseDTO {
    private String codeName;
    private int code;
    private String message;

    public ErrorResponseDTO(NegativeNumberException e) {
        this.codeName = e.getCodeName();
        this.code = e.getCode();
        this.message = e.getMessage();
    }

    public ErrorResponseDTO(ExternalAPIException e) {
        this.codeName = e.getCodeName();
        this.code = e.getCode();
        this.message = e.getMessage();
    }

    public ErrorResponseDTO(NotExternalDataException e) {
        this.codeName = e.getCodeName();
        this.code = e.getCode();
        this.message = e.getMessage();


    }

    public ErrorResponseDTO(ExpirationTimeOverException e) {
        this.codeName = e.getCodeName();
        this.code = e.getCode();
        this.message = e.getMessage();
    }

    public ErrorResponseDTO(AmountLimitExcessException e) {
        this.codeName = e.getCodeName();
        this.code = e.getCode();
        this.message = e.getMessage();
    }

    public ErrorResponseDTO(InValidPatternTypeException e) {
        this.codeName = e.getCodeName();
        this.code = e.getCode();
        this.message = e.getMessage();
    }

    public ErrorResponseDTO(DuplicateUserIdException e) {
        this.codeName = e.getCodeName();
        this.code = e.getCode();
        this.message = e.getMessage();
    }

    public ErrorResponseDTO(NotFoundMemberException e) {
        this.codeName = e.getCodeName();
        this.code = e.getCode();
        this.message = e.getMessage();
    }

    public ErrorResponseDTO(NullPointerQuotationException e) {
        this.codeName = e.getCodeName();
        this.code = e.getCode();
        this.message = e.getMessage();
    }

    public ErrorResponseDTO(InValidRequestHeaderException e) {
        this.codeName = e.getCodeName();
        this.code = e.getCode();
        this.message = e.getMessage();
    }

    public ErrorResponseDTO(UnAuthorizationJwtException e) {
        this.codeName = e.getCodeName();
        this.code = e.getCode();
        this.message = e.getMessage();
    }

    public ErrorResponseDTO(HttpResponseCode errorCode) {
        this.codeName = errorCode.getCodeName();
        this.code = errorCode.getStatusCode();
        this.message = errorCode.getMessage();
    }
}
