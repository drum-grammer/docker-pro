package com.moin.remittance.repository.v1;

import com.moin.remittance.domain.entity.member.v2.MemberEntityV2;
import com.moin.remittance.repository.v2.MemberRepositoryV2;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;


@ExtendWith(SpringExtension.class)
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.ANY) // 가짜 DB로 테스트
public class MemberRepositoryTest {

    @Autowired
    private MemberRepositoryV2 memberRepository;

    /**
     * 회원가입
     * 유저데이터 저장 -> 유저 조회 where(유저아이디, 비밀번호)
     */
    @Test
    @DisplayName("회원 가입 데이터 저장")
    public void saveUserTest() {
        // given: 회원 엔티티
        MemberEntityV2 member = MemberEntityV2.builder()
                .userId("test@test.com")
                .password("1234")
                .name("카라멜프라프치노")
                .idType("reg_no")
                .idValue("111111-1111111")
                .build();

        // when: 회원 저장
        MemberEntityV2 memberEntity = memberRepository.save(member);

        // then: junit 메소드로 값 비교
        assertEquals(member.getUserId(), memberEntity.getUserId()); // 회원 아이디(이메일 형식)
        assertEquals(member.getPassword(), memberEntity.getPassword()); // 회원 비밀번호
        assertEquals(member.getName(), memberEntity.getName()); // 회원 이름

        // assertj 메소드
        assertThat(member.getIdType()).isEqualTo(memberEntity.getIdType()); // 개인 회원 or 법인 회원
        assertThat(member.getIdValue()).isEqualTo(memberEntity.getIdValue()); // 주민등록번호 or 사업자 번호
    }

    /**
     * 회원가입 -> 로그인
     * 유저데이터 저장 -> 유저 조회 where(유저아이디, 비밀번호)
     */
    @Test
    @Transactional
    @DisplayName("회원 가입 데이터 저장 -> 회원 조회")
    public void createUserAndExistsUserTest() {
        // given: 회원 엔티티 생성
        MemberEntityV2 member = MemberEntityV2.builder()
                .userId("test@test.com")
                .password("1234")
                .name("카라멜프라프치노")
                .idType("reg_no")
                .idValue("111111-1111111")
                .build();

        // when: 회원 저장
        MemberEntityV2 memberEntity = memberRepository.saveAndFlush(member);

        // then: junit 메소드로 타겟 값과 저장된 값 비교
        assertEquals(member.getUserId(), memberEntity.getUserId()); // 회원 아이디(이메일 형식)
        assertEquals(member.getPassword(), memberEntity.getPassword()); // 회원 비밀번호
        assertEquals(member.getName(), memberEntity.getName()); // 회원 이름
        // assertj 메소드
        assertThat(member.getIdType()).isEqualTo(memberEntity.getIdType()); // 개인 회원 or 법인 회원
        assertThat(member.getIdValue()).isEqualTo(memberEntity.getIdValue()); // 주민등록번호 or 사업자 번호


        // then: 회원 조회
        assertTrue(memberRepository.existsByUserIdAndPassword("test@test.com", "1234")); // true && true == true
        assertFalse(memberRepository.existsByUserIdAndPassword("test@test.com", "123")); // true && false == false
        assertFalse(memberRepository.existsByUserIdAndPassword("test@test2.com", "1234")); // false && true == false
        assertFalse(memberRepository.existsByUserIdAndPassword("test@test2.com", "123")); // false && false == false
    }

}
