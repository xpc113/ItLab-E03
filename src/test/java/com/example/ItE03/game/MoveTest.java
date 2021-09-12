package com.example.ItE03.game;

import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.apache.commons.lang3.RandomStringUtils.randomAlphabetic;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

class MoveTest {

    @Test
    void whenCreated_thenIsOk() {
        new Move(1, "Rock");
    }

    @Nested
    class ComparatorTest {

        @ParameterizedTest
        @CsvSource({"0,0,0", "1,1,0", "1,0,1", "5,-1,1", "1,2,-1", "-3,0,-1"})
        void naturalComparatorIsOk(int aOrder, int bOrder, int expected) {
            assertThat(
                    moveWithOrder(aOrder).compareTo(moveWithOrder(bOrder)),
                    is(expected));
        }

        Move moveWithOrder(int order) {
            return new Move(order, randomAlphabetic(6));
        }
    }

}