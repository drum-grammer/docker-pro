package com.moin.remittance.repository.v1;

import com.moin.remittance.domain.entity.remittance.v1.RemittanceLogEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface RemittanceLogRepository extends JpaRepository<RemittanceLogEntity, Long> {
    List<RemittanceLogEntity> findByUserId(String userId);

    @Query(value = "select * from remittance_log where user_id = :userId and cast(requested_date as date) = CURRENT_DATE", nativeQuery = true)
    List<RemittanceLogEntity>findByUserIdAndRequestedDate(String userId);
}
