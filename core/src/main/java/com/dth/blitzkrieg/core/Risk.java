package com.dth.blitzkrieg.core;

import java.util.ArrayList;
import java.util.List;

import com.dth.blitzkrieg.algorithm.Helper;

public class Risk {
    private final ArrayList<Player> players;
    private final Province[] provinces;
    private final ContinentIncome continentIncome;
    private int whichPlayer;
    private boolean end;

    public Risk(Province[] provinces, ContinentIncome continentIncome) {
	this(new ArrayList<>(), provinces, continentIncome);
    }

    public Risk(ArrayList<Player> players, Province[] provinces, ContinentIncome continentIncome) {
	this.players = players;
	this.provinces = provinces;
	this.continentIncome = continentIncome;

	whichPlayer = 1;

	end = false;
    }

    public void nextTurn() {
	if (isGameOver()) return;
	System.gc();

	if (players.get(whichPlayer).getAi() != null && players.get(whichPlayer).isAlive())
	    while (true) {
		Move m = players.get(whichPlayer).getAi().makeMove();
		makeMove(m);
		if (m.lastMove()) break;
	    }

	do {
	    whichPlayer += 1;
	} while (whichPlayer < players.size() && !players.get(whichPlayer).isAlive());

	if (whichPlayer > players.size() - 1) {
	    whichPlayer = 1;
	}

	refresh();
	isGameOver();
    }

    public Risk getCopy() {
	Province[] copyProvinces = new Province[provinces.length];
	for (int i = 0; i < provinces.length; i++) {
	    copyProvinces[i] = new Province(provinces[i]);
	}

	for (Province copyProvince : copyProvinces) {
	    provinces[copyProvince.getId()]
		.getNeighbours().stream().map(Province::getId).forEach(neighborId ->
		    copyProvince.addNeighbour(copyProvinces[neighborId])
		);
	}

	ArrayList<Player> copyPlayers = new ArrayList<>();

	for (Player player : players) {
	    copyPlayers.add(new Player(player));
	}

	return new Risk(copyPlayers, copyProvinces, this.getContinentIncome());
    }

    public void addPlayer(Player player) {
	players.add(player);
    }

    public Player getPlayer(int id) {
	return players.stream()
	    .filter(player -> player.getId() == id)
	    .findFirst()
	    .orElseThrow(() -> new IllegalArgumentException("Requested a non-existing player with id " + id));
    }

    public Province[] getProvinces() {
	return provinces;
    }

    public ArrayList<Player> getPlayers() {
	return players;
    }

    public int getWhichPlayer() {
	return whichPlayer;
    }

    public boolean hasEnded() {
	return end;
    }

    public ContinentIncome getContinentIncome() {
	return continentIncome;
    }

    public boolean makeMove(Move move) {
	if (isGameOver()) return true;

	// Check for an empty move
	if (move.army() < 1) return true;

	Province fromProvince = provinces[move.from()];
	Province toProvince = provinces[move.to()];
	int army = move.army();


	// Check if the move is deployment
	if (fromProvince.equals(toProvince)) {
	    fromProvince.setArmy(fromProvince.getArmy() + army);
	    return true;
	}

	// Check if armies can move at all
	if (fromProvince.getArmy() < 2 || army < 1 ||
	    fromProvince.getArmy() <= army) {
	    return false;
	}

	// If a fight does not occur, just move the army
	if (fromProvince.getOwner() == toProvince.getOwner()) {
	    fromProvince.setArmy(fromProvince.getArmy() - army);
	    toProvince.setArmy(toProvince.getArmy() + army);

	    return true;
	}

	// Otherwise allow a fight

	RiskFight fight = new RiskFight(army, toProvince.getArmy());
	fight.calculateFight();

	if (fight.attackerWon()) {
	    fromProvince.setArmy(fromProvince.getArmy() - army);

	    toProvince.setArmy(fight.getAttackers());
	    toProvince.setOwner(fromProvince.getOwner());

	    return true;
	} else {
	    fromProvince.setArmy(fromProvince.getArmy() - fight.getAttackers());
	    toProvince.setArmy(fight.getDefenders());

	    return true;
	}
    }

    public void makeAllMoves(List<Move> moves) {
	for (Move m : moves) {
	    makeMove(m);
	}
    }

    public boolean isGameOver() {
	int playersAlive = 0;

	for (Player player : players) {
	    if (player.getId() == 0) continue;
	    if (player.isAlive()) playersAlive++;
	}

	if (playersAlive > 1) return false;
	else {
	    end = true;
	    return true;
	}
    }

    public void refresh() {
	for (Player player : players) {
	    if (player.getId() == 0) continue;

	    if (Helper.getAllProvincesOf(player.getId(), provinces).length == 0) {
		player.setAlive(false);
	    } else {
		calculateIncome(player);
	    }
	}
    }

    private void calculateIncome(Player player) {
	player.setIncome(Helper.calculateIncome(player.getId(), provinces, continentIncome));
    }

}
