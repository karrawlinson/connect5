package com.genesys.connect5.domain;
/**
 * Class representing move
 * 
 * @author Karen Rawlinson
 *
 */
public class Move {

	private int player;
	private int column;
	
	public int getPlayer() {
		return player;
	}
	public void setPlayer(int player) {
		this.player = player;
	}
	public int getColumn() {
		return column;
	}
	public void setColumn(int column) {
		this.column = column;
	}
	public Move(int player, int column) {
		super();
		this.player = player;
		this.column = column;
	}
	
	public Move() {
		super();
	}
	
}
