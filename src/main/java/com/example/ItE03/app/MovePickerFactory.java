package com.example.ItE03.app;

import com.example.ItE03.game.Move;

import java.util.Set;

public interface MovePickerFactory {

    public MovePicker movePicker(Set<Move> moves);
}
