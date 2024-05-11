package com.moin.remittance.domain.dto.requestbody.v2;

import lombok.Getter;

import java.util.UUID;

@Getter
public class RemittanceAcceptRequestBodyV2DTO {
    private UUID quoteId;
}
