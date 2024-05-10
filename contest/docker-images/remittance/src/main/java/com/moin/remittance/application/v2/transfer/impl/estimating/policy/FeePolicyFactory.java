package com.moin.remittance.application.v2.transfer.impl.estimating.policy;

import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
public record FeePolicyFactory(FeePolicy feePolicy) {
    public BigDecimal getAppliedFeeFromPolicy(long sourceAmount) {
        return feePolicy.calculateRemittanceFee(sourceAmount);
    }
}
