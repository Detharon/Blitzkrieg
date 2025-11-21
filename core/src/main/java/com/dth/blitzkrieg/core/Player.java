package com.dth.blitzkrieg.core;

import com.dth.blitzkrieg.algorithm.ArtificialPlayer;

public class Player {
    private final int id;
    private final ArtificialPlayer ai;

    private int income;
    private boolean alive;

    public Player(int id, int income, ArtificialPlayer ai) {
	this.id = id;
	this.income = income;
	this.ai = ai;
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

    public boolean isAlive() {
	return alive;
    }

    public void setAlive(boolean alive) {
	this.alive = alive;
    }
}
