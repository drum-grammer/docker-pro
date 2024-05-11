package com.moin.remittance.presentation.v1;

import com.moin.remittance.application.v1.MemberServiceV1;
import com.moin.remittance.domain.dto.member.MemberDTO;
import com.moin.remittance.domain.dto.requestbody.MemberLoginRequestBodyDTO;

import jakarta.transaction.Transactional;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureWebMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.BDDMockito.given;
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(MemberControllerV1.class) // 메인 컨트롤러와 테스트 컨트롤러 패키지 경로 맞추기
@AutoConfigureMockMvc // 이 어노테이션을 통해 MockMvc를 Builder 없이 주입받을 수 있음
@AutoConfigureWebMvc
@ExtendWith(MockitoExtension.class)
public class MemberControllerV1Test {


    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private MemberServiceV1 memberServiceV1;

    @Test
    @DisplayName("회원 가입 테스트")
    public void saveUserTest() throws Exception {
        System.out.println("saveUserTest()");
        MemberDTO member = MemberDTO.builder()
                .userId("test@test.com")
                .password("1234")
                .name("카라멜프라프치노")
                .idType("reg_no")
                .idValue("111111-1111111")
                .build();

        System.out.println("member: " + member);

        //given : Mock 객체가 특정 상황에서 해야하는 행위를 정의하는 메소드
        doNothing().when(mock(MemberServiceV1.class)).saveUser(isA(MemberDTO.class));
        mock(MemberServiceV1.class).saveUser(member);

        // andExpect : 기대하는 값이 나왔는지 체크
        mockMvc.perform(post("/api/v1/signup")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(String.valueOf(member)))
                .andExpect(status().is2xxSuccessful());

        // verify : 해당 객체의 메소드가 실행되었는지 체크
        verify(memberServiceV1).saveUser(member);
    }

    // 로그인
    @Test
    @Transactional
    @DisplayName("로그인 테스트")
    public void userLoginTest() throws Exception {
        // given
        given(memberServiceV1.getAuthToken("test@test.com", "1234")).willReturn(
                "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzUxMiJ9.eyJleHAiOjE3MDY2NzcxOTAsInVzZXJJZCI6ImhvbmcxMiJ9.oTl_bkZArOZ1CrrMx0uNi_ukP9RmLFLJRzlY4Wi_yFSzDotSZuR8O84mjE8qcXI9Yyp4JrzN3llgStkdy8n4TQ"
        );

        MemberLoginRequestBodyDTO requestBody = MemberLoginRequestBodyDTO.builder()
                .username("test@test.com")
                .password("1234")
                .build();

        // andExpect : 기대하는 값이 나왔는지 체크
        mockMvc.perform(post("/api/v1/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(String.valueOf(requestBody)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.token").exists())
                .andDo(print());

        // verify : 해당 객체의 메소드가 실행되었는지 체크
        verify(memberServiceV1).getAuthToken("test@test.com", "1234");
    }

}
