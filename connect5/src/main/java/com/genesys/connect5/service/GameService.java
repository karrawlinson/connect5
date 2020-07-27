package com.genesys.connect5.service;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.genesys.connect5.domain.Game;
import com.genesys.connect5.domain.Move;
import com.genesys.connect5.domain.Player;
/**
 * Service for playing the game
 * 
 * @author Karen Rawlinson
 *
 */
@Service
public class GameService {

	private Map<Long, Game> games = new HashMap<Long, Game>();

	private Logger logger = LoggerFactory.getLogger(GameService.class);

	/**
	 * Return a game given the id
	 * @param id
	 * @return
	 */
	public Game getGame(Long id) {

		return games.get(id);
	}

	/**
	 * Checks if there are any games in the WAITING state - if so add a second player
	 * otherwise just create a new game with the first player and set status to WAITING
	 * 
	 * @return
	 */
	public Player joinPendingGame() {
		Game game;
		Optional<Game> pendingGame = games.values().stream().filter(
				g -> g.getCurrentStatus().equals(Game.GameStatus.WAITING)).findFirst();
		int playerId = 1;
		if (pendingGame.isPresent()) {
			game = pendingGame.get();
			game.setCurrentStatus(Game.GameStatus.IN_PROGRESS);
			game.setLastMove(System.currentTimeMillis());
			playerId = 2;
		} else {
			game = new Game();
			game.setCurrentStatus(Game.GameStatus.WAITING);
			game.setCurrentPlayer(1);
			playerId = 1;
		}
		games.put(game.getId(), game);
		return new Player(playerId, game.getId());

	}

	/**
	 * Apply the move and update the check
	 * to reflect the next current player and the game status
	 * 
	 * @param game
	 * @param move
	 * @return
	 */
	public Game playMove(Game game, Move move) {
		int[][] gameState = game.getGameState();
		for (int row = 0; row < Game.NUM_ROWS; row++) {
			if (gameState[row][move.getColumn()] == 0) {
				gameState[row][move.getColumn()] = move.getPlayer();
				game.setLastMove(System.currentTimeMillis());
				int otherPlayer = move.getPlayer() == 1 ? 2 : 1;
				game.setCurrentPlayer(otherPlayer);
				break;
			}
		}
		
		if (isGameWon(game, move.getPlayer())) {
			game.setCurrentStatus(Game.GameStatus.COMPLETE);
			game.setWinner(move.getPlayer());
		}

		return game;
	}

	/**
	 * Check to see if there are 5 in a row in the various directions 
	 * 
	 * @param game
	 * @param player
	 * @return
	 */
	public boolean isGameWon(Game game, int player) {
		int[][] state = game.getGameState();
		
		// check horizontal	
		for (int i = 0; i < Game.NUM_ROWS; i++) {
			for (int j = 0; j < Game.NUM_COLUMNS - 4; j++) {
				if (state[i][j] == player && state[i][j + 1] == player && state[i][j + 2] == player
						&& state[i][j + 3] == player && state[i][j + 4] == player) {
					return true;
				}
			}
		}
		// check vertical
		for (int i = 0; i < Game.NUM_ROWS - 4; i++) {
			for (int j = 0; j < Game.NUM_COLUMNS; j++) {
				if (state[i][j] == player && state[i + 1][j] == player && state[i + 2][j] == player
						&& state[i + 3][j] == player && state[i + 4][j] == player) {
					return true;
				}
			}
		}
		// check asc diagonal
		for (int i = 4; i < Game.NUM_ROWS; i++) {
			for (int j = 0; j < Game.NUM_COLUMNS - 4; j++) {
				if (state[i][j] == player && state[i - 1][j + 1] == player && state[i - 2][j + 2] == player
						&& state[i - 3][j + 3] == player && state[i - 4][j + 4] == player)
					return true;
			}
		}
		// check desc diagonal
		for (int i = 4; i < Game.NUM_ROWS; i++) {
			for (int j = 4; j < Game.NUM_COLUMNS; j++) {
				if (state[i][j] == player && state[i - 1][j - 1] == player && state[i - 2][j - 2] == player
						&& state[i - 3][j - 3] == player && state[i - 4][j - 4] == player)
					return true;
			}
		}
		return false;
	}


	public void setGames(Map<Long, Game> games) {
		this.games = games;
	}
	
	public Map<Long, Game> getGames() {
		return games;
	}

	/**
	 * Task to COMPLETE any games where clients have stopped playing
	 */
	@Scheduled(fixedRate = 300000)
	public void expireGames() {
		logger.info("Checking pending games");
		for (Game g : games.values()) {
			if (g.getCurrentStatus() == Game.GameStatus.IN_PROGRESS
					&& g.getLastMove() < (System.currentTimeMillis() - 1000 * 60 * 5)) {
				logger.info("Timing out game as time to move has expired");
				g.setCurrentStatus(Game.GameStatus.COMPLETE);
				int winner = g.getCurrentPlayer() == 1 ? 2 : 1;
				g.setWinner(winner);
			}
		}
	}
}
