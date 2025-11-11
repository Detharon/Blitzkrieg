package com.dth.algorithm;

import java.util.ArrayList;
import java.util.List;

import com.dth.blitzkrieg.Move;
import com.dth.blitzkrieg.Province;
import com.dth.blitzkrieg.Risk;

public class DefaultMoveGenerator implements MoveGenerator {

	@Override
	public List<List<Move>> generateMoves(Risk risk, int id) {
		ArrayList<List<Move>> allMoves = new ArrayList<List<Move>>();
		
		// Prepare a list of all enemy provinces		
		Province[] myProvinces = Helper.getAllProvincesOf(id, risk.getProvinces());
		Province[] targets = Helper.getAllEnemyNeighbours(id, myProvinces, true);
		
		// Calculate income
		int income = risk.getPlayer(id).getIncome();

		// Calculate all offensive moves
		for (Province target : targets) {
			int highestArmy = Integer.MIN_VALUE;
			Province source = null;
			
			for (Province p : target.getNeighbours()) {
				if (p.getOwner() == id && p.getArmy() > highestArmy) {
					highestArmy = p.getArmy();
					source = p;
				}
			}
			
			// If the attacker's army is bigger than defender's, attack it with full army
			if (source.getArmy() + income > target.getArmy()) {
				ArrayList<Move> moves = new ArrayList<Move>();
				
				// Set enemy income, if neutral, set to maximum
				int enemyIncome = (target.getOwner() == 0)? Integer.MAX_VALUE : risk.getPlayer(target.getOwner()).getIncome();
				
				// Attack either with full, of with half of an army
				if (source.getArmy() + income > enemyIncome * 3
						&& (source.getArmy() + income) > target.getArmy() * 3) {
					moves.add(new Move(source.getId(), source.getId(), income));
					moves.add(new Move(source.getId(), target.getId(), (source.getArmy() + income) / 2, true));
				} else {
					moves.add(new Move(source.getId(), source.getId(), income));
					moves.add(new Move(source.getId(), target.getId(), source.getArmy() + income - 1, true));
				}

				allMoves.add(moves);
			}
		}
		
		// Defensive move, if it isn't possible to attack anything		
		if (allMoves.isEmpty()) {
			Province[] frontierProvinces = Helper.getFrontierProvinces(id, myProvinces);
			
			if (frontierProvinces.length > 0) {
				int highestArmy = Integer.MIN_VALUE;
				Province chosen = null;
				
				for (Province p : frontierProvinces) {
					if (p.getArmy() > highestArmy) {
						highestArmy = p.getArmy();
						chosen = p;
					}
				}
				
				ArrayList<Move> moves = new ArrayList<Move>();
				moves.add(new Move(chosen.getId(), chosen.getId(), income));
				allMoves.add(moves);
			}
		}
		
		return allMoves;
	}
}
