package com.genesys.connect5.domain;
import java.util.Arrays;
import java.util.Random;

/**
 * Class representing game
 * 
 * @author Karen Rawlinson
 *
 */
public class Game {
	
	public final static int NUM_ROWS = 6;
	
	public final static int NUM_COLUMNS = 9;
	
	private Long id = Math.abs(new Random().nextLong());
		
	private long lastMove;

	public enum GameStatus { WAITING, IN_PROGRESS, COMPLETE };
	
	private GameStatus currentStatus;
	
	private int currentPlayer;
	
	private int winner;
	
	private int[][] gameState = new int[NUM_ROWS][NUM_COLUMNS];
	
	public GameStatus getCurrentStatus() {
		return currentStatus;
	}

	public void setCurrentStatus(GameStatus currentStatus) {
		this.currentStatus = currentStatus;
	}
	
	public long getLastMove() {
		return lastMove;
	}

	public void setLastMove(long lastMove) {
		this.lastMove = lastMove;
	}
	
	public int getWinner() {
		return winner;
	}

	public void setWinner(int winner) {
		this.winner = winner;
	}
	
	public int[][] getGameState() {
		return gameState;
	}

	public void setGameState(int[][] gameState) {
		this.gameState = gameState;
	}

	public int getCurrentPlayer() {
		return currentPlayer;
	}

	public void setCurrentPlayer(int currentPlayer) {
		this.currentPlayer = currentPlayer;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	@Override
	public String toString() {
		return "Game [id=" + id + ", lastMove=" + lastMove + ", currentStatus=" + currentStatus + ", currentPlayer="
				+ currentPlayer + ", winner=" + winner + ", gameState=" + Arrays.toString(gameState) + "]";
	}
	
	
	
	
}
