package com.moin.remittance.application.v2.user;

import com.moin.remittance.domain.dto.member.MemberDTO;
import org.springframework.stereotype.Service;

@Service
public interface MemberServiceV2 {
    void saveUser(MemberDTO dto);
}
