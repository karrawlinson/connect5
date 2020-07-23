package com.genesys.connect5.domain;
/**
 * Class representing player 
 * 
 * @author Karen Rawlinson
 *
 */
public class Player {

	private int playerId;
	private long gameId;
		
	public int getPlayerId() {
		return playerId;
	}
	public void setPlayerId(int playerId) {
		this.playerId = playerId;
	}
	public long getGameId() {
		return gameId;
	}
	public void setGameId(long gameId) {
		this.gameId = gameId;
	}
	public Player(int playerId, long gameId) {
		super();
		this.playerId = playerId;
		this.gameId = gameId;
	}
	
	
}
