package com.example.demo.basic;

import org.assertj.core.api.Assertions;
import org.assertj.core.api.BDDAssertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class JunitAssertJTest {
    private Calculator calculator;

    @BeforeEach
    void setUp() {
        calculator = new Calculator();
    }

    @Test
    void addTest() {
        Integer result = calculator.add(2, 3);

        Assertions.assertThat(result)
            .isEqualTo(5)
            .isGreaterThanOrEqualTo(2)
            .isGreaterThanOrEqualTo(3);
    }

    @Test
    @DisplayName("BDD Style Assertion")
    void addBDDStyleAssertionTest() {
        Integer result = calculator.add(2, 3);

        BDDAssertions.then(result).isEqualTo(5);
    }

    @Test
    void divideTest() {
        Float result = calculator.divide(4, 2);

        Assertions.assertThat(result)
            .isEqualTo(2.0f);
    }

    @Test
    void divideByZeroTest() {
        Assertions.assertThatThrownBy(() -> calculator.divide(2, 0))
            .isInstanceOf(IllegalArgumentException.class)
            .hasMessage("Divide By Zero");
    }

    class Calculator {
        Integer add(Integer a, Integer b) {
            return a + b;
        }

        Float divide(Integer a, Integer b) {
            if (b == 0) {
                throw new IllegalArgumentException("Divide By Zero");
            }

            return (float) a / (float) b;
        }
    }
}
