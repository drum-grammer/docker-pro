package com.moin.remittance.application.v2.transfer.impl.estimating.policy;

import java.math.BigDecimal;

@FunctionalInterface
public interface FeePolicy {
    BigDecimal calculateRemittanceFee(long amount);
}
