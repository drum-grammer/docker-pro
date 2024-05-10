package com.moin.remittance.domain.dto.requestparams;

import io.swagger.v3.oas.annotations.Hidden;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Getter;

@Getter
public class RemittanceQuoteRequestParamsDTO {

    @Hidden
    private final String codes;

    @Positive(message = "양수로 입력하세요.")
    @NotNull(message = "금액을 입력하세요.")
    @Schema(name = "amount", description = "원화 송금액")
    private final long amount;

    @NotEmpty(message = "유효한 타겟 통화를 입력하세요.")
    @Schema(name = "targetCurrency", description = "환전할 통화 코드를 입력하세요 .")
    private final String targetCurrency;

    public RemittanceQuoteRequestParamsDTO(String targetCurrency, long amount) {
        this.codes = !targetCurrency.equals("USD") ? "FRX.KRW" + targetCurrency + ",FRX.KRWUSD" : "FRX.KRW" + targetCurrency;
        this.amount = amount;
        this.targetCurrency = targetCurrency;
    }
}
