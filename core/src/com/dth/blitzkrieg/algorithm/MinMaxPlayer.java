package com.dth.blitzkrieg.algorithm;

import java.util.ArrayList;
import java.util.List;

import com.dth.blitzkrieg.core.Move;
import com.dth.blitzkrieg.core.Player;
import com.dth.blitzkrieg.core.Risk;

public class MinMaxPlayer implements ArtificialPlayer {
	private String name = "MinMax";
	
	private Risk risk;
	private int id;
	private int depth;
	private Risk simulation;
	private int whichMove;
	private List<Move> preparedMoves;
	private MoveGenerator moveGenerator;
	private boolean mod;
	
	public MinMaxPlayer(MoveGenerator moveGenerator, Risk risk, int id, int depth, boolean mod) {
		this.moveGenerator = moveGenerator;
		this.risk = risk;
		this.id = id;
		this.depth = depth;
		this.mod = mod;
		
		if (mod) {
			name = "MinMaxMod";
		}
		
		whichMove = 0;
	}
	
	@Override
	public Move makeMove() {
		if (whichMove == 0) {
			preparedMoves = prepareMoves();
			whichMove++;
			return preparedMoves.get(0);
		} else {
			whichMove = 0;
			if (preparedMoves.size() > 1) {
				return preparedMoves.get(1);
			}
			return new Move();
		}
	}
	
	@Override
	public String getName() {
		return name;
	}
	
	private List<Move> prepareMoves() {
		List<List<Move>> allMoves = moveGenerator.generateMoves(risk, id);
		
		int modDepth = depth;
		if (mod) {
			if (allMoves.size() < 4 && modDepth < 6) modDepth++;
			if (allMoves.size() < 6) modDepth++;
			if (allMoves.size() > 12 && modDepth > 2) modDepth--;
		}
		
		List<Move> chosenMoves = null;
		int bestScore = Integer.MIN_VALUE;
		
		for (List<Move> moves : allMoves) {
			simulation = risk.getCopy();
			for (Move m : moves) {
				simulation.makeMove(m);
			}
			
			int score = minmax(simulation, modDepth, modDepth, id);
			
			if (score > bestScore) {
				bestScore = score;
				chosenMoves = moves;
			}
		}
		
		return new ArrayList<Move>(chosenMoves);
	}

	private int evaluateSituation(Risk simulation) {
		int bonusMultiplier = 100;
		int points = 0;
		ArrayList<Player> players = simulation.getPlayers();

		// Points from income
		for (Player player : players) {
			if (player.getId() == 0) continue;
			
			// My income
			if (player.getId() == id) {
				points += Helper.calculateIncome(player.getId(), simulation.getProvinces(), simulation.getContinentIncome()) * bonusMultiplier;
			}
			// Their income
			else {
				points -= Helper.calculateIncome(player.getId(), simulation.getProvinces(), simulation.getContinentIncome()) * bonusMultiplier;
			}
		}
		
		return points;
	}
	
	private int minmax (Risk simulation, int depth, int maxDepth, int id) {
		if (depth == 0) return evaluateSituation(simulation);
		
		List<List<Move>> allMoves = moveGenerator.generateMoves(simulation, id);
		if (allMoves.isEmpty()) return evaluateSituation(simulation);
		
		if (id == this.id) {
			int bestScore = Integer.MIN_VALUE;
			
			for (List<Move> moves : allMoves) {
				Risk newSimulation = simulation.getCopy();
				newSimulation.makeAllMoves(moves);
				simulation.refresh();
				
				
				int score = evaluateSituation(simulation) + minmax(newSimulation, depth - 1, maxDepth, id);
				
				bestScore = Math.max(bestScore, score);
			}
			return bestScore;
		}
		
		else {
			int bestScore = Integer.MAX_VALUE;
			
			for (List<Move> moves : allMoves) {
				Risk newSimulation = simulation.getCopy();
				newSimulation.makeAllMoves(moves);
				simulation.refresh();
				
				int enemy = id + 1;
				if (enemy > risk.getPlayers().size() - 1) enemy = 1;
				
				int score = evaluateSituation(simulation) + minmax(newSimulation, depth - 1, maxDepth, enemy);
				
				bestScore = Math.min(bestScore, score);
			}
			return bestScore;
		}
	}
}
