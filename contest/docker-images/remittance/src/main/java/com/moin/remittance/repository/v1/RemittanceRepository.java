package com.moin.remittance.repository.v1;

import com.moin.remittance.domain.entity.remittance.v1.RemittanceQuoteEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RemittanceRepository extends JpaRepository<RemittanceQuoteEntity, Long> {
    RemittanceQuoteEntity findByQuoteId(long quoteId);
}
