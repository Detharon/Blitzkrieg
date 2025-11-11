package com.dth.algorithm;

import com.dth.blitzkrieg.Risk;

public interface Playout {
	public int playout(Risk risk, MoveGenerator moveGenerator, int player);
}
