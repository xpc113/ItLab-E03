package com.example.ItE03.game;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.apache.commons.lang3.RandomStringUtils.randomAlphabetic;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.assertThrows;

@Slf4j
class GameVariationTest {

    GameVariation gv;

    @Test
    void whenCreate_thenNoExceptionThrown() {
        GameVariation gv = new GameVariation(Arrays.asList("Rock", "Paper", "Scissors"));
    }

    @Test
    void whenCreateEven_thenThrowsIAE() {
        assertThrows(IllegalArgumentException.class,
                () -> new GameVariation(Arrays.asList("Rock", "Paper")));
    }

    @Test
    void whenCreateNotDistinct_thenThrowsIAE() {
        assertThrows(IllegalArgumentException.class,
                () -> new GameVariation(Arrays.asList("Rock", "Paper", "Rock")));
    }

    @Nested
    class ResultTest {



        @BeforeEach
        void setup() {
            gv = basicRPSVariation();
        }

        @ParameterizedTest
        @CsvSource({"Rock,Paper,Lose", "Rock,Scissors,Win", "Rock,Rock,Draw"})
        void givenBasicRpsVariation_whenResultForMoves_thenResultIsExpected(
                String moveA,
                String moveB,
                String result) {
            assertThat(gv.resultFor(gv.getMoveByName(moveA), gv.getMoveByName(moveB)),
                    is(GameResult.valueOf(result)));
        }
    }

    @Nested
    class DistanceToTest {

        @ParameterizedTest
        @CsvSource({"1,2,1", "5,1,1", "1,1,0", "5,5,0", "3,3,0", "1,5,4","5,4,4"})
        void distanceForwardOk(int orderA, int orderB, int expected) {
            GameVariation gv = gameVariation(5);

            assertThat(gv.distanceForward(
                    gv.getMoveByOrder(orderA),
                    gv.getMoveByOrder(orderB)), is(expected));
        }
    }

    GameVariation gameVariation(int size) {
        List<String> moveNames = Stream.generate(() -> randomAlphabetic(6))
                .limit(size)
                .collect(Collectors.toList());
        return new GameVariation(moveNames);
    }

    GameVariation basicRPSVariation() {
        return new GameVariation(Arrays.asList("Rock", "Paper", "Scissors"));
    }
}