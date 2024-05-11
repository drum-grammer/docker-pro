package com.moin.remittance.exception;

import com.moin.remittance.domain.vo.HttpResponseCode;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ExternalAPIException extends RuntimeException {
    private String codeName;
    private int code;
    private String message;

    public ExternalAPIException(HttpResponseCode errorCode) {
        this.code = errorCode.getStatusCode();
        this.message = errorCode.getMessage();
        this.codeName = errorCode.getCodeName();
    }
}
