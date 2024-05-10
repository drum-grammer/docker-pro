package com.moin.remittance.application.v1;

import com.moin.remittance.domain.dto.member.MemberDTO;
import org.springframework.stereotype.Service;

@Service
public interface MemberServiceV1 {
    void saveUser(MemberDTO dto);

    String getAuthToken(String userId, String password);
}
