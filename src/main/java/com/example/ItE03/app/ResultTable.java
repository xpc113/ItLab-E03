package com.example.ItE03.app;

import com.example.ItE03.game.GameResult;
import com.example.ItE03.game.GameVariation;
import com.example.ItE03.game.Move;
import de.vandermeer.asciitable.AT_Cell;
import de.vandermeer.asciitable.AsciiTable;
import lombok.Value;
import lombok.extern.slf4j.Slf4j;
import org.antlr.v4.misc.OrderedHashMap;
import org.apache.commons.lang3.tuple.Pair;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Value
@Slf4j
public class ResultTable {

    Map<Move, List<Pair<Move, GameResult>>> results;
    AsciiTable table;

    public ResultTable(GameVariation gameVariation) {
        results = initResultMap(gameVariation);
        table = initAsciiTable(results);
    }

    public static void main(String[] args) {
        GameVariation gv = new GameVariation(List.of("Rock", "Paper", "Scissors"));

        ResultTable table = new ResultTable(gv);
        log.warn(table.toString());
    }

    private Map<Move, List<Pair<Move, GameResult>>> initResultMap(GameVariation gameVariation) {
        Map<Move, List<Pair<Move, GameResult>>> results = new OrderedHashMap<>();
        Set<Move> moves = gameVariation.getMoves();

        moves.forEach(move -> results.put(move, moves.stream()
                .map(against -> Pair.of(
                        against,
                        gameVariation.resultFor(move, against)))
                .collect(Collectors.toList())));
        return results;
    }

    private AsciiTable initAsciiTable(Map<Move, List<Pair<Move, GameResult>>> results) {
        AsciiTable at = new AsciiTable();
        Stream<String> header = Stream.concat(
                Stream.of(""),
                results.keySet().stream()
                        .map(Move::getName));
        Stream<Stream<String>> rows = results.entrySet().stream()
                .map(moveListEntry -> Stream.concat(
                        Stream.of(moveListEntry.getKey().getName()),
                        moveListEntry.getValue().stream()
                                .map(pair -> pair.getRight().name())));

        at.addRule();
        Stream.concat(Stream.of(header), rows).forEachOrdered(stringStream -> {
            at.addRow(stringStream.collect(Collectors.toList()));
            at.addRule();
        });

        return at;
    }

    public String toAscii() {
        return table.render();
    }

    public String toString() {
        return toAscii();
    }
}
