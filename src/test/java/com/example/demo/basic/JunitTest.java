package com.example.demo.basic;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class JunitTest {
    private Calculator calculator;

    @BeforeEach
    void setUp() {
        calculator = new Calculator();
    }

    @Test
    void addTest() {
        Integer result = calculator.add(2, 3);

        Assertions.assertEquals(5, result);
    }

    @Test
    void divideTest() {
        Float result = calculator.divide(4, 2);

        Assertions.assertEquals(2.0f, result);
    }

    @Test
    void divideByZeroTest() {
        Exception exception = Assertions.assertThrows(IllegalArgumentException.class, () -> calculator.divide(2, 0));

        Assertions.assertTrue(exception.getMessage().contains("Divide By Zero"));
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
