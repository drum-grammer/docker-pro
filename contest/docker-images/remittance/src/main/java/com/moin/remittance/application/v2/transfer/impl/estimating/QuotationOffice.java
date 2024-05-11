package com.moin.remittance.application.v2.transfer.impl.estimating;

import com.moin.remittance.application.v2.transfer.impl.estimating.policy.FeePolicy;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;


@Component
@RequiredArgsConstructor
public class QuotationOffice {
    private final FeePolicy feePolicy;

    public BigDecimal getRemittanceFee(long amount) {
        return feePolicy.calculateRemittanceFee(amount);
    }
}
