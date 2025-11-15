package com.dth.blitzkrieg;

import com.dth.algorithm.ArtificialPlayer;

public class Player {
    private final int id;
    private int income;
    private boolean canTransfer;
    private boolean alive;
    private ArtificialPlayer ai;

    public Player(int id, int income, ArtificialPlayer ai) {
	this.id = id;
	this.income = income;
	this.ai = ai;
	canTransfer = false;
	alive = true;
    }

    public Player(Player other) {
	this(other.getId(), other.getIncome(), other.getAi());
    }

    public int getId() {
	return id;
    }

    public int getIncome() {
	return income;
    }

    public void setIncome(int income) {
	this.income = income;
    }

    public ArtificialPlayer getAi() {
	return ai;
    }

    public boolean canTransfer() {
	return canTransfer;
    }

    public void setCanTransfer(boolean canTransfer) {
	this.canTransfer = canTransfer;
    }

    public boolean isAlive() {
	return alive;
    }

    public void setAlive(boolean alive) {
	this.alive = alive;
    }
}
