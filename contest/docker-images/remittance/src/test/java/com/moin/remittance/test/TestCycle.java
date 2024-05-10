package com.moin.remittance.test;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class TestCycle {

    /**
     * 테스트 시작 전에 실행되어야 하는 메소드를 표현
     * */
    @BeforeAll
    static void beforeAll() {
        System.out.println("## BeforeAll Annotation 호출 ##");
        System.out.println();
    }

    /**
     * 테스트 종료 후에 실행되어야 하는 메소드를 표현
     * */
    @AfterAll
    static void afterAll() {
        System.out.println("## afterAll Annotation 호출 ##");
        System.out.println();
    }

    /**
     * 각 테스트 메소드가 시작되기 전에 실행되어야 하는 메소드를 표현
     * */
    @BeforeEach
    void beforeEach() {
        System.out.println("## beforeEach Annotation 호출 ##");
        System.out.println();
    }

    /**
     * 각 테스트 메소드가 시작된 후에 실행되어야 하는 메소드를 표현
     * */
    @AfterEach
    void afterEach() {
        System.out.println("## afterEach Annotation 호출 ##");
        System.out.println();
    }

    /**
     * 테스트용 메소드를 표현하는 어노테이션
     * */
    @Test
    void test1() {
        System.out.println("## test1 시작 ##");
        System.out.println();
    }

    @Test
    @DisplayName("Test Case 2!!!")
    void test2() {
        System.out.println("## test2 시작 ##");
        System.out.println();
    }

    @Test
    @Disabled
        // Disabled Annotation : 테스트를 실행하지 않게 설정하는 어노테이션
    void test3() {
        System.out.println("## test3 시작 ##");
        System.out.println();
    }

}
