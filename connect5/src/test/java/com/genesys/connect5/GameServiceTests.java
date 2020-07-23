package com.genesys.connect5;

import java.util.HashMap;
import java.util.Map;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.genesys.connect5.domain.Game;
import com.genesys.connect5.domain.Move;
import com.genesys.connect5.domain.Player;
import com.genesys.connect5.service.GameService;

@SpringBootTest
public class GameServiceTests {

	@Autowired
	GameService gameService;

	private long gameId;
	
	@Test
	public void testGetGame() {
		assert(gameService.getGame((long) 1) == null);
		Map<Long, Game> games = new HashMap<Long, Game>();
		Game game1 = new Game();
		games.put(game1.getId(), game1);
		gameId = game1.getId();
		gameService.setGames(games);
		assert(gameService.getGame(gameId) != null);
	}
	
	@Test
	public void testIsGameWonVertical() {
		Game game = new Game();
		int[][] gameStatus = new int[6][9];
		for(int i=0; i<5; i++) {
			gameStatus[i][0] = 1;
		}
		game.setGameState(gameStatus);
		assert(gameService.isGameWon(game, 1) == true);
		assert(gameService.isGameWon(game, 2) == false);
		for(int i=5; i<5; i++) {
			gameStatus[i][0] = 1;
		}
		game.setGameState(gameStatus);
		assert(gameService.isGameWon(game, 1) == true);
		assert(gameService.isGameWon(game, 2) == false);
	}
	
	@Test
	public void testIsGameWonHorizontal() {
		Game game = new Game();
		int[][] gameStatus = new int[6][9];
		for(int i=0; i<5; i++) {
			gameStatus[0][i] = 1;
		}
		game.setGameState(gameStatus);
		assert(gameService.isGameWon(game, 1) == true);
		assert(gameService.isGameWon(game, 2) == false);
	}
	
	@Test
	public void testIsGameWonDiagnonalAsc() {
		Game game = new Game();
		int[][] gameStatus = new int[6][9];
		for(int i=0; i<5; i++) {
			gameStatus[i][i] = 1;
		}
		game.setGameState(gameStatus);
		assert(gameService.isGameWon(game, 1) == true);
		assert(gameService.isGameWon(game, 2) == false);
	}
	
	@Test
	public void testIsGameWonDiagnonalDesc() {
		Game game = new Game();
		int[][] gameStatus = new int[6][9];
		gameStatus[5][4] = 1;
		gameStatus[4][5] = 1;
		gameStatus[3][6] = 1;
		gameStatus[2][7] = 1;
		gameStatus[1][8] = 1;
		game.setGameState(gameStatus);
		assert(gameService.isGameWon(game, 1) == true);
		assert(gameService.isGameWon(game, 2) == false);
	}
	
	@Test
	public void testJoinPendingGame() {
		Player player1 = gameService.joinPendingGame();
		assert(player1.getGameId() > 0);
		assert(player1.getPlayerId() == 1);
		assert(gameService.getGames().values().size() == 1);
		Game newGame = gameService.getGames().get(player1.getGameId());
		assert(newGame.getCurrentStatus() == Game.GameStatus.WAITING);
		assert(newGame.getCurrentPlayer() == 1);
		Player player2 = gameService.joinPendingGame();
		assert(player2.getGameId() == player1.getGameId());
		assert(player2.getPlayerId() == 2);
		assert(gameService.getGames().values().size() == 1);
		Game existingGame = gameService.getGames().get(player2.getGameId());
		assert(existingGame.getCurrentPlayer() == 1);
		assert(existingGame.getCurrentStatus() == Game.GameStatus.IN_PROGRESS);
	}
	
	@Test
	public void testPlayMove() {
		Game game = new Game();
		game.setCurrentPlayer(1);				
		game = gameService.playMove(new Game(), new Move(2, 1));
		
		assert(game.getGameState()[0][1] == 2);
		assert(game.getCurrentPlayer() == 1);
		
		game = gameService.playMove(new Game(), new Move(1, 1));
		
		assert(game.getGameState()[0][1] == 1);
		assert(game.getCurrentPlayer() == 2);
	}
}
