package com.dth.algorithm;

import java.util.List;

import com.dth.blitzkrieg.Move;
import com.dth.blitzkrieg.Risk;

public interface MoveGenerator {
	public List<List<Move>> generateMoves(Risk risk, int id);
}
