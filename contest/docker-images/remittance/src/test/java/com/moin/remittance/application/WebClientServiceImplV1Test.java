package com.moin.remittance.application;

import com.moin.remittance.application.v2.api.impl.ExchangeRateApiClientImplV2;
import com.moin.remittance.domain.dto.remittance.v1.ExchangeRateInfoDTO;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.web.reactive.function.client.WebClient;

@SpringBootTest(classes = ExchangeRateApiClientImplV2.class)
public class WebClientServiceImplV1Test {

    @Test
    @DisplayName("다나움 외부 API로 환율 정보 호출")
    void getExchangeRateInfo() {

    }

}
