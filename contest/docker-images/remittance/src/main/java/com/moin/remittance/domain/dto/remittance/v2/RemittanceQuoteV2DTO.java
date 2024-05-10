package com.moin.remittance.domain.dto.remittance.v2;

import com.moin.remittance.domain.entity.remittance.v2.RemittanceQuoteEntityV2;
import com.moin.remittance.exception.NullPointerQuotationException;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;

import java.math.BigDecimal;
import java.time.OffsetDateTime;

import static com.moin.remittance.domain.vo.HttpResponseCode.BAD_NOT_MATCH_QUOTATION;

@Builder
@Getter
@Schema(description = "송금 견적서")
public class RemittanceQuoteV2DTO {
    // 송금 할 금액(원화)
    private long sourceAmount;

    // 수수료 = 보내는금액(amount: 원화) * 수수료율 + 고정 수수료
    private BigDecimal fee;

    // USD 환율(base price)
    private BigDecimal usdExchangeRate;

    // USD 송금액 = 달러로 환산된 금액
    private BigDecimal usdAmount;

    // 받는 환율 정보 = currenyCode
    private String targetCurrency;

    // targetCurrency가 미국이면 미국 환율 일본이면 일본 환율 = basePrice
    private BigDecimal exchangeRate;

    // 받는 금액
    private BigDecimal targetAmount;

    // 만료 기간
    private OffsetDateTime expireTime;

    // 요청자(유저 아이디를 저장)
    private String userId;

    public RemittanceQuoteEntityV2 toEntity (RemittanceQuoteV2DTO dto) {
        return RemittanceQuoteEntityV2.builder()
                .sourceAmount(dto.getSourceAmount())// 원화
                .fee(new BigDecimal(String.valueOf(dto.getFee())))// 수수료
                .usdExchangeRate(dto.getUsdExchangeRate())
                .usdAmount(dto.getUsdAmount())// USD 송금액
                .targetCurrency(dto.getTargetCurrency())
                .targetAmount(dto.getTargetAmount())// 받는 금액
                .exchangeRate(dto.getExchangeRate())
                .expireTime(dto.getExpireTime())// 송금 견적서 만료 기간
                .userId(dto.getUserId())
                .build();
    }

    public static RemittanceQuoteV2DTO of (RemittanceQuoteEntityV2 entity) {
        if(entity == null) {
            throw new NullPointerQuotationException(BAD_NOT_MATCH_QUOTATION);
        }
        return RemittanceQuoteV2DTO.builder()
                .sourceAmount(entity.getSourceAmount())// 원화
                .fee(new BigDecimal(String.valueOf(entity.getFee())))// 수수료
                .usdExchangeRate(entity.getUsdExchangeRate())
                .usdAmount(entity.getUsdAmount())// USD 송금액
                .targetCurrency(entity.getTargetCurrency())
                .targetAmount(entity.getTargetAmount())// 받는 금액
                .exchangeRate(entity.getExchangeRate())
                .expireTime(entity.getExpireTime())// 송금 견적서 만료 기간
                .build();
    }


}
