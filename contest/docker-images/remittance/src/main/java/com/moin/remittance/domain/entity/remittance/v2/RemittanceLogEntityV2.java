package com.moin.remittance.domain.entity.remittance.v2;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.UUID;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Entity
@Table(name = "remittance_log_v2")
public class RemittanceLogEntityV2 {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "log_id")
    private UUID logId;

    // 송금 할 금액(원화)
    @Column(name = "source_amount", nullable = false)
    private long sourceAmount;

    // 적용된 수수료
    @Column(name = "fee", nullable = false)
    private BigDecimal fee;

    // USD 환율(base price)
    @Column(name = "usd_exchange_rate", nullable = false)
    private BigDecimal usdExchangeRate;

    // USD 송금액
    @Column(name = "usd_amount", nullable = false)
    private BigDecimal usdAmount;

    // 받는 환율 정보
    @Column(name = "target_currency", nullable = false)
    private String targetCurrency;

    // targetCurrency가 미국이면 미국 환율 일본이면 일본 환율
    @Column(name = "exchange_rate", nullable = false)
    private BigDecimal exchangeRate;

    // 받는 금액
    @Column(name = "target_amount", nullable = false)
    private BigDecimal targetAmount;


    @Column(name = "requested_date", nullable = false)
    private OffsetDateTime requestedDate;

    @Column(name = "user_id", nullable = false)
    private String userId;

    @Override
    public String toString() {
        return "RemittanceLogEntityV2 {" + "\n" +
                "\tlogId: " + logId + ",\n" +
                "\tsourceAmount: " + sourceAmount + ",\n" +
                "\tfee: " + fee + ",\n" +
                "\tusdExchangeRate: " + usdExchangeRate + ",\n" +
                "\tusdAmount: " + usdAmount + ",\n" +
                "\ttargetCurrency: '" + targetCurrency + '\'' + ",\n" +
                "\ttargetAmount: " + targetAmount + ",\n" +
                "\texchangeRate: " + exchangeRate + ",\n" +
                "\texpireTime: " + requestedDate + ",\n" +
                "\tuserId: '" + userId + '\'' + ",\n" +
                '}';
    }
}
