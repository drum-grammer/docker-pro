package com.moin.remittance.repository.v2;

import com.moin.remittance.application.v2.transfer.impl.estimating.calculating.ExchangeRateCalculator;
import com.moin.remittance.application.v2.transfer.impl.estimating.policy.RemittanceFeePolicy;
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

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.ANY) // 가짜 DB로 테스트
@ExtendWith(SpringExtension.class)
public class RemittanceRepositoryV2Test {

    @Autowired
    private RemittanceRepositoryV2 remittanceRepositoryV2;

    private UUID quoteIdTestCase;
    private String userIdTestCase;

    private RemittanceQuoteEntityV2 createQuotationTestCase(long sourceAmount, int currencyUnit, BigDecimal usdBasePrice, BigDecimal basePrice, String currencyCode) {
        ExchangeRateCalculator exchangeRateCalculator = new ExchangeRateCalculator();
        RemittanceFeePolicy feePolicy = new RemittanceFeePolicy();

        BigDecimal fee = feePolicy.calculateRemittanceFee(sourceAmount);

        return RemittanceQuoteEntityV2.builder()// 송금 견적서 DTO
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
                .expireTime(OffsetDateTime.now().plusMinutes(10)) // 송금 견적서 만료 기간
                .userId("test@test.com")
                .build();
    }

    @BeforeEach
    @Test
    @DisplayName("@BeforeEach: 송금 견적서 저장")
    void saveSuccessfulTest() {
        // given: 저장할 송금 견적서 엔티티
        RemittanceQuoteEntityV2 quote1 = createQuotationTestCase(
                50000, 1, new BigDecimal("1362.50"), new BigDecimal("1362.50"), "USD"
        );
        RemittanceQuoteEntityV2 quote2 = createQuotationTestCase(
                70000, 100, new BigDecimal("1362.50"), new BigDecimal("884.67"), "JPY"
        );
        RemittanceQuoteEntityV2 quote3 = createQuotationTestCase(
                800000, 1, new BigDecimal("1380.00"), new BigDecimal("1380.00"), "USD"
        );

        // given: 저장하지 않을 송금 견적서 엔티티
        RemittanceQuoteEntityV2 quoteCopy1 = createQuotationTestCase(
                50000, 1, new BigDecimal("1362.50"), new BigDecimal("1362.50"), "USD"
        );
        RemittanceQuoteEntityV2 quoteCopy2 = createQuotationTestCase(
                70000, 100, new BigDecimal("1362.50"), new BigDecimal("884.67"), "JPY"
        );

        // when: 송금 견적서 저장
        RemittanceQuoteEntityV2 savedQuote1 = remittanceRepositoryV2.saveAndFlush(quote1);
        RemittanceQuoteEntityV2 savedQuote2 = remittanceRepositoryV2.saveAndFlush(quote2);
        RemittanceQuoteEntityV2 savedQuote3 = remittanceRepositoryV2.saveAndFlush(quote3);

        // then: 생성한 엔티티와 그 생성한 엔티티를 저장한 데이터가 같은지 확인
        assertEquals(quote1, savedQuote1);
        assertEquals(quote2, savedQuote2);
        assertEquals(quote3, savedQuote3);

        // then: PK가 잘 생성되었는지 확인 == 모든 송금 견적서는 서로 다름
        assertNotNull(savedQuote1.getQuoteId());
        assertNotNull(savedQuote2.getQuoteId());
        assertNotNull(savedQuote3.getQuoteId());
        assertNotEquals(quote1, savedQuote2);
        assertNotEquals(quote2, savedQuote1);
        assertNotEquals(savedQuote1, savedQuote2);
        assertNotEquals(savedQuote1, savedQuote3);

        /*
         * then: DB 저장하지않은 엔티티는 quoteId가 null == 저장한 견적서와 내용이 같더라도 저장하지 않았으므로 서로 다른 견적서
         * */
        assertNull(quoteCopy1.getQuoteId());
        assertNull(quoteCopy2.getQuoteId());
        assertNotEquals(quoteCopy1, savedQuote1);
        assertNotEquals(quoteCopy2, savedQuote2);

        this.quoteIdTestCase = quote1.getQuoteId();
        this.userIdTestCase = quote1.getUserId();
    }

