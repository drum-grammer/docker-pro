package com.moin.remittance.util;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.OffsetDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Currency;

import static java.lang.Math.round;

public class ExchangeRateCalculatorCalculatorTest {
    @Test
    @DisplayName("환율 계산")
    public void calculateExchangeRate() {
        final int HUNDRED_EXCEED_FEE = 3000;
        final double HUNDRED_EXCEED_FEE_RATE = 0.1;

        final double HUNDRED_DOWN_FEE = 1000;
        final double HUNDRED_DOWN_FEE_RATE = 0.2;

        int amount = 5000;

        int usdCurrencyUnit = 1;
        int jpyCurrenyUnit = 100;

        BigDecimal krw = new BigDecimal((double)amount);
        BigDecimal usdToKrwExchangeRate = new BigDecimal(1338.0 / usdCurrencyUnit);
        BigDecimal jpyTokrwExchangeRate = new BigDecimal(903.54 / jpyCurrenyUnit);

        System.out.println("usdToKrwExchangeRate: " + usdToKrwExchangeRate);
        System.out.println("jpyTokrwExchangeRate: " + jpyTokrwExchangeRate);

        int decimalPlaces = Currency.getInstance("USD").getDefaultFractionDigits();
        BigDecimal usdAmount = krw.divide(usdToKrwExchangeRate, decimalPlaces, BigDecimal.ROUND_HALF_UP);
        BigDecimal jpyAmount = krw.divide(jpyTokrwExchangeRate, decimalPlaces, BigDecimal.ROUND_HALF_UP);

        System.out.println("usdAmount: " + usdAmount);
        System.out.println("jpyAmount: " + jpyAmount);

        double targetAmount = Double.parseDouble(String.valueOf(usdAmount));
        System.out.println("targetAmount: " + targetAmount);
    }

    public static void calculateExpireTime() throws DateTimeParseException {
        String modifiedAt = String.valueOf(OffsetDateTime.now());
        int minute = 10;
        System.out.println(modifiedAt);
        // 현재 시각
        OffsetDateTime currentDateTime = OffsetDateTime.parse(modifiedAt);
        System.out.println("현재 시각: " + currentDateTime);

        // 30분 후의 시각 계산
        OffsetDateTime futureDateTime = currentDateTime.plusMinutes(minute);
        System.out.println("10분 후의 시각: " + futureDateTime);

        // 원하는 형식으로 출력 (예: "yyyy-MM-dd HH:mm:ss")
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        String formattedFutureDateTime = futureDateTime.format(formatter);
        System.out.println("30분 후의 시각 (형식 지정): " + formattedFutureDateTime);


    }
}
