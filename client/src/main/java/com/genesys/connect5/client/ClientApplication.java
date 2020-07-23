package com.genesys.connect5.client;

import java.util.Scanner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.genesys.connect5.domain.Game;
import com.genesys.connect5.domain.Player;
/**
 * Simple client to play connect5
 *  
 * @author Karen Rawlinson
 *
 */
@SpringBootApplication
public class ClientApplication implements CommandLineRunner {

	@Autowired
	Connect5Service connect5Service;
	
	@Override
	public void run(String... args) throws Exception {
		Scanner scanner = new Scanner(System.in);
		namePrompt(scanner);
		
		Player player = connect5Service.joinGame();
		Game game = connect5Service.getGame(player.getGameId());
		
		do {
			if (game.getCurrentStatus().equals(Game.GameStatus.IN_PROGRESS)) {
				if(game.getCurrentPlayer() == player.getPlayerId()) {
					makeMove(scanner, game, player.getPlayerId());
				} else {
					System.out.println("Waiting for the other player to move ...");
				}
			} else {
				System.out.println("Waiting for the other player to join ...");
			}
			Thread.sleep(2000);
			game = connect5Service.getGame(game.getId());
		} while(!(game.getCurrentStatus().equals(Game.GameStatus.COMPLETE)));
		
		drawGame(game);
		if(game.getWinner() == player.getPlayerId()) {
			System.out.println("Game over ... you win!! ");
		} else {
			System.out.println("Game over ... you lose!! ");
		}
		
	}
	
	private void namePrompt(Scanner scanner) {
		
		System.out.println("What's your name?");
		String name = null;
		if (scanner.hasNext()) {
			name = scanner.nextLine();
		}
		System.out.println("Hello " + name + "... joining game");
	}

	private void makeMove(Scanner scanner, Game game, int playerId) {
		drawGame(game);
		int column = movePrompt(scanner);
		try {
			game = connect5Service.move(game.getId(), column-1, playerId);
		} catch (RuntimeException e) {
			System.out.println("Failed to make move - " + e.getMessage() + " - please retry");
			column = movePrompt(scanner);
			game = connect5Service.move(game.getId(), column-1, playerId);
		}
		drawGame(game);
	}

	private int movePrompt(Scanner scanner) {
		int column = 0;
		// ensure user can only enter a value between 1 and 9
		while(column < 1 || column > 9) {
			System.out.println("It's your move, pick a column [1-9]");
			while(!scanner.hasNextInt()) {
				System.out.println("Invalid column, pick a column [1-9]" + " - please retry");
				scanner.next();
			}
			column = scanner.nextInt();
		}
		
		return column;
	}

	private void drawGame(Game game) {
		
		for (int row = 5; row >= 0; row--) {
			for (int col = 0; col < 9; col++) {
				System.out.print("[ " + formatEntry(game.getGameState()[row][col]) + " ]");
			}
			System.out.println();
		}
		System.out.println();
		
	}
	
	private String formatEntry(int value) {
		if(value == 1) {
			return "x";
		} else if (value == 2) {
			return "o";
		}
		
		return " ";
	}

	public static void main(String[] args) {
		SpringApplication.run(ClientApplication.class, args);
	}
}