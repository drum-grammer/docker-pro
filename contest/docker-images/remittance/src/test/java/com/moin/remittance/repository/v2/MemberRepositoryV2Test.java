package com.moin.remittance.repository.v2;

import com.moin.remittance.domain.entity.member.v2.MemberEntityV2;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;


@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.ANY) // 가짜 DB로 테스트
@ExtendWith(SpringExtension.class)
public class MemberRepositoryV2Test {

    @Autowired
    private MemberRepositoryV2 memberRepositoryV2;

    private MemberEntityV2 createMemberTestCase(
            String userId, String password, String name, String idType, String idValue
    ) {
        return MemberEntityV2.builder()
                .userId(userId)
                .password(password)
                .name(name)
                .idType(idType)
                .idValue(idValue)
                .build();
    }

    /**
     * 회원가입
     * 유저데이터 저장 -> 유저 조회 where(유저아이디, 비밀번호)
     */
    @BeforeEach
    @Test
    @DisplayName("회원 저장")
    void saveUserTest() {
        String REG_NO = "REG_NO";
        String BUSINESS_NO = "BUSINESS_NO";
        // given: 회원 엔티티
        MemberEntityV2 member1 = createMemberTestCase(
                "test@test.com", "1234", "카라멜프라프치노", REG_NO, "111111-1111111"
        );
        MemberEntityV2 member2 = createMemberTestCase(
                "test2@test.com", "3333", "자바칩프라푸치노", REG_NO, "222222-2222222"
        );
        MemberEntityV2 member3 = createMemberTestCase(
                "test3@test.com", "3333", "(주) 컴포즈", BUSINESS_NO, "222222-2222222"
        );

        // when: 회원 저장
        MemberEntityV2 savedMember1 = memberRepositoryV2.save(member1);
        MemberEntityV2 savedMember2 = memberRepositoryV2.save(member2);
        MemberEntityV2 savedMember3 = memberRepositoryV2.save(member3);

        // then: junit 메소드로 값 비교
        assertEquals(member1, savedMember1);
        assertEquals(member2, savedMember2);
        assertEquals(member3, savedMember3);

        // then: PK가 잘 생성되었는지 확인
        assertNotNull(savedMember1.getIndex());
        assertNotNull(savedMember2.getIndex());
        assertNotNull(savedMember3.getIndex());
        assertNotEquals(member1, savedMember2);
        assertNotEquals(member2, savedMember1);
        assertNotEquals(savedMember1, savedMember2);
        assertNotEquals(savedMember1, savedMember3);
    }

    /**
     * 회원가입 -> 로그인
     */
    @Test
    @DisplayName("Parameter: 유저아이디, 비밀번호 => 회원 조회")
    void findByUserIdAndPasswordTest() {
        // then: 회원 조회
        assertTrue(memberRepositoryV2.existsByUserIdAndPassword("test@test.com", "1234")); // true && true == true
        assertFalse(memberRepositoryV2.existsByUserIdAndPassword("test@test.com", "123")); // true && false == false
        assertFalse(memberRepositoryV2.existsByUserIdAndPassword("test@test2.com", "1234")); // false && true == false
        assertFalse(memberRepositoryV2.existsByUserIdAndPassword("test@test2.com", "123")); // false && false == false

        // Test Data 확인
        System.out.println("====** 회원 조회 **====");
        System.out.println("====↑↑ Test Data ↑↑====");
        System.out.println(memberRepositoryV2.findByUserIdAndPassword("test@test.com", "1234"));
    }

    @Test
    @DisplayName("모든 회원 조회")
    void findAllMember() {
        List<MemberEntityV2> dataAll = memberRepositoryV2.findAll();

        // then: Not Null 체크
        dataAll.forEach(data -> {
            assertNotNull(data);
            assertNotNull(data.getIndex());
            assertNotNull(data.getUserId());
            assertNotNull(data.getPassword());
            assertNotNull(data.getName());
            assertNotNull(data.getIdType());
            assertNotNull(data.getIdValue());
        });

        // Test Data 확인
        System.out.println("====** 모든 회원 조회 **====");
        System.out.println("====↑↑ Test Data ↑↑====");
        dataAll.forEach(System.out::println);
    }

}
