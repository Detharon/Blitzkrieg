package com.dth.algorithm;

import java.util.List;

import com.badlogic.gdx.math.MathUtils;
import com.dth.blitzkrieg.Move;
import com.dth.blitzkrieg.Player;
import com.dth.blitzkrieg.Risk;

public class RandomPlayout implements Playout {
	private int player;
	private MoveGenerator moveGenerator;
	
	@Override
	public int playout(Risk risk, MoveGenerator moveGenerator, int player) {
		this.player = player;
		this.moveGenerator = moveGenerator;
		int winner = randomPlayout(risk);
		return winner;
	}
	
	private int randomPlayout(Risk simulation) {
		int whichOne = player;
		
		while (!simulation.isGameOver()) {
			if (whichOne > simulation.getPlayers().size() - 1) {
				whichOne = 1;
			}
			randomMove(simulation, whichOne);
			simulation.refresh();
			whichOne++;
		}
		
		for (Player p : simulation.getPlayers()) {
			if (p.getId() == 0) continue;
			if (p.isAlive()) return p.getId();
		}
		return -1;
	}
	
	private void randomMove(Risk simulation, int player) {
		List<List<Move>> allMoves = moveGenerator.generateMoves(simulation, player);
		if (allMoves.size() > 0) {
			simulation.makeAllMoves(allMoves.get(MathUtils.random(0, allMoves.size()-1)));
		} else {
			return;
		}
	}
}
