package com.moin.remittance.domain.dto.remittance.v1;

import lombok.Data;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;

@Data
public class TransactionLogDTO {
    private String userId;
    private String name;
    private int todayTransferCount;
    private double todayTransferUsdAmount;
    private List<RemittanceHistoryDTO> history;

    public TransactionLogDTO(List<RemittanceHistoryDTO> history, String userId, String name) {
        this.userId = userId;
        this.name = name;
        this.todayTransferCount = history.size();
        for(RemittanceHistoryDTO dto : history) {
            this.todayTransferUsdAmount += dto.getUsdAmount();
        }
        // 소수점 두째 자리 반올림
        this.todayTransferUsdAmount = Double.parseDouble(String.valueOf(new BigDecimal(this.todayTransferUsdAmount).setScale(2, RoundingMode.HALF_UP)));

        this.history = history;
    }
}
