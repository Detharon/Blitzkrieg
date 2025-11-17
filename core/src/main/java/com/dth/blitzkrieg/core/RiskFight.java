package com.dth.blitzkrieg.core;

import com.badlogic.gdx.math.MathUtils;

public class RiskFight {
	private int attackers;
	private int defenders;
	
	
	public RiskFight() {
		this(0,0);
	}
	
	public RiskFight(int attackers, int defenders) {
		this.attackers = attackers;
		this.defenders = defenders;
	}
	
	public void setArmies(int attackers, int defenders) {
		this.attackers = attackers;
		this.defenders = defenders;
	}
	
	public int getAttackers() {
		return attackers;
	}
	
	public int getDefenders() {
		return defenders;
	}
	
	public boolean attackerWon() {
		return (defenders == 0);
	}
	
	public void calculateFight() {
			while(attackers > 0 && defenders > 0) {
			int[] attackerRolls = new int[3];
			int[] defenderRolls = new int[3];
			
			attackerRolls[0] = roll();
			if (attackers > 1) attackerRolls[1] = roll();
			if (attackers > 2) attackerRolls[2] = roll();

			defenderRolls[0] = roll();
			if (defenders > 1) defenderRolls[1] = roll();
			if (defenders > 2) defenderRolls[2] = roll();
			
			// highest dice
			int a = getHighest(attackerRolls);
			int d = getHighest(defenderRolls);
			
			if (a > d) {
				defenders--;
			} else {
				attackers--;
			}
			
			// second dice
			a = getHighest(attackerRolls);
			d = getHighest(defenderRolls);
			if (a > 0 && d > 0) {
				if (a > d) {
					defenders--;
				} else {
					attackers--;
				}
			}
		}
	}
	
	private int getHighest(int[] rolls) {
		int highest = 0;
		int index = -1;
		
		for (int i = 0; i < rolls.length; i++) {
			if (rolls[i] > highest) {
				highest = rolls[i];
				index = i;
			}
		}
		
		if (index != -1) {
			rolls[index] = 0;
		}
		
		return highest;
	} 
	
	private int roll() {
		return MathUtils.random(1,6);
	}
}
