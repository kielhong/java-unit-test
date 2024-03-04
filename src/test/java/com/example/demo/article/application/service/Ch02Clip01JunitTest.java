package com.example.demo.article.application.service;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class Ch02Clip01JunitTest {
    @BeforeAll
    static void initAll() {
        System.out.println("BeforeAll");
    }

    @BeforeEach
    void setUp() {
        System.out.println("BeforeEach");
    }

    @Test
    @DisplayName("성공 테스트")
    void succeedingTest() {
        // Arrange (준비)
        String string1 = "Junit";
        String string2 = "Junit";
        String string3 = "test";
        String string4 = null;
        int number1 = 1;
        int	number2 = 2;
        int[] array1 = { 1, 2, 3 };
        int[] array2 = { 1, 2, 3 };

        // Act (실행)
        int result = number1 + number2;

        // Assert (검증)
        Assertions.assertEquals(3, result);
        Assertions.assertTrue(number1 < number2);
        Assertions.assertEquals(string1, string2);
        Assertions.assertNotEquals(string1, string3);
        Assertions.assertNotNull(string1);
        Assertions.assertNull(string4);
        Assertions.assertArrayEquals(array1, array2);

        System.out.println("succeedingTest");
    }

    @Test
    @DisplayName("실패 테스트")
    void failingTest() {
        // 3을 다른 값으로 바꿔서 test fail 시키기
        Assertions.assertEquals(3, 1 + 2, "실패한 테스트의 메시지");

        System.out.println("failingTest");
    }

    @Test
    @DisplayName("예외 테스트")
    void exceptionTest() {
        var exception = Assertions.assertThrows(NumberFormatException.class, () -> {
            Integer.parseInt("1a");
        });

        var expectedMessage = "For input string";
        var actualMessage = exception.getMessage();

        Assertions.assertTrue(actualMessage.contains(expectedMessage));
    }

    @Test
    @Disabled("이 테스트를 Disable 이유")
    void skippingTest() {
        System.out.println("DisabledTest");

        Assertions.assertEquals(3, 1 + 2);
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
