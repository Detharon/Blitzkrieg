package com.dth.blitzkrieg.core;

import java.util.HashMap;

public class ContinentIncome {
    HashMap<Integer, Integer> incomes;

    public ContinentIncome() {
	this(new HashMap<>());
    }

    public ContinentIncome(HashMap<Integer, Integer> incomes) {
	this.incomes = incomes;
    }

    public void addIncome(int continent, int income) {
	incomes.put(continent, income);
    }

    public int getIncome(int continent) {
	return incomes.get(continent);
    }
}
