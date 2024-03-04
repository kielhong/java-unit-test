package com.example.demo.article.application.service;

import org.assertj.core.api.Assertions;
import org.assertj.core.api.BDDAssertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class Ch02Clip01JunitAssertJTest {
    @Test
    @DisplayName("AssertJ Assertion")
    void addTest() {
        var number1 = 2;
        var number2 = 3;

        var result = number1 + number2;


        Assertions.assertThat(result)
            .isNotNull()
            .isEqualTo(5)
            .isGreaterThanOrEqualTo(number1)
            .isGreaterThanOrEqualTo(number2);
        Assertions.assertThat(number1 < number2)
            .isTrue();

        // Junit Assertions
        org.junit.jupiter.api.Assertions.assertEquals(5, result);
        org.junit.jupiter.api.Assertions.assertTrue(number1 < number2);
        org.junit.jupiter.api.Assertions.assertNotEquals(number1, number2);
        org.junit.jupiter.api.Assertions.assertNotNull(number1);
    }

    @Test
    @DisplayName("BDD Style Assertion")
    void addBDDStyleAssertion() {
        // Given
        var number1 = 2;
        var number2 = 3;

        // When
        var result = number1 + number2;

        // Then
        BDDAssertions.then(result)
            .isEqualTo(5);
    }
}
