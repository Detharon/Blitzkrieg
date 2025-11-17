package com.dth.blitzkrieg.core;

import java.util.ArrayList;
import java.util.List;

public class Province {
	public static int EUROPE = 0;
	public static int AFRICA = 1;
	public static int ASIA = 2;
	public static int OCEANIA = 3;
	public static int N_AMERICA = 4;
	public static int S_AMERICA = 5;
	
	private int id;
	private int owner;
	private int army;
	private int continent;
	
	private ArrayList<Province> neighbours;
	
	public Province(int id, int continent) {
		this(id, 0, 0, continent);
	}
	
	public Province(int id, int owner, int army, int continent) {
		this.id = id;
		this.owner = owner;
		this.army = army;
		this.continent = continent;
		
		this.neighbours = new ArrayList<Province>(3);
	}
	
	public Province(Province other) {
		this(other.getId(), other.getOwner(), other.getArmy(), other.getContinent());
		this.neighbours = new ArrayList<Province>(3);
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
