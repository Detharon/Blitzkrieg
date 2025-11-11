package com.dth.algorithm;

import java.util.ArrayList;
import java.util.List;

import com.dth.blitzkrieg.Move;
import com.dth.blitzkrieg.Player;
import com.dth.blitzkrieg.Risk;

public class MonteCarlo {
	private MonteCarloPlayer monteCarloPlayer;
	private Risk simulation;
	private List<MonteCarlo> children;
	private int player;
	private MonteCarlo parent;
	private List<Move> moves;
	private MoveGenerator moveGenerator;
	
	// UTC variables
	private int wins;
	private int total;
	private double c;
	private static double epsilon = 1e-6;
	
	private boolean mod;
	
	public MonteCarlo(MonteCarloPlayer monteCarloPlayer, boolean mod) {
		this(monteCarloPlayer, monteCarloPlayer.getRisk().getCopy(), monteCarloPlayer.getPlayer(), null, null, mod);
	}
	
	public MonteCarlo(MonteCarloPlayer monteCarloPlayer, Risk simulation, int player, MonteCarlo parent, List<Move> moves, boolean mod) {
		this.monteCarloPlayer = monteCarloPlayer;
		this.moveGenerator = monteCarloPlayer.getMoveGenerator();
		this.simulation = simulation;
		this.player = player;
		this.parent = parent;
		this.moves = moves;
		
		children = new ArrayList<MonteCarlo>();
		
		wins = 0;
		total = 0;
		c = Math.sqrt(2);
		
		this.mod = mod;
	}
	
	public List<Move> getMoves() {
		return moves;
	}
	
	public int getTotal() {
		return total;
	}
	
	public void setTotal(int total) {
		this.total = total;
	}
	
	public int getWins() {
		return wins;
	}
	
	public void setWins(int wins) {
		this.wins = wins;
	}
	
	public List<MonteCarlo> getChildren() {
		return children;
	}
	
	public void play(int time) {
		while (time > 5) {
			long beg = System.currentTimeMillis();
			this.selection();
			long end = System.currentTimeMillis();
			time -= end - beg;
		}
	}
	
	private void selection() {
		if (children.isEmpty()) {
			List<List<Move>> allMoves = moveGenerator.generateMoves(simulation, player);
			
			if (allMoves.isEmpty()) {
				simulation.refresh();
				
				int winner = -1;
				for (Player p : simulation.getPlayers()) {
					if (p.getId() == 0) continue;
					if (p.isAlive()) winner = p.getId();
				}
			
				addResult(winner);
				backpropagation(winner);
			} else {
				playout();
				expansion(allMoves);
			}
		} else {
			double bestUTC = -1;
			MonteCarlo chosen = null;
			
			for (MonteCarlo child : children) {
				// Always play unexplored node
				if (child.total == 0) {
					chosen = child;
					break;
				}
				
				double UTC = ( (double)child.wins / (child.total + epsilon))+c*Math.sqrt(Math.log(total)/(child.total + epsilon));
				
				if (UTC > bestUTC) {
					bestUTC = UTC;
					chosen = child;
				}
			}
			chosen.selection();
		}
	}
	
	private void expansion(List<List<Move>> allMoves) {
		for (List<Move> moves : allMoves) {
			Risk newSimulation = simulation.getCopy();
			newSimulation.makeAllMoves(moves);
			newSimulation.refresh();
			
			int nextPlayer = player + 1;
			if (nextPlayer > simulation.getPlayers().size() - 1) nextPlayer = 1;
			
			MonteCarlo child = new MonteCarlo(monteCarloPlayer, newSimulation, nextPlayer, this, moves, mod);
			
			if (mod) {
				int baseWins = newSimulation.getPlayers().get(player).getIncome();
				int baseTotal = newSimulation.getPlayers().get(nextPlayer).getIncome() + baseWins;
				child.setWins(baseWins);
				child.setTotal(baseTotal);
			}
			
			children.add(child);
		}
	}
	
	private void playout() {
		Risk newSimulation = simulation.getCopy();
		int winner = monteCarloPlayer.getPlayout().playout(newSimulation, moveGenerator, player);
		addResult(winner);
		backpropagation(winner);
	}
	
	private void addResult(int winner) {
		boolean hasWon = (winner == player) ? false : true;
		
		if (hasWon) wins++;
		total++;
	}

	private void backpropagation(int winner) {
		if (parent != null) {
			parent.addResult(winner);
			parent.backpropagation(winner);
		}
	}
}
