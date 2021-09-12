package com.example.ItE03.app;

import com.example.ItE03.game.Move;
import com.google.common.base.Preconditions;

import java.security.SecureRandom;
import java.util.Set;

public class MovePicker {

    public Move pickOne(Set<Move> moves) {
        checkNotNullOrEmpty(moves);
        return moves.stream()
                .skip(new SecureRandom().nextInt(moves.size()))
                .findFirst()
                .orElse(null);
    }

    private void checkNotNullOrEmpty(Set<Move> moves) {
        Preconditions.checkNotNull(moves);
        Preconditions.checkArgument(moves.size() > 0);
    }
}
