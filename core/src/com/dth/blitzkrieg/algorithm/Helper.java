package com.dth.blitzkrieg.algorithm;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.badlogic.gdx.math.MathUtils;
import com.dth.blitzkrieg.core.ContinentIncome;
import com.dth.blitzkrieg.core.Province;

public class Helper {
	public static List<Integer> getPossibleMoves(Province province) {
		List<Integer> moves = new ArrayList<Integer>();
		
		for (Province target : province.getNeighbours()) {
			moves.add(target.getId());
		}
		
		return moves;
	}
	
	public static Province[] getAllProvincesOf(int owner, Province[] provinces) {
		ArrayList<Province> ownedProvinces = new ArrayList<Province>();
		
		for (Province target : provinces) {
			if (target.getOwner() == owner) ownedProvinces.add(target);
		}

		return ownedProvinces.toArray(new Province[ownedProvinces.size()]);
	}
	
	public static Province[] getEnemyNeighbours(int owner, Province province, boolean neutrals) {
		List<Province> enemies = new ArrayList<Province>();
		
		for (Province p : province.getNeighbours()) {
			if (p.getOwner() != owner) {
				if (neutrals || p.getOwner() != 0) {
					enemies.add(p);
				}
			}
				
		}
		
		return enemies.toArray(new Province[enemies.size()]);		
	}
	
	public static Province[] getAttackableEnemyNeighbours(int owner, Province province, boolean neutrals) {
		List<Province> enemies = Arrays.asList(getEnemyNeighbours(owner, province, neutrals));
		List<Province> attackableEnemies = new ArrayList<Province>();;
		
		int myArmy = province.getArmy();
		for (Province p : enemies) {
			if (myArmy > p.getArmy()) {
				attackableEnemies.add(p);
			}
		}
		
		return attackableEnemies.toArray(new Province[attackableEnemies.size()]);
	}
	
	public static Province[] getFriendlyNeighbours(int owner, Province province) {
		List<Province> friends = new ArrayList<Province>();
		
		for (Province p : province.getNeighbours()) {
			if (p.getOwner() == owner) friends.add(p);
		}
		
		return friends.toArray(new Province[friends.size()]);	
	}
	
	public static Province getRandomProvince(Province[] provinces) {
		if (provinces.length == 0) return null;
		
		int chosen = MathUtils.random(0, provinces.length - 1);
		return provinces[chosen];
	}
	
	public static Province[] getFrontierProvinces(int owner, Province[] provinces) {
		ArrayList<Province> provincesWithEnemies = new ArrayList<Province>();
		
		for (Province province : provinces) {
			if (province.getOwner() != owner) continue;
			
			if ((getEnemyNeighbours(owner, province, true).length > 0)) {
				provincesWithEnemies.add(province);
			}
		}
		
		return provincesWithEnemies.toArray(new Province[provincesWithEnemies.size()]);
	}
	
	public static Province[] getInternalProvinces(int owner, Province[] provinces) {
		ArrayList<Province> internalProvinces = new ArrayList<Province>();
		
		for (Province province : provinces) {
			if (getEnemyNeighbours(owner, province, true).length == 0) {
				internalProvinces.add(province);
			}
		}
		
		return internalProvinces.toArray(new Province[internalProvinces.size()]);
	}
	
	public static int calculateIncome(int owner, Province[] provinces, ContinentIncome continentIncome) {
		int income = 3;
		
		for (int i = 0; i < 6; i++) {
			boolean hasAll = true;
			for (Province province : provinces) {
				if (province.getContinent() == i && province.getOwner() != owner) {
					hasAll = false;
					break;
				}
			}
			
			if (hasAll) {
				income += continentIncome.getIncome(i);
			}
		}
		
		return income;
	}
	
	public static Province[] getAllEnemyNeighbours(int owner, Province[] provinces, boolean neutrals) {
		Set<Province> enemies = new HashSet<Province>();
		
		for (Province province : provinces) {
			enemies.addAll(Arrays.asList(Helper.getEnemyNeighbours(owner, province, neutrals)));
		}
		
		return enemies.toArray(new Province[enemies.size()]);
	}
}
