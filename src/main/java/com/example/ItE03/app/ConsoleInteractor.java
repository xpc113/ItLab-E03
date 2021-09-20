package com.example.ItE03.app;

import lombok.extern.slf4j.Slf4j;
import org.beryx.textio.InputReader;
import org.beryx.textio.TextIO;
import org.beryx.textio.TextTerminal;

import java.util.List;

@Slf4j
public class ConsoleInteractor {

    TextIO textIO;
    TextTerminal terminal;

    public ConsoleInteractor(TextIO textIO) {
        this.textIO = textIO;
        terminal = textIO.getTextTerminal();
    }

    public void display(String s) {
        terminal.println(s);
    }

    public void displayInputOptions(List<InputOption> inputOptions) {
        display("Available options:");
        inputOptions.forEach(this::displayInputOption);
    }

    public InputOption askForAction(List<InputOption> inputOptions) {
        return readAction(inputOptions, "Select acton:");
    }

    private InputOption readAction(List<InputOption> inputOptions, String prompt) {
        String key = textIO.newStringInputReader()
                .withValueChecker(keyInOptionsListChecker(inputOptions))
                .read(prompt);
        return parseActionKey(inputOptions, key);
    }

    private InputOption parseActionKey(List<InputOption> inputOptions, String key) {
        return inputOptions.stream()
                .filter(inputOption -> inputOption.getKey().equals(key))
                .findFirst()
                .orElse(null);
    }

    private void displayInputOption(InputOption inputOption) {
        terminal.println(inputOptionMessage(inputOption));
    }

    private String inputOptionMessage(InputOption inputOption) {
        return String.format("%s: %s", inputOption.getKey(), inputOption.getDisplayName());
    }

    private InputReader.ValueChecker<String> keyInOptionsListChecker(List<InputOption> inputOptions) {
        return (val, itemName) -> inputOptions.stream()
                .anyMatch(inputOption ->
                        val.equals(inputOption.getKey())) ? null : List.of("Invalid option: " + val);
    }

    public void close() {
        terminal.dispose();
    }
}
