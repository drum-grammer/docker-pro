package com.moin.remittance.application.v1.util;

import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.OffsetDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Currency;

public class ExchangeRateCalculator {
    // 수수료 적용한 달러 환산
    public static double calculateExchangeRate(long amount, double currencyUnit, double basePrice, String currencyCode) {
        double sourceAmount = (double) amount;
        double fee = calculateFee(amount);

        BigDecimal krw = new BigDecimal(sourceAmount -  fee);
        BigDecimal targetCurrencyToKrwExchangeRate = new BigDecimal(basePrice / currencyUnit).setScale(4, RoundingMode.HALF_UP);// 소수점 넷째자리에서 반올림
        System.out.println(targetCurrencyToKrwExchangeRate);

        int decimalPlaces = Currency.getInstance(currencyCode).getDefaultFractionDigits();

        BigDecimal targetAmount = krw.divide(targetCurrencyToKrwExchangeRate, decimalPlaces, RoundingMode.HALF_UP);// 자릿수 getDefaultFractionDigits

        return Double.parseDouble(String.valueOf(targetAmount));
    }

    // 수수료 적용하지 않은 달러 환산
    public static double calculateExchangeRate(long amount, double usdExchangeRate) {
        double sourceAmount = (double) amount;

        BigDecimal krw = new BigDecimal(sourceAmount);
        BigDecimal targetCurrencyToKrwExchangeRate = new BigDecimal(usdExchangeRate);

        int decimalPlaces = Currency.getInstance("USD").getDefaultFractionDigits();

        BigDecimal targetAmount = krw.divide(targetCurrencyToKrwExchangeRate, decimalPlaces, RoundingMode.HALF_UP);

        return Double.parseDouble(String.valueOf(targetAmount));
    }


    // 만료 시간 계산
    public static OffsetDateTime calculateExpireTime(int minute) throws DateTimeParseException {
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

    public static double calculateFee(long amount) {
        final double HUNDRED_EXCEED_FEE = 3000.0;
        final double HUNDRED_EXCEED_FEE_RATE = 0.1;

        final double HUNDRED_DOWN_FEE = 1000.0;
        final double HUNDRED_DOWN_FEE_RATE = 0.2;

        double sourceAmount = (double) amount;

        double targetFee = (sourceAmount > 0.0 && sourceAmount <= 1000000.0)?
                (sourceAmount * HUNDRED_DOWN_FEE_RATE) + HUNDRED_DOWN_FEE : (sourceAmount * HUNDRED_EXCEED_FEE_RATE) + HUNDRED_EXCEED_FEE;

        BigDecimal fee = new BigDecimal(targetFee);

        return Double.parseDouble(String.valueOf(fee.setScale(1, RoundingMode.HALF_UP)));
    }

    public static boolean isExpirationTimeOver(OffsetDateTime expireTime) {
        int result = expireTime.compareTo(OffsetDateTime.now());
        // result > 0 : 만료시간 > 현재 시간 -> 유효
        // result < 0 : 만료시간 < 현재 시간 -> 유효 하지 않음
        return (result < 0)? true : false;
    }
}
