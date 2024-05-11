package com.moin.remittance.exception;

import com.moin.remittance.domain.vo.HttpResponseCode;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.stereotype.Component;

@Getter
public class NotFoundMemberException extends InternalAuthenticationServiceException {
    private String codeName;
    private int code;
    private String message;

    public NotFoundMemberException(HttpResponseCode errorCode) {
        super(errorCode.getMessage());
        this.code = errorCode.getStatusCode();
        this.codeName = errorCode.getCodeName();
    }
}
