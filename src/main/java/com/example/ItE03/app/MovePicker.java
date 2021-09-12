package com.example.ItE03.app;

import com.example.ItE03.game.Move;
import com.google.common.base.Preconditions;
import lombok.RequiredArgsConstructor;

import java.security.SecureRandom;
import java.util.Collection;
import java.util.Set;

public class MovePicker {

    private final Set<Move> moves;
    private final SecureRandom secureRandom;

    public MovePicker(Set<Move> moves) {
        checkNotNullOrEmpty(moves);
        this.moves = moves;
        this.secureRandom = new SecureRandom();
    }

    public Move pickOne() {
        return moves.stream()
                .skip(secureRandom.nextInt(moves.size()))
                .findFirst()
                .orElse(null);
    }

    private void checkNotNullOrEmpty(Collection<?> moves) {
        Preconditions.checkNotNull(moves);
        Preconditions.checkArgument(moves.size() > 0);
    }
}
