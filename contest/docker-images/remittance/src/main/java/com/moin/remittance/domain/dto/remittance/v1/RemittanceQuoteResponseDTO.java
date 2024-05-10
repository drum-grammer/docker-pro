package com.moin.remittance.domain.dto.remittance.v1;

import com.moin.remittance.domain.entity.remittance.v1.RemittanceQuoteEntity;
import lombok.*;

import java.time.OffsetDateTime;

/* 환율을 적용한 송금 견적서 */
@Data
@Builder
public class RemittanceQuoteResponseDTO {
    // 채번한 송금 견적서 아이디
    private Long quoteId;

    // targetCurrency가 미국이면 미국 환율, 일본이면 일본 환율 금액
    private double exchangeRate;

    // 만료 시간
    private OffsetDateTime expireTime;

    // 받는 금액
    private double targetAmount;


    public static RemittanceQuoteResponseDTO of(RemittanceQuoteEntity entity) {
        return RemittanceQuoteResponseDTO.builder()
                .quoteId(entity.getQuoteId())
                .exchangeRate(entity.getExchangeRate())
                .expireTime(entity.getExpireTime())
                .targetAmount(entity.getTargetAmount())
                .build();
    }
}
