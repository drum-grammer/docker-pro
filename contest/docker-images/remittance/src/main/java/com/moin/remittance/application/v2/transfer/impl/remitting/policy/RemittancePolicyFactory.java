package com.moin.remittance.application.v2.transfer.impl.remitting.policy;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
@Getter
@RequiredArgsConstructor
public class RemittancePolicyFactory {
    private final RemittancePolicy remittancePolicy;

    public BigDecimal getLimitAmountByIdType(String idType) {
        return remittancePolicy.getLimitAmountByIdType(idType);
    }
}
