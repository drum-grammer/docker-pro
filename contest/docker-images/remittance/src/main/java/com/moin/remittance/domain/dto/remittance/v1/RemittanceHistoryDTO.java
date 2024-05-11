package com.moin.remittance.domain.dto.remittance.v1;

import lombok.Data;

import java.time.OffsetDateTime;

@Data
public class RemittanceHistoryDTO {
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

    // 유저 이름
    private String userId;

    // 송금 접수 요청 날짜
    private OffsetDateTime requestedDate;
}
