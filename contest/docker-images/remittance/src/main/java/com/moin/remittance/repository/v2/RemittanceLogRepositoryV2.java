package com.moin.remittance.repository.v2;

import com.moin.remittance.domain.entity.remittance.v2.RemittanceLogEntityV2;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface RemittanceLogRepositoryV2 extends JpaRepository<RemittanceLogEntityV2, UUID> {
    List<RemittanceLogEntityV2> findByUserId(String userId);

    /* 테스트 용 쿼리 메소드 */
    boolean existsByUserId(String userId);
}
