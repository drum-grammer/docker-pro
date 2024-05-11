package com.moin.remittance.domain.dto.remittance.v1;

import com.moin.remittance.domain.dto.remittance.v1.RemittanceQuoteDTO;
import lombok.Data;

import java.time.OffsetDateTime;

@Data
public class RemittanceLogDTO {
    // 유저 이름
    private String userId;

    // 송금 견적서 dto
    private RemittanceQuoteDTO remittanceQuoteDTO;

    // 송금 접수 요청 날짜
    private OffsetDateTime requestedDate;

    public RemittanceLogDTO (RemittanceQuoteDTO dto, String userId, OffsetDateTime requestedDate){
        this.remittanceQuoteDTO = dto;
        this.userId = userId;
        this.requestedDate = requestedDate;
    }

    public RemittanceLogDTO () {

    }
}
