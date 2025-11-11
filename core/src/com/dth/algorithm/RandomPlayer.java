package com.dth.algorithm;

import com.dth.blitzkrieg.Move;
import com.dth.blitzkrieg.Province;
import com.dth.blitzkrieg.Risk;

public class RandomPlayer implements ArtificialPlayer {
	private final String NAME = "Random";
	
	private Risk risk;
	private Province[] provinces;
	private int id;
	private int move;
	
	public RandomPlayer(Risk risk, int id) {
		this.risk = risk;
		this.provinces = risk.getProvinces();
		this.id = id;
		
		this.move = 0;
	}
	
	@Override
	public Move makeMove() {
		Province[] myProvinces = Helper.getAllProvincesOf(id, provinces);
		
		// If there are no provinces left, don't do anything
		if (myProvinces.length == 0) {
			return new Move(true);
		}
		
		// Deploy troops (move 0)
		if (move == 0) {
			move++;
			
			int income = risk.getPlayer(id).getIncome();
			Province chosen = Helper.getRandomProvince(myProvinces);
			return new Move(chosen.getId(), chosen.getId(), income, false);
		}
		
		// Transfer troops from internal province
		/*else if (move == 1) {
			move++;
			
			Province[] internalProvinces = Helper.getInternalProvinces(id, myProvinces);
			
			// Check if there's at least one internal province
			if (internalProvinces.length > 1) {
				Province source = Helper.getRandomProvince(internalProvinces);
				Province[] targets = Helper.getFriendlyNeighbours(id, source);
				
				if (source.getArmy() > 1 && targets.length > 0) {
					Province target = Helper.getRandomProvince(targets);

					return new Move(source.getId(), target.getId(), source.getArmy() - 1, false);
				}
			}

		}*/
		
		// Attack someone
		move = 0;
			
		Province[] frontierProvinces = Helper.getFrontierProvinces(id, myProvinces);
			
		// If there are no enemies, don't do anything
		if (frontierProvinces.length < 1) {
			return new Move(true);
		}
			
		// If there are enemies, attack them
		Province source = Helper.getRandomProvince(frontierProvinces);
		Province target = Helper.getRandomProvince(Helper.getEnemyNeighbours(id, source, true));
			
		return new Move(source.getId(), target.getId(), source.getArmy() -1, true);
	}
	
	@Override
	public String getName() {
		return NAME;
	}
}
