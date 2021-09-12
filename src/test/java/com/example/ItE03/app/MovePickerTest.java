package com.example.ItE03.app;

import com.example.ItE03.game.Move;
import com.google.common.collect.Sets;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.Test;

import java.util.Set;

import static com.example.ItE03.game.MoveTestUtils.move;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.in;
import static org.hamcrest.Matchers.is;

class MovePickerTest {

    MovePicker underTest;

    @BeforeEach
    void setup() {
        underTest = new MovePicker();
    }

    @Test
    void givenSetSizeIsOne_whenPickOne_thenPicksSingleElement() {
        Move rock = move(1, "Rock");
        Set<Move> moves = Sets.newHashSet(rock);

        assertThat(underTest.pickOne(moves), is(rock));
    }

    @RepeatedTest(10)
    void givenStandardRpsMovesSet_whenPickOne_thenPickedIsOneOfThisMoveSet() {
        Set<Move> moves = rpsMoves();
        assertThat(underTest.pickOne(moves), is(in(moves)));
    }

    Set<Move> rpsMoves() {
        return Sets.newHashSet(move(1, "Rock"),
                move(2, "Paper"),
                move(1, "Scissors"));
    }
}