package com.moin.remittance.domain.entity.remittance.v1;

import com.moin.remittance.domain.dto.remittance.v1.RemittanceLogDTO;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.OffsetDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Entity
@Table(name = "remittance_log")
public class RemittanceLogEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "log_id")
    private Long logId;

    // 송금 할 금액(원화)
    @Column(name = "source_amount", nullable = false)
    private long sourceAmount;

    // 적용된 수수료
    @Column(name = "fee", nullable = false)
    private double fee;

    // USD 환율(base price)
    @Column(name = "usd_exchange_rate", nullable = false)
    private double usdExchangeRate;

    // USD 송금액
    @Column(name = "usd_amount", nullable = false)
    private double usdAmount;

    // 받는 환율 정보
    @Column(name = "target_currency", nullable = false)
    private String targetCurrency;

    // targetCurrency가 미국이면 미국 환율 일본이면 일본 환율
    @Column(name = "exchange_rate", nullable = false)
    private double exchangeRate;

    // 받는 금액
    @Column(name = "target_amount", nullable = false)
    private double targetAmount;


    @Column(name = "requested_date", nullable = false)
    private OffsetDateTime requestedDate;


    @Column(name = "user_id", nullable = false)
    private String userId;

    public RemittanceLogEntity toEntity (RemittanceLogDTO dto) {
        return RemittanceLogEntity.builder()
                .sourceAmount(dto.getRemittanceQuoteDTO().getSourceAmount())
                .fee(dto.getRemittanceQuoteDTO().getFee())
                .usdExchangeRate(dto.getRemittanceQuoteDTO().getUsdExchangeRate())
                .usdAmount(dto.getRemittanceQuoteDTO().getUsdAmount())
                .targetCurrency(dto.getRemittanceQuoteDTO().getTargetCurrency())
                .targetAmount(dto.getRemittanceQuoteDTO().getTargetAmount())
                .exchangeRate(dto.getRemittanceQuoteDTO().getExchangeRate())
                .requestedDate(dto.getRequestedDate())
                .userId(dto.getUserId())
                .build();
    }
}
