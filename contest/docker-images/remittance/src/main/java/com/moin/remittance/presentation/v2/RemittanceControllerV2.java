package com.moin.remittance.presentation.v2;

import com.moin.remittance.application.v2.transfer.RemittanceServiceV2;
import com.moin.remittance.domain.dto.remittance.v2.RemittanceQuoteResponseV2DTO;
import com.moin.remittance.domain.dto.remittance.v2.TransactionLogV2DTO;
import com.moin.remittance.domain.dto.requestbody.v2.RemittanceAcceptRequestBodyV2DTO;
import com.moin.remittance.domain.dto.responsebody.HttpResponseBody;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import static com.moin.remittance.domain.vo.HttpResponseCode.*;


@RestController
@RequiredArgsConstructor
@Tag(name = "RemittanceControllerV2", description = "송금 관련 컨트롤러: 송금 견적서 가져오기, 송금 요청, 회원의 송금 거래 이력 가져오기")
@RequestMapping(value = "/api/v2/transfer")
public class RemittanceControllerV2 {

    private final RemittanceServiceV2 remittanceService;

    /**
     * EndPoint: GET /api/v2/transfer/quote
     * 기능: 두나무 오픈 API 스크래핑해서 환율이 적용된 송금 견적서를 리턴
     *
     * @RequestParam RemittanceQuoteRequestParamsDTO: 송금 견적서 요청 파라미터
     * @Param String amount: 원화
     * @Param String targetCurrency: 타겟 통화
     **/
    @Operation(summary = "두나무 오픈 API 스크래핑해서 환율이 적용된 송금 견적서를 리턴")
    @GetMapping(value = "/quote", headers = "API-Version=2")
    public ResponseEntity<HttpResponseBody<RemittanceQuoteResponseV2DTO>> getRemittanceQuoteV2(
            @RequestParam
            @Parameter(name="amount", description = "원화 송금액", required = true)

            @Positive(message = "양수로 입력하세요.")
            @NotNull(message = "금액을 입력하세요.")
            long amount,
            @RequestParam
            @Parameter(name="targetCurrency", description = "환전할 통화 코드를 입력하세요.", required = true)

            @NotEmpty(message = "유효한 타겟 통화를 입력하세요.")
            String targetCurrency
    ) {
        String userId = SecurityContextHolder.getContext().getAuthentication().getName();

        return ResponseEntity.status(SUCCESS_GET_REMITTANCE_QUOTE.getStatusCode()).body(
                HttpResponseBody.<RemittanceQuoteResponseV2DTO>builder()
                        .statusCode(SUCCESS_GET_REMITTANCE_QUOTE.getStatusCode())
                        .message(SUCCESS_GET_REMITTANCE_QUOTE.getMessage())
                        .codeName(SUCCESS_GET_REMITTANCE_QUOTE.getCodeName())
                        .data(remittanceService.getRemittanceQuoteV2(amount, targetCurrency, userId))
                        .build()
        );
    }

    // 송금 접수 요청
    @Operation(summary = "송금 접수 요청")
    @PostMapping(value = "/request", headers = "API-Version=2")
    public ResponseEntity<HttpResponseBody<?>> requestRemittanceAccept(@RequestBody RemittanceAcceptRequestBodyV2DTO requestBody) {

        String userId = SecurityContextHolder.getContext().getAuthentication().getName();
        String idType = SecurityContextHolder.getContext().getAuthentication()
                .getAuthorities()
                .iterator()
                .next()
                .getAuthority();

        // 송금 접수 요청
        remittanceService.requestRemittanceAccept(requestBody.getQuoteId(), userId, idType);

        // Response 처리
        return ResponseEntity.status(SUCCESS_REQUEST_REMITTANCE_ACCEPT.getStatusCode()).body(
                HttpResponseBody.builder()
                        .statusCode(SUCCESS_REQUEST_REMITTANCE_ACCEPT.getStatusCode())
                        .message(SUCCESS_REQUEST_REMITTANCE_ACCEPT.getMessage())
                        .codeName(SUCCESS_REQUEST_REMITTANCE_ACCEPT.getCodeName())
                        .build()
        );
    }

    /**
     * EndPoint: GET /api/v2/transfer/list
     * 기능: 회원의 거래 이력을 리턴하는 API
     *
     * @Param MemberDTO: 회원 정보 -> JWT 파싱해서 정보 얻기
     **/
    @Operation(summary = "회원의 거래 이력을 리턴")
    @GetMapping(value = "/list", headers = "API-Version=2")
    public ResponseEntity<HttpResponseBody<TransactionLogV2DTO>> getRemittanceLog() {

        String userId = SecurityContextHolder.getContext().getAuthentication().getName();

        // Response 처리
        return ResponseEntity.status(SUCCESS_GET_REMITTANCE_LOG.getStatusCode()).body(
                HttpResponseBody.<TransactionLogV2DTO>builder()
                        .statusCode(SUCCESS_GET_REMITTANCE_LOG.getStatusCode())
                        .message(SUCCESS_GET_REMITTANCE_LOG.getMessage())
                        .codeName(SUCCESS_GET_REMITTANCE_LOG.getCodeName())
                        .data(remittanceService.getRemittanceLogList(userId))
                        .build()
        );
    }
}
