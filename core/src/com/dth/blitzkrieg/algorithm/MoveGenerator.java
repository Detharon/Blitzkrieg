package com.dth.blitzkrieg.algorithm;

import java.util.List;

import com.dth.blitzkrieg.core.Move;
import com.dth.blitzkrieg.core.Risk;

public interface MoveGenerator {
	public List<List<Move>> generateMoves(Risk risk, int id);
}
