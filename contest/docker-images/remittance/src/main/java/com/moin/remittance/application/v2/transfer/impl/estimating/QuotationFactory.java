package com.moin.remittance.application.v2.transfer.impl.estimating;

import com.moin.remittance.application.v2.transfer.impl.estimating.calculating.ExchangeRateCalculator;
import com.moin.remittance.application.v2.transfer.impl.estimating.calculating.ExpireTimeCalculator;
import com.moin.remittance.application.v2.transfer.impl.estimating.policy.FeePolicyFactory;
import com.moin.remittance.domain.dto.remittance.v2.ExchangeRateInfoDTO;
import com.moin.remittance.domain.dto.remittance.v2.RemittanceQuoteV2DTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
@RequiredArgsConstructor
public class QuotationFactory {
    private final FeePolicyFactory feePolicyFactory;
    private final ExchangeRateCalculator exchangeRateCalculator;
    private final ExpireTimeCalculator expireTimeCalculator;

    /**
     * 송금 견적서 찍어내는 메소드
     * - 만료 기간 = 생성시간 + 10분
     * - 수수료 = 보내는금액(amount: 원화) * 수수료율 + 고정 수수료 => 소수점 반올림(int 형)
     * - 받는 금액 = (보내는 금액 - 수수료) / 환율
     *
     * @param sourceAmount 원화
     * @param usd          USD 환율 정보
     * @param target       타겟 통화 환율 정보
     */
    public RemittanceQuoteV2DTO estimating(long sourceAmount, ExchangeRateInfoDTO usd, ExchangeRateInfoDTO target, String userId) {
        BigDecimal fee = feePolicyFactory.getAppliedFeeFromPolicy(sourceAmount);

        return RemittanceQuoteV2DTO.builder()// 송금 견적서 DTO
                .sourceAmount(sourceAmount) // 원화
                .fee(fee) // 수수료
                .usdExchangeRate(usd.getBasePrice()) // USD 환율
                .usdAmount(exchangeRateCalculator.calculateExchangeRate(
                        sourceAmount,
                        fee,
                        usd.getCurrencyUnit(),
                        usd.getBasePrice(),
                        usd.getCurrencyCode())
                ) // USD 송금액
                .targetCurrency(target.getCurrencyCode()) // 타겟 통화
                .exchangeRate(target.getBasePrice()) // 환율
                .targetAmount(exchangeRateCalculator.calculateExchangeRate(
                        sourceAmount,
                        fee,
                        target.getCurrencyUnit(),
                        target.getBasePrice(),
                        target.getCurrencyCode())
                ) // 받는 금액
                .expireTime(expireTimeCalculator.calculateExpireTime(10)) // 송금 견적서 만료 기간
                .userId(userId)
                .build();
    }


}
