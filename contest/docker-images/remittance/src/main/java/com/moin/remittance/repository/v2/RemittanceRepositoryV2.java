package com.moin.remittance.repository.v2;

import com.moin.remittance.domain.entity.remittance.v2.RemittanceQuoteEntityV2;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface RemittanceRepositoryV2 extends JpaRepository<RemittanceQuoteEntityV2, UUID> {
    // 송금 견적서 조회
    RemittanceQuoteEntityV2 findByQuoteIdAndUserId(UUID quoteId, String userId);

    boolean existsByQuoteIdAndUserId(UUID quoteId, String userId);


}
