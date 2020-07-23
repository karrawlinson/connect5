package com.genesys.connect5.domain;
/**
 * Class representing game
 * 
 * @author Karen Rawlinson
 *
 */
public class Game {
	
	private Long id;
		
	private long lastMove;

	public enum GameStatus { WAITING, IN_PROGRESS, COMPLETE };

	private GameStatus currentStatus;
	
	private int currentPlayer;
	
	private int winner;
	
	private int[][] gameState;
	
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
	
	public GameStatus getCurrentStatus() {
		return currentStatus;
	}

	public void setCurrentStatus(GameStatus currentStatus) {
		this.currentStatus = currentStatus;
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
	
	
	
}
