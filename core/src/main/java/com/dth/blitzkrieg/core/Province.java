package com.dth.blitzkrieg.core;

import java.util.ArrayList;
import java.util.List;

public class Province {
    public static int NO_OWNER = 0;

    private final int id;
    private final int continent;
    private int owner;
    private int army;

    private ArrayList<Province> neighbours;

    public Province(int id, int continent) {
	this(id, NO_OWNER, 0, continent);
    }

    public Province(int id, int owner, int army, int continent) {
	this.id = id;
	this.owner = owner;
	this.army = army;
	this.continent = continent;

	this.neighbours = new ArrayList<>(3);
    }

    public Province(Province other) {
	this(other.getId(), other.getOwner(), other.getArmy(), other.getContinent());
	this.neighbours = new ArrayList<>(3);
    }

    public boolean isNeutral() {
	return owner == NO_OWNER;
    }

    public int getId() {
	return id;
    }

    public int getOwner() {
	return owner;
    }

    public void setOwner(int owner) {
	this.owner = owner;
    }

    public int getArmy() {
	return army;
    }

    public void setArmy(int army) {
	this.army = army;
    }

    public int getContinent() {
	return continent;
    }

    public void addNeighbour(Province neighbour) {
	neighbours.add(neighbour);
    }

    public void addNeighbours(List<Province> neighbours) {
	neighbours.addAll(neighbours);
    }

    public ArrayList<Province> getNeighbours() {
	return neighbours;
    }
}