    @Test
    @DisplayName("모든 송금 견적서 조회")
    void findAllQuotationTest() {
        // when: 송금 견적서 리스트
        List<RemittanceQuoteEntityV2> dataAll = remittanceRepositoryV2.findAll();

        // then: Not Null 체크
        dataAll.forEach(data -> {
            assertNotNull(data);
            assertNotNull(data.getQuoteId());
            assertTrue(data.getSourceAmount() > 0);
            assertNotNull(data.getFee());
            assertNotNull(data.getUsdExchangeRate());
            assertNotNull(data.getUsdAmount());
            assertNotNull(data.getTargetAmount());
            assertNotNull(data.getExchangeRate());
            assertNotNull(data.getUserId());
            assertNotNull(data.getExpireTime());
        });

        // Test Data 확인
        System.out.println("====** 모든 송금 견적서 조회 **====");
        System.out.println("====↑↑ Test Data ↑↑====");
        dataAll.stream()
                .map(RemittanceQuoteEntityV2::toString)
                .forEach(System.out::println);
    }

    @Test
    @DisplayName("견적서 번호와 유저아이디가 일치하는 발행한 견적서 조회")
    void findAllByQuoteIdAndUserIdTest() {
        // when: 견적서 번호와 유저아이디가 일치하는 발행한 견적서 조회
        RemittanceQuoteEntityV2 quotation = remittanceRepositoryV2.findByQuoteIdAndUserId(this.quoteIdTestCase, this.userIdTestCase);

        // then: 조회 결과값 확인
        assertEquals(this.quoteIdTestCase, quotation.getQuoteId());
        assertEquals(this.userIdTestCase, quotation.getUserId());
        assertTrue(remittanceRepositoryV2.existsByQuoteIdAndUserId(this.quoteIdTestCase, this.userIdTestCase));
        assertTrue(remittanceRepositoryV2.existsByQuoteIdAndUserId(quotation.getQuoteId(), quotation.getUserId()));

        assertFalse(remittanceRepositoryV2.existsByQuoteIdAndUserId(this.quoteIdTestCase, "test2@test.com"));
        assertFalse(remittanceRepositoryV2.existsByQuoteIdAndUserId(null, "test@test.com"));
        assertFalse(remittanceRepositoryV2.existsByQuoteIdAndUserId(UUID.fromString("2aeb538f-e05c-49ff-bb64-ae6fa93d494f"), "test@test.com"));
        assertFalse(remittanceRepositoryV2.existsByQuoteIdAndUserId(null, "test2@test.com"));

        assertNull(remittanceRepositoryV2.findByQuoteIdAndUserId(this.quoteIdTestCase, "test2@test.com"));// 일치하는 유저가 없는 경우
        assertNull(remittanceRepositoryV2.findByQuoteIdAndUserId(null, "test@test.com"));// 견적서 아이디값이 null일 경우
        assertNull(remittanceRepositoryV2.findByQuoteIdAndUserId(UUID.fromString("2aeb538f-e05c-49ff-bb64-ae6fa93d494f"), "test@test.com"));// 일치하는 견적서 아이디값이 없는 경우
        assertNull(remittanceRepositoryV2.findByQuoteIdAndUserId(null, "test2@test.com"));// 둘다 일치하지 않는 경우

        // Test Data 확인
        System.out.println("====** 견적서 번호로 발행한 견적서 조회 **====");
        System.out.println(quotation);
    }

    @Test
    @DisplayName("송금 견적서 삭제")
    void removeTest() {
        System.out.println("====** 삭제 전 **====");
        System.out.println(remittanceRepositoryV2.findAll());

        // when
        remittanceRepositoryV2.deleteById(this.quoteIdTestCase);

        // then
        assertFalse(remittanceRepositoryV2.findById(this.quoteIdTestCase).isPresent());
        assertFalse(remittanceRepositoryV2.existsById(this.quoteIdTestCase));

        System.out.println("====** 삭제 후 **====");
        System.out.println(remittanceRepositoryV2.findAll());
    }
}
