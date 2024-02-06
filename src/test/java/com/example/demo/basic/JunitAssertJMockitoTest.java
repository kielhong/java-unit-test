package com.example.demo.basic;

import java.util.Random;
import org.assertj.core.api.Assertions;
import org.assertj.core.api.BDDAssertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.BDDMockito;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

class JunitAssertJMockitoTest {
    private Combat combat;

    private Dice dice;

    @BeforeEach
    void setUp() {
        dice = Mockito.mock(Dice.class);

        combat = new Combat(dice);
    }

    @Test
    void attackWinTest() {
        Mockito.when(dice.roll())
            .thenReturn(10);

        var result = combat.attack(5);

        Assertions.assertThat(result)
            .isTrue();
    }

    @Test
    void attackLooseBddStyleTest() {
        BDDMockito.given(dice.roll())
            .willReturn(10);

        var result = combat.attack(12);

        BDDAssertions.then(result)
            .isFalse();
    }

    class Combat {
        private final Dice dice;

        public Combat(Dice dice) {
            this.dice = dice;
        }

        Boolean attack(Integer armor) {
            return dice.roll() >= armor;
        }
    }

    class Dice {
        Integer roll() {
            return new Random().nextInt(19) + 1;
        }
    }
}
