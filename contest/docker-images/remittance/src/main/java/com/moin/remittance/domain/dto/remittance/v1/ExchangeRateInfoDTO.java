package com.moin.remittance.domain.dto.remittance.v1;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;


/* 외부 API로 받은 환율 정보 데이터*/
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class ExchangeRateInfoDTO {

    @JsonProperty
    private String code;

    @JsonProperty
    private String currencyCode;

    @JsonProperty
    private double basePrice;

    @JsonProperty
    private int currencyUnit;

    @JsonProperty
    private String modifiedAt;
}
