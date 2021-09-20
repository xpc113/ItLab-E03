package com.example.ItE03;

import com.example.ItE03.app.ConsoleInteractor;
import com.example.ItE03.app.GameController;
import com.example.ItE03.app.HMACGenerator;
import com.example.ItE03.app.MovePicker;
import lombok.extern.slf4j.Slf4j;
import org.beryx.textio.TextIoFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.Arrays;

@SpringBootApplication
@Slf4j
public class ItE03Application implements CommandLineRunner {

	public static void main(String[] args) {
		SpringApplication.run(ItE03Application.class, args);
	}

	private final ConsoleInteractor consoleInteractor =
			new ConsoleInteractor(TextIoFactory.getTextIO());
	private final GameController gameController =
			new GameController(new HMACGenerator(), MovePicker::new, consoleInteractor);

	@Override
	public void run(String... args) throws Exception {
		try {
			gameController.runGame(args);
		}
		catch (Exception e) {

			log.error("Error: {}", Arrays.toString(e.getStackTrace()));
			consoleInteractor.display(String.format("Error: %s", e.getMessage()));
		}
		finally {
			consoleInteractor.close();
		}
	}
}
