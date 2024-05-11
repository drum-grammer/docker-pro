package com.moin.remittance.application.v1.dao;

import com.moin.remittance.domain.dto.member.MemberDTO;

public interface MemberDAO {
    void saveUser(MemberDTO member);

    boolean hasUser(String userId);

    boolean hasUser(String userId, String password);

    String getIdTypeByUserId(String userId);

    String getNameOfMemberByUserId(String userId);

}
