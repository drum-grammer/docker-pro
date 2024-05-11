package com.moin.remittance.domain.dto.remittance.v2;

import com.moin.remittance.domain.entity.remittance.v2.RemittanceLogEntityV2;
import lombok.Builder;
import lombok.Getter;

import java.time.OffsetDateTime;

@Builder
@Getter
public class RemittanceLogV2DTO {
    // 유저 이름
    private String userId;

    // 송금 견적서 dto
    private RemittanceQuoteV2DTO remittanceQuoteV2DTO;

    // 송금 접수 요청 날짜
    private OffsetDateTime requestedDate;

    public RemittanceLogEntityV2 toEntity (RemittanceLogV2DTO dto) {
        return RemittanceLogEntityV2.builder()
                .sourceAmount(dto.getRemittanceQuoteV2DTO().getSourceAmount())
                .fee(dto.getRemittanceQuoteV2DTO().getFee())
                .usdExchangeRate(dto.getRemittanceQuoteV2DTO().getUsdExchangeRate())
                .usdAmount(dto.getRemittanceQuoteV2DTO().getUsdAmount())
                .targetCurrency(dto.getRemittanceQuoteV2DTO().getTargetCurrency())
                .targetAmount(dto.getRemittanceQuoteV2DTO().getTargetAmount())
                .exchangeRate(dto.getRemittanceQuoteV2DTO().getExchangeRate())
                .requestedDate(dto.getRequestedDate())
                .userId(dto.getUserId())
                .build();
    }
}
