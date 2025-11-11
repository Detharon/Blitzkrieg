package com.dth.blitzkrieg;

public class Move {
	private int from;
	private int to;
	private int army;
	private boolean lastMove;	
	
	public Move() {
		this(-1, -1, 0, true);
	}
	
	public Move(boolean lastMove) {
		this(-1, -1, 0, lastMove);
	}
	
	public Move(int from, int to, int army, boolean lastMove) {
		this.from = from;
		this.to = to;
		this.army = army;
		this.lastMove = lastMove;
	}
	
	public Move(int from, int to, int army) {
		this(from, to, army, false);
	}
	
	public int getFrom() {
		return from;
	}
	
	public int getTo() {
		return to;
	}
	
	public int getArmy() {
		return army;
	}
	
	public boolean isLastMove() {
		return lastMove;
	}
}
