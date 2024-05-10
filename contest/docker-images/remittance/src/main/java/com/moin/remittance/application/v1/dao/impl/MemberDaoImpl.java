package com.moin.remittance.application.v1.dao.impl;

import com.moin.remittance.application.v1.dao.MemberDAO;
import com.moin.remittance.domain.dto.member.MemberDTO;
import com.moin.remittance.domain.entity.member.v1.MemberEntity;
import com.moin.remittance.repository.v1.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class MemberDaoImpl implements MemberDAO {

    private final MemberRepository memberRepository;

    @Override
    public void saveUser (MemberDTO memberDTO) {
        memberRepository.save(new MemberEntity().toEntity(memberDTO));
    }

    @Override
    public boolean hasUser(String userId) {
        return memberRepository.existsByUserId(userId);
    }

    @Override
    public boolean hasUser(String userId, String password) {
        return memberRepository.existsByUserIdAndPassword(userId, password);
    }

    @Override
    public String getNameOfMemberByUserId(String userId) {
        return memberRepository.findByUserId(userId).getName();
    }

    @Override
    public String getIdTypeByUserId(String userId) {
        return memberRepository.findByUserId(userId).getIdType();
    }
}
