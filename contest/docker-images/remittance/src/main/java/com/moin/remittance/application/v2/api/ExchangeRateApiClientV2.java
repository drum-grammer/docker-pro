package com.moin.remittance.application.v2.api;

import com.moin.remittance.domain.dto.remittance.v2.ExchangeRateInfoDTO;
import org.springframework.stereotype.Service;

import java.util.HashMap;


@Service
public interface ExchangeRateApiClientV2 {
    HashMap<String, ExchangeRateInfoDTO> fetchExchangeRateInfoFromExternalAPI(String codes);
}
