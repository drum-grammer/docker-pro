package com.moin.remittance.exception.handler;

import com.moin.remittance.domain.dto.remittance.v1.TransactionLogDTO;
import com.moin.remittance.domain.dto.responsebody.HttpResponseBody;
import com.moin.remittance.exception.dto.ErrorResponseDTO;
import com.moin.remittance.exception.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;

import static com.moin.remittance.domain.vo.HttpResponseCode.*;


@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(NegativeNumberException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<HttpResponseBody<?>> negativeNumberExceptionHandler(NegativeNumberException e) {
        return ResponseEntity.status(e.getCode()).body(
                HttpResponseBody.<TransactionLogDTO>builder()
                        .statusCode(e.getCode())
                        .message(e.getMessage())
                        .codeName(e.getCodeName())
                        .build()
        );
    }

    @ExceptionHandler(ExternalAPIException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ResponseEntity<HttpResponseBody<?>> externalAPIExceptionHandler(ExternalAPIException e) {
        return ResponseEntity.status(e.getCode()).body(
                HttpResponseBody.<TransactionLogDTO>builder()
                        .statusCode(e.getCode())
                        .message(e.getMessage())
                        .codeName(e.getCodeName())
                        .build()
        );
    }

    @ExceptionHandler(NotExternalDataException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ResponseEntity<HashMap<String, ErrorResponseDTO>> notExternalDataExceptionHandler(NotExternalDataException e) {
        HashMap<String, ErrorResponseDTO> responseBody = new HashMap<String, ErrorResponseDTO>();
        responseBody.put("result", new ErrorResponseDTO(e));
        return ResponseEntity.status(e.getCode()).body(responseBody);
    }

    @ExceptionHandler(ExpirationTimeOverException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<HashMap<String, ErrorResponseDTO>> expirationTimeOverExceptionHandler(ExpirationTimeOverException e) {
        HashMap<String, ErrorResponseDTO> responseBody = new HashMap<String, ErrorResponseDTO>();
        responseBody.put("result", new ErrorResponseDTO(e));
        return ResponseEntity.status(e.getCode()).body(responseBody);
    }

    @ExceptionHandler(AmountLimitExcessException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<HashMap<String, ErrorResponseDTO>> amountLimitExcessExceptionHandler(AmountLimitExcessException e) {
        HashMap<String, ErrorResponseDTO> responseBody = new HashMap<String, ErrorResponseDTO>();
        responseBody.put("result", new ErrorResponseDTO(e));
        return ResponseEntity.status(e.getCode()).body(responseBody);
    }

    @ExceptionHandler(value = MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<HashMap<String, Object>> methodArgumentNotValidExceptionHandler(MethodArgumentNotValidException e) {
        HashMap<String, Object> responseBody = new HashMap<String, Object>();
        e.getBindingResult().getAllErrors()
                .forEach(c -> responseBody.put(((FieldError) c).getField(), c.getDefaultMessage()));
        responseBody.put("result", new ErrorResponseDTO(BAD_REQUEST_BODY_INVALID_ERROR));
        return ResponseEntity.status(e.getStatusCode()).body(responseBody);
    }

    @ExceptionHandler(InValidPatternTypeException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<HashMap<String, Object>> inValidPatternTypeExceptionHandler(InValidPatternTypeException e) {
        HashMap<String, Object> responseBody = new HashMap<String, Object>();
        responseBody.put("result", new ErrorResponseDTO(e));
        responseBody.put("idValue", "개인 회원은 주민등록번호, 법인 회원은 사업자번호를 등록해주세요.");
        return ResponseEntity.status(new ErrorResponseDTO(BAD_REQUEST_BODY_INVALID_ERROR).getCode()).body(responseBody);
    }

    @ExceptionHandler(DuplicateUserIdException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<HashMap<String, ErrorResponseDTO>> inValidPatternTypeExceptionHandler(DuplicateUserIdException e) {
        HashMap<String, ErrorResponseDTO> responseBody = new HashMap<String, ErrorResponseDTO>();
        responseBody.put("result", new ErrorResponseDTO(e));
        return ResponseEntity.status(e.getCode()).body(responseBody);
    }

    @ExceptionHandler(NotFoundMemberException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<HashMap<String, ErrorResponseDTO>> notFoundMemberException(NotFoundMemberException e) {
        HashMap<String, ErrorResponseDTO> responseBody = new HashMap<String, ErrorResponseDTO>();
        responseBody.put("result", new ErrorResponseDTO(e));
        return ResponseEntity.status(e.getCode()).body(responseBody);
    }

    @ExceptionHandler(NullPointerQuotationException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<HashMap<String, ErrorResponseDTO>> nullPointerQuotationException(NullPointerQuotationException e) {
        HashMap<String, ErrorResponseDTO> responseBody = new HashMap<String, ErrorResponseDTO>();
        responseBody.put("result", new ErrorResponseDTO(e));
        return ResponseEntity.status(e.getCode()).body(responseBody);
    }

    @ExceptionHandler(InValidRequestHeaderException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<HashMap<String, ErrorResponseDTO>> inValidRequestHeaderException(InValidRequestHeaderException e) {
        HashMap<String, ErrorResponseDTO> responseBody = new HashMap<String, ErrorResponseDTO>();
        responseBody.put("result", new ErrorResponseDTO(e));
        return ResponseEntity.status(e.getCode()).body(responseBody);
    }
    @ExceptionHandler(UnAuthorizationJwtException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<HashMap<String, ErrorResponseDTO>> unAuthorizationJwtException(UnAuthorizationJwtException e) {
        HashMap<String, ErrorResponseDTO> responseBody = new HashMap<String, ErrorResponseDTO>();
        responseBody.put("result", new ErrorResponseDTO(e));
        return ResponseEntity.status(e.getCode()).body(responseBody);
    }
}
