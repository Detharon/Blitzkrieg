package com.dth.blitzkrieg.algorithm;

import com.dth.blitzkrieg.core.Risk;

public interface Playout {
	public int playout(Risk risk, MoveGenerator moveGenerator, int player);
}
