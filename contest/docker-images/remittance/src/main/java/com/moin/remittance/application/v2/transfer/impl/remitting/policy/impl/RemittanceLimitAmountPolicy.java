package com.moin.remittance.application.v2.transfer.impl.remitting.policy.impl;

import com.moin.remittance.application.v2.transfer.impl.remitting.policy.RemittancePolicy;
import com.moin.remittance.exception.InValidPatternTypeException;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

import static com.moin.remittance.domain.vo.HttpResponseCode.BAD_NOT_FOUND_ID_TYPE;

@Component
public class RemittanceLimitAmountPolicy implements RemittancePolicy {


    @Override
    public BigDecimal getLimitAmountByIdType(String userType) {

        /*
         * 유저 타입을 코어단계에서 필터링하는 방법으로 옮길 예정
         * */
        if (!userType.equalsIgnoreCase("REG_NO") && !userType.equalsIgnoreCase("BUSINESS_NO")) {
            throw new InValidPatternTypeException(BAD_NOT_FOUND_ID_TYPE);
        }

        int limitPrice = 0;

        if (userType.equalsIgnoreCase("REG_NO")) {
            limitPrice = 1000;
        }

        if (userType.equalsIgnoreCase("BUSINESS_NO")) {
            limitPrice = 5000;
        }

        return new BigDecimal(limitPrice);
    }

}
