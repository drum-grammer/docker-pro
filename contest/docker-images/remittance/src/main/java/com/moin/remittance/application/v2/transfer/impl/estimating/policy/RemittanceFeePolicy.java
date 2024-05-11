package com.moin.remittance.application.v2.transfer.impl.estimating.policy;

import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.math.RoundingMode;

@Component
public class RemittanceFeePolicy implements FeePolicy{

    @Override
    public BigDecimal calculateRemittanceFee(long amount) {
        final double HUNDRED_EXCEED_FEE = 3000.0;
        final double HUNDRED_EXCEED_FEE_RATE = 0.1;

        final double HUNDRED_DOWN_FEE = 1000.0;
        final double HUNDRED_DOWN_FEE_RATE = 0.2;

        double sourceAmount = (double) amount;

        double targetFee = (sourceAmount > 0.0 && sourceAmount <= 1000000.0)?
                (sourceAmount * HUNDRED_DOWN_FEE_RATE) + HUNDRED_DOWN_FEE : (sourceAmount * HUNDRED_EXCEED_FEE_RATE) + HUNDRED_EXCEED_FEE;

        return new BigDecimal(targetFee).setScale(1, RoundingMode.HALF_UP);
    }
}
