package com.moin.remittance.application.v2.transfer.impl.remitting.policy;

import java.math.BigDecimal;
import java.time.OffsetDateTime;

@FunctionalInterface
public interface RemittancePolicy {
    BigDecimal getLimitAmountByIdType(String userType);
}
