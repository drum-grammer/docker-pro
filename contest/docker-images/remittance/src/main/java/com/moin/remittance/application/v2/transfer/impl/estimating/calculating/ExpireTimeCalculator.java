package com.moin.remittance.application.v2.transfer.impl.estimating.calculating;

import org.springframework.stereotype.Component;

import java.time.OffsetDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

@Component
public class ExpireTimeCalculator {
    public OffsetDateTime calculateExpireTime(int minute) throws DateTimeParseException {
        // 현재 시각
        OffsetDateTime currentDateTime = OffsetDateTime.now();
        System.out.println("현재 시각: " + currentDateTime);

        // 10분 후의 시각 계산
        OffsetDateTime futureDateTime = currentDateTime.plusMinutes(minute);
        System.out.println("10분 후의 시각: " + futureDateTime);

        // 원하는 형식으로 출력 (예: "yyyy-MM-dd HH:mm:ss")
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        String formattedFutureDateTime = futureDateTime.format(formatter);
        System.out.println("10분 후의 시각 (형식 지정): " + formattedFutureDateTime);

        return futureDateTime;
    }
}
