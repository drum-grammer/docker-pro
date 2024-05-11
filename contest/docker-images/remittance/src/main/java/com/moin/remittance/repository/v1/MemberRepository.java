package com.moin.remittance.repository.v1;

import com.moin.remittance.domain.entity.member.v1.MemberEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MemberRepository extends JpaRepository<MemberEntity, Long> {
    boolean existsByUserId(String userId);

    boolean existsByUserIdAndPassword(String userId, String password);

    MemberEntity findByUserId(String userId);

}
