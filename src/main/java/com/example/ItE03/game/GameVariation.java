package com.example.ItE03.game;

import com.google.common.base.Preconditions;
import com.google.common.collect.ImmutableSortedSet;
import org.apache.commons.collections4.iterators.LoopingIterator;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class GameVariation {

    private final LinkedHashSet<Move> moves;

    public GameVariation(List<String> moveNames) {
        Preconditions.checkNotNull(moveNames);
        checkIfOdd(moveNames, "moveNames must contain odd number of elements");
        moves = moveNames.stream()
                .distinct()
                .map(name -> new Move(moveNames.indexOf(name)+1, name))
                .collect(Collectors.toCollection(LinkedHashSet::new));
        Preconditions.checkArgument(moves.size() == moveNames.size(),
                "moveNames must contain distinct strings");
    }

    private static void checkIfOdd(List<?> list, String msg) {
        Preconditions.checkArgument(list.size() % 2 != 0,
                msg);
    }

    private void checkMove(Move move) {
        Preconditions.checkNotNull(move);
        Preconditions.checkState(moves.contains(move),
                "Move " + move + " is not present in this GameVariation");
    }

    public GameResult resultFor(Move a, Move b) {
        checkMove(a);
        checkMove(b);

        if (a.equals(b)) {
            return GameResult.Draw;
        }
        return distanceForward(a,b) > distanceForward(b,a) ? GameResult.Win : GameResult.Lose;
    }

    protected int distanceForward(Move a, Move b) {
        checkMove(a);
        checkMove(b);

        Iterator<Move> iterator = new LoopingIterator<>(moves);
        stepsToReach(iterator, a); // Skips to move a
        return a.equals(b)? 0 : stepsToReach(iterator, b);
    }

    private int stepsToReach(Iterator<Move> iterator, Move move) {
        int steps = 1;
        while (!iterator.next().equals(move)) {
            steps++;
        }
        return steps;
    }

    public Set<Move> getMoves() {
        return ImmutableSortedSet.copyOf(moves);
    }

    public Move getMoveByName(String name) {
        return moves.stream()
                .filter(move -> move.getName().equals(name))
                .findFirst()
                .orElseThrow(NoSuchElementException::new);
    }

    public Move getMoveByOrder(int order) {
        return moves.stream()
                .filter(move -> move.getOrder() == order)
                .findFirst()
                .orElseThrow(NoSuchElementException::new);
    }

    public int moveCount() {
        return moves.size();
    }
}
