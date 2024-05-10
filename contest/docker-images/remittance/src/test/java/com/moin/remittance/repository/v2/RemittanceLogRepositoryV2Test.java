package com.moin.remittance.repository.v2;

import com.moin.remittance.application.v2.transfer.impl.estimating.calculating.ExchangeRateCalculator;
import com.moin.remittance.application.v2.transfer.impl.estimating.policy.RemittanceFeePolicy;
import com.moin.remittance.domain.entity.remittance.v2.RemittanceLogEntityV2;
import com.moin.remittance.domain.entity.remittance.v2.RemittanceQuoteEntityV2;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.ANY)
@ExtendWith(SpringExtension.class)
public class RemittanceLogRepositoryV2Test {

    @Autowired
    private RemittanceLogRepositoryV2 remittanceLogRepositoryV2;

    private String userIdTestCase;

    private RemittanceLogEntityV2 createRemittanceLogTestCase(
            long sourceAmount, int currencyUnit, BigDecimal usdBasePrice, BigDecimal basePrice, String currencyCode, String userId
    ) {
        ExchangeRateCalculator exchangeRateCalculator = new ExchangeRateCalculator();
        RemittanceFeePolicy feePolicy = new RemittanceFeePolicy();

        BigDecimal fee = feePolicy.calculateRemittanceFee(sourceAmount);

        return RemittanceLogEntityV2.builder()// 송금 견적서 DTO
                .sourceAmount(sourceAmount) // 원화
                .fee(fee) // 수수료
                .usdExchangeRate(usdBasePrice) // USD 환율
                .usdAmount(exchangeRateCalculator.calculateExchangeRate(
                        sourceAmount,
                        fee,
                        1,
                        usdBasePrice,
                        "USD")
                ) // USD 송금액
                .targetCurrency(currencyCode) // 타겟 통화
                .exchangeRate(basePrice) // 환율
                .targetAmount(exchangeRateCalculator.calculateExchangeRate(
                        sourceAmount,
                        fee,
                        currencyUnit,
                        basePrice,
                        currencyCode)
                ) // 받는 금액
                .requestedDate(OffsetDateTime.now()) // 송금 요청 시간
                .userId(userId)
                .build();
    }


    @Test
    @DisplayName("송금 거래 이력 저장")
    @BeforeEach
    void saveTest() {
        // given: 저장할 송금 견적서 엔티티
        RemittanceLogEntityV2 log1 = createRemittanceLogTestCase(
                50000, 1, new BigDecimal("1362.50"), new BigDecimal("1362.50"), "USD", "test1@test.com"
        );
        RemittanceLogEntityV2 log2 = createRemittanceLogTestCase(
                70000, 100, new BigDecimal("1362.50"), new BigDecimal("884.67"), "JPY", "test1@test.com"
        );
        RemittanceLogEntityV2 log3 = createRemittanceLogTestCase(
                800000, 1, new BigDecimal("1380.00"), new BigDecimal("1380.00"), "USD", "test2@test.com"
        );

        // given: 저장하지 않을 송금 견적서 엔티티
        RemittanceLogEntityV2 logCopy1 = createRemittanceLogTestCase(
                50000, 1, new BigDecimal("1362.50"), new BigDecimal("1362.50"), "USD", "test1@test.com"
        );
        RemittanceLogEntityV2 logCopy2 = createRemittanceLogTestCase(
                70000, 100, new BigDecimal("1362.50"), new BigDecimal("884.67"), "JPY", "test2@test.com"
        );

        // when: 송금 견적서 저장
        RemittanceLogEntityV2 savedLog1 = remittanceLogRepositoryV2.saveAndFlush(log1);
        RemittanceLogEntityV2 savedLog2 = remittanceLogRepositoryV2.saveAndFlush(log2);
        RemittanceLogEntityV2 savedLog3 = remittanceLogRepositoryV2.saveAndFlush(log3);

        // then: 생성한 엔티티와 그 생성한 엔티티를 저장한 데이터가 같은지 확인
        assertEquals(log1, savedLog1);
        assertEquals(log2, savedLog2);
        assertEquals(log3, savedLog3);

        // then: PK가 잘 생성되었는지 확인 == 모든 송금 견적서는 서로 다름
        assertNotNull(savedLog1.getLogId());
        assertNotNull(savedLog2.getLogId());
        assertNotNull(savedLog3.getLogId());
        assertNotEquals(log1, savedLog2);
        assertNotEquals(log2, savedLog1);
        assertNotEquals(savedLog1, savedLog2);
        assertNotEquals(savedLog1, savedLog3);

        /*
         * then: DB 저장하지않은 엔티티는 quoteId가 null == 저장한 견적서와 내용이 같더라도 저장하지 않았으므로 서로 다른 견적서
         * */
        assertNull(logCopy1.getLogId());
        assertNull(logCopy2.getLogId());
        assertNotEquals(logCopy1, savedLog1);
        assertNotEquals(logCopy2, savedLog2);

        this.userIdTestCase = savedLog1.getUserId();
    }

    @Test
    @DisplayName("모든 유저의 송금 거래 이력 조회")
    void findAllRemittanceLogList() {
        // when: 송금 거래 리스트
        List<RemittanceLogEntityV2> dataAll = remittanceLogRepositoryV2.findAll();

        // then: Not Null 체크
        dataAll.forEach(data -> {
            assertNotNull(data);
            assertNotNull(data.getLogId());
            assertTrue(data.getSourceAmount() > 0);
            assertNotNull(data.getFee());
            assertNotNull(data.getUsdExchangeRate());
            assertNotNull(data.getUsdAmount());
            assertNotNull(data.getTargetAmount());
            assertNotNull(data.getExchangeRate());
            assertNotNull(data.getUserId());
            assertNotNull(data.getRequestedDate());
        });

        // Test Data 확인
        System.out.println("====** 모든 유저의 송금 거래 내역 조회 **====");
        System.out.println("====↑↑ Test Data ↑↑====");
        System.out.println(dataAll);
    }

    @Test
    @DisplayName("특정 유저의 송금 거래 이력 조회")
    void findRemittanceLogListByUserId() {
        // when: 견적서 번호와 유저아이디가 일치하는 발행한 견적서 조회
        List<RemittanceLogEntityV2> remittanceLog = remittanceLogRepositoryV2.findByUserId(this.userIdTestCase);

        // then: 조회 결과값 확인
        remittanceLog.forEach(log -> assertEquals(this.userIdTestCase, log.getUserId()));// 요소가 거래 이력 객체인 리스트 안에 유저 아이디가 같은지 확인
        assertEquals(0, remittanceLogRepositoryV2.findByUserId("test@test2.com").size());// 존재하지 않으므로 리스트 사이즈가 0 이어야함

        assertTrue(remittanceLogRepositoryV2.existsByUserId(this.userIdTestCase));// 일치하는 거래 이력

        assertFalse(remittanceLog.isEmpty());// 리스트가 비어있으면 안됨
        assertFalse(remittanceLogRepositoryV2.existsByUserId("test@test2.com"));// 해당 유저의 데이터가 존재하지 않는 경우

        assertNotNull(remittanceLog);
        assertNotNull(remittanceLogRepositoryV2.findByUserId("test@test2.com"));// 사이즈가 0인 리스트가 리턴되어야함

        // Test Data 확인
        System.out.println("====** 특정 유저의 송금 거래 이력 조회 **====");
        System.out.println(remittanceLog);
    }
}
