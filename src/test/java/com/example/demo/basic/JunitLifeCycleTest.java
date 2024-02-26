package com.example.demo.basic;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

class JunitLifeCycleTest {
    @BeforeAll
    static void initAll() {
        System.out.println("BeforeAll");
    }

    @BeforeEach
    void setUp() {
        System.out.println("BeforeEach");
    }

    @Test
    void succeedingTest() {
        System.out.println("SucceedTest");
    }

    @Test
    void failingTest() {
        System.out.println("FailTest");
    }

    @Test
    @Disabled
    void skippingTest() {
        System.out.println("DisabledTest");
    }

    @AfterEach
    void tearDown() {
        System.out.println("AfterEach");
    }

    @AfterAll
    static void tearDownAll() {
        System.out.println("AfterAll");
    }
}
