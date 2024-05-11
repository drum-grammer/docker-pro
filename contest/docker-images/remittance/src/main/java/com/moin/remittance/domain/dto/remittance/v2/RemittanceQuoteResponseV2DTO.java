package com.moin.remittance.domain.dto.remittance.v2;

import com.moin.remittance.domain.entity.remittance.v2.RemittanceQuoteEntityV2;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.UUID;

/* 환율을 적용한 송금 견적서 */
@Data
@Builder
@Schema(description = "SUCCESS_GET_REMITTANCE_QUOTE: 송금 견적서")
public class RemittanceQuoteResponseV2DTO {
    // 채번한 송금 견적서 아이디
    private UUID quoteId;

    // targetCurrency가 미국이면 미국 환율, 일본이면 일본 환율 금액
    private BigDecimal exchangeRate;

    // 만료 시간
    private OffsetDateTime expireTime;

    // USD 송금액
    private BigDecimal usdAmount;

    // 받는 금액
    private BigDecimal targetAmount;

    public static RemittanceQuoteResponseV2DTO of(RemittanceQuoteEntityV2 entity) {
        return RemittanceQuoteResponseV2DTO.builder()
                .quoteId(entity.getQuoteId())
                .exchangeRate(entity.getExchangeRate())
                .expireTime(entity.getExpireTime())
                .usdAmount(entity.getUsdAmount())
                .targetAmount(entity.getTargetAmount())
                .build();
    }
}
