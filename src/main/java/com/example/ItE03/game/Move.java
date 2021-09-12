package com.example.ItE03.game;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Value;

import java.util.Comparator;

@Value
@Getter
@AllArgsConstructor(access = AccessLevel.PROTECTED)
public class Move implements Comparable<Move>{

    int order;
    String name;

    @Override
    public int compareTo(Move m) {
        return naturalOrderComparator().compare(this, m);
    }

    protected Comparator<Move> naturalOrderComparator() {
        return Comparator.comparingInt(Move::getOrder);
    }
}
