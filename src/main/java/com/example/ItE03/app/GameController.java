package com.example.ItE03.app;

import com.example.ItE03.game.GameVariation;
import com.example.ItE03.game.Move;
import com.example.ItE03.utils.MyPreconditions;
import com.google.common.base.Preconditions;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.beryx.textio.TextIoFactory;

import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Slf4j
@RequiredArgsConstructor
public class GameController {

    private final HMACGenerator hmacGenerator;
    private final MovePickerFactory movePickerFactory;
    private final ConsoleInteractor consoleInteractor;

    public static void main(String[] args) {
        ConsoleInteractor consoleInteractor = new ConsoleInteractor(TextIoFactory.getTextIO());
        GameController gc = new GameController(new HMACGenerator(), MovePicker::new, consoleInteractor);

        gc.runGame("Rock", "Paper", "Scissors");
        consoleInteractor.close();
    }

    private GameVariation initGame(String... moveNames) {
        List<String> moveNamesList = Arrays.asList(moveNames);

        checkMoveNames(moveNamesList);

        return new GameVariation(moveNamesList);
    }

    public void runGame(String... moveNames) {
        GameVariation gameVariation = initGame(moveNames);

        Move computerMove = pickMove(gameVariation);

        String key = hmacGenerator.key(256);
        String hmac = hmacGenerator.hmac(key, computerMove.getName());

        consoleInteractor.display(String.format("HMAC: %s", hmac));
        //Display and read option
        List<InputOption> inputOptions = makeUserInputOptions(gameVariation.getMoves());

        InputOption action = null;
        consoleInteractor.displayInputOptions(inputOptions);
        while (!isExit(action) && !isMove(action)) {
            action = consoleInteractor.askForAction(inputOptions);
            if (isMove(action)) {
                Move userMove = moveFromInputOption(gameVariation, action);

                consoleInteractor.display(String.format(
                        "User move: %s; Computer move: %s;  Result is: %s",
                        userMove.getName(),
                        computerMove.getName(),
                        gameVariation.resultFor(userMove, computerMove)));
                consoleInteractor.display(String.format("HMAC key: %s", key));
            }
            if (isHelp(action)) {
                consoleInteractor.display(makeTable(gameVariation).toAscii());
            }
        }
    }

    private ResultTable makeTable(GameVariation gameVariation) {
        return new ResultTable(gameVariation);
    }

    private Move moveFromInputOption(GameVariation gameVariation, InputOption action) {
        return gameVariation.getMoveByOrder(Integer.parseInt(action.getKey()));
    }

    private boolean isHelp(InputOption action) {
        return action != null && action.getType().equals(UserInputType.Help);
    }

    private boolean isMove(InputOption action) {
        return action != null && action.getType().equals(UserInputType.Move);
    }

    private boolean isExit(InputOption action) {
        return action != null && action.getType().equals(UserInputType.Exit);
    }

    public List<InputOption> makeUserInputOptions(Set<Move> moves) {
        List<InputOption> options = moves.stream()
                .map(this::fromMove)
                .collect(Collectors.toList());

        options.add(new InputOption("?", "Help", UserInputType.Help));
        options.add(new InputOption("0", "Exit", UserInputType.Exit));

        return options;
    }

    private InputOption fromMove(Move move) {
        return new InputOption(String.valueOf(move.getOrder()), move.getName(), UserInputType.Move);
    }

    public Move pickMove(GameVariation gameVariation) {
        return movePickerFactory
                .movePicker(gameVariation.getMoves())
                .pickOne();
    }

    private void checkMoveNames(List<String> moveNamesList) {
        MyPreconditions.checkNotNullOrEmpty(moveNamesList,
                "arguments must not be null or empty, but was: " + moveNamesList);
        Preconditions.checkArgument(moveNamesList.size() >= 3
                ,"must have at least 3 arguments, but was: " + moveNamesList.size());
        MyPreconditions.checkIfOdd(moveNamesList,
                "must have odd number of arguments, but was: "+ moveNamesList.size());
        MyPreconditions.checkIfDistinct(moveNamesList, "arguments must be distinct");
    }
}
