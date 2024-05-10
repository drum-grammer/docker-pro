package com.moin.remittance.domain.dto.remittance.v1;

import com.moin.remittance.domain.entity.remittance.v1.RemittanceQuoteEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.OffsetDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RemittanceQuoteDTO {
    // 송금 할 금액(원화)
    private long sourceAmount;

    // 수수료 = 보내는금액(amount: 원화) * 수수료율 + 고정 수수료
    private double fee;

    // USD 환율(base price)
    private double usdExchangeRate;

    // USD 송금액 = 달러로 환산된 금액
    private double usdAmount;

    // 받는 환율 정보 = currenyCode
    private String targetCurrency;

    // targetCurrency가 미국이면 미국 환율 일본이면 일본 환율 = basePrice
    private double exchangeRate;

    // 받는 금액
    private double targetAmount;

    // 만료 기간
    private OffsetDateTime expireTime;

    public RemittanceQuoteDTO(ExchangeRateInfoDTO usdDTO, ExchangeRateInfoDTO targetDTO) {
        this.usdExchangeRate = usdDTO.getBasePrice();
        this.targetCurrency = targetDTO.getCurrencyCode();
        this.exchangeRate = targetDTO.getBasePrice();
    }

    public RemittanceQuoteEntity toEntity (RemittanceQuoteDTO dto) {
        return RemittanceQuoteEntity.builder()
                .sourceAmount(dto.getSourceAmount())// 원화
                .fee(dto.getFee())// 수수료
                .usdExchangeRate(dto.getUsdExchangeRate())
                .usdAmount(dto.getUsdAmount())// USD 송금액
                .targetCurrency(dto.getTargetCurrency())
                .targetAmount(dto.getTargetAmount())// 받는 금액
                .exchangeRate(dto.getExchangeRate())
                .expireTime(dto.getExpireTime())// 송금 견적서 만료 기간
                .build();
    }


}
