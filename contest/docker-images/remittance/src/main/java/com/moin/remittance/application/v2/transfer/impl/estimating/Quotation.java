package com.moin.remittance.application.v2.transfer.impl.estimating;

import com.moin.remittance.domain.dto.remittance.v2.ExchangeRateInfoDTO;
import com.moin.remittance.domain.dto.remittance.v2.RemittanceQuoteV2DTO;
import com.moin.remittance.exception.NegativeNumberException;
import org.springframework.stereotype.Component;

@Component
public record Quotation(QuotationOffice quotationOffice, QuotationFactory quotationFactory) {

    public RemittanceQuoteV2DTO createQuotation(long sourceAmount, ExchangeRateInfoDTO usd, ExchangeRateInfoDTO target, String userId) throws NegativeNumberException {
        return quotationFactory.estimating(sourceAmount, usd, target, userId);
    }
}
