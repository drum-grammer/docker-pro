package com.moin.remittance.presentation.v1;

import com.moin.remittance.domain.dto.responsebody.HttpResponseBody;
import com.moin.remittance.domain.dto.remittance.v1.TransactionLogDTO;
import com.moin.remittance.domain.dto.remittance.v1.RemittanceQuoteResponseDTO;
import com.moin.remittance.domain.dto.requestbody.v1.RemittanceAcceptRequestBodyDTO;
import com.moin.remittance.domain.dto.requestparams.RemittanceQuoteRequestParamsDTO;
import com.moin.remittance.application.v1.RemittanceServiceV1;
import io.swagger.v3.oas.annotations.Hidden;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static com.moin.remittance.domain.vo.HttpResponseCode.*;


@RestController
@RequiredArgsConstructor
@Hidden
@RequestMapping(value = "/api/v1/transfer")
public class RemittanceControllerV1 {

    private final RemittanceServiceV1 remittanceServiceV1;

    // 송금 견적서 호출
    @GetMapping("/quote")
    public ResponseEntity<HttpResponseBody> getRemittanceQuote(@RequestHeader("Authorization") HttpHeaders header,
                                                               @Valid RemittanceQuoteRequestParamsDTO requestParams) {
        String accessToken = header.getFirst("Authorization").split("Bearer ")[1];
        // 송금 견적서 조회
        RemittanceQuoteResponseDTO remittanceQuoteDTO = remittanceServiceV1.getRemittanceQuote(requestParams);

        // Response 처리
        return ResponseEntity.status(SUCCESS_GET_REMITTANCE_QUOTE.getStatusCode()).body(
                HttpResponseBody.<RemittanceQuoteResponseDTO>builder()
                        .statusCode(SUCCESS_GET_REMITTANCE_QUOTE.getStatusCode())
                        .message(SUCCESS_GET_REMITTANCE_QUOTE.getMessage())
                        .codeName(SUCCESS_GET_REMITTANCE_QUOTE.getCodeName())
                        .data(remittanceQuoteDTO)
                        .build()
        );
    }

    // 송금 접수 요청
    @PostMapping(value = "/request")
    public ResponseEntity<HttpResponseBody> requestRemittanceAccept(@RequestBody RemittanceAcceptRequestBodyDTO requestBody,
                                                                    @RequestHeader("Authorization") HttpHeaders header) {
        // 송금 접수 요청
        remittanceServiceV1.requestRemittanceAccept(requestBody.getQuoteId(), "test@test.com");


        // Response 처리
        return ResponseEntity.status(SUCCESS_REQUEST_REMITTANCE_ACCEPT.getStatusCode()).body(
                HttpResponseBody.builder()
                        .statusCode(SUCCESS_REQUEST_REMITTANCE_ACCEPT.getStatusCode())
                        .message(SUCCESS_REQUEST_REMITTANCE_ACCEPT.getMessage())
                        .build()
        );
    }

    // 회원의 송금 거래 이력
    @GetMapping(value = "/list")
    public ResponseEntity<HttpResponseBody> getRemittanceLog(@RequestHeader("Authorization") HttpHeaders header) {
        String accessToken = header.getFirst("Authorization").split("Bearer ")[1];
        String userId = "test@test.com";
        TransactionLogDTO log = remittanceServiceV1.getRemittanceLogList(userId);

        // Response 처리
        return ResponseEntity.status(SUCCESS_GET_REMITTANCE_LOG.getStatusCode()).body(
                HttpResponseBody.<TransactionLogDTO>builder()
                        .statusCode(SUCCESS_GET_REMITTANCE_LOG.getStatusCode())
                        .message(SUCCESS_GET_REMITTANCE_LOG.getMessage())
                        .codeName(SUCCESS_GET_REMITTANCE_LOG.getCodeName())
                        .data(log)
                        .build()
        );
    }
}
