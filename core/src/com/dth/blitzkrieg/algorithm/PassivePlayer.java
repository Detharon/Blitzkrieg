package com.dth.blitzkrieg.algorithm;

import com.dth.blitzkrieg.core.Move;
import com.dth.blitzkrieg.core.Province;
import com.dth.blitzkrieg.core.Risk;

public class PassivePlayer implements ArtificialPlayer {
	private final String NAME = "Passive";
	
	private Risk risk;
	private Province[] provinces;
	int id;
	
	public PassivePlayer(Risk risk, int id) {
		this.risk = risk;
		this.provinces = risk.getProvinces();
		this.id = id;
	}

	@Override
	public Move makeMove() {
		if (Helper.getAllProvincesOf(id, provinces).length == 0) {
			return new Move(true);
		} else {
			int income = risk.getPlayer(id).getIncome();
			
			Province chosen = Helper.getAllProvincesOf(id, provinces)[0];
			return new Move(chosen.getId(), chosen.getId(), income, true);
		}
	}
	
	@Override
	public String getName() {
		return NAME;
	}
}
