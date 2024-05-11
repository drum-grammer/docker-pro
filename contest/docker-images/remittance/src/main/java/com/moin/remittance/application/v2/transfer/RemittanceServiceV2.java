package com.moin.remittance.application.v2.transfer;

import com.moin.remittance.domain.dto.remittance.v2.RemittanceQuoteResponseV2DTO;
import com.moin.remittance.domain.dto.remittance.v2.TransactionLogV2DTO;
import com.moin.remittance.domain.dto.requestparams.RemittanceQuoteRequestParamsDTO;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public interface RemittanceServiceV2 {

    /**
     * @Parameter RemittanceQuoteRequestParamsDTO: 송금 견적서 요청 파라미터
     * @Param String amount: 원화
     * @Param String targetCurrency: 타겟 통화
     *
     * @Return RemittanceQuoteResponseDTO: 송금 견적서
     */
    RemittanceQuoteResponseV2DTO getRemittanceQuoteV2(long sourceAmount, String targetCurrency, String userId);

    /**
     * @Parameter long quoteId: 견적서 id
     * @Parameter String userId: 유저의 아이디
     */
    void requestRemittanceAccept(UUID quoteId, String userId, String idType);

    /**
     * @Return 송금 견적서
     */
    TransactionLogV2DTO getRemittanceLogList(String userId);

}
