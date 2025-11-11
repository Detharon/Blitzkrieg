package com.dth.algorithm;

import java.util.ArrayList;
import java.util.List;

import com.dth.blitzkrieg.Move;
import com.dth.blitzkrieg.Risk;

public class MonteCarloPlayer implements ArtificialPlayer {
	private String name = "MonteCarlo";
	
	private Risk risk;
	private int id;
	private int time;
	private int whichMove;
	private List<Move> preparedMoves;
	private MoveGenerator moveGenerator;
	private Playout playout;
	boolean firstMove;
	boolean mod;
		
	public MonteCarloPlayer(MoveGenerator moveGenerator, Playout playout, Risk risk, int id, int time, boolean mod) {
		this.moveGenerator = moveGenerator;
		this.playout = playout;
		this.risk = risk;
		this.id = id;
		this.time = time;
		
		this.firstMove = true;
		this.mod = mod;
		
		if (mod) {
			name = "MonteCarloMod";
		}
		
		whichMove = 0;
	}

	@Override
	public Move makeMove() {
		if (whichMove == 0) {
			preparedMoves = prepareMoves();
			whichMove++;
			return preparedMoves.get(0);
		} else {
			whichMove = 0;
			if (preparedMoves.size() > 1) {
				return preparedMoves.get(1);
			}
			return new Move();
		}
	}
	
	public MoveGenerator getMoveGenerator() {
		return moveGenerator;
	}
	
	public Playout getPlayout() {
		return playout;
	}
	
	public Risk getRisk() {
		return risk;
	}
	
	public int getPlayer() {
		return id;
	}
	
	@Override
	public String getName() {
		return name;
	}
	
	private List<Move> prepareMoves() {
		List<Move> chosenMoves = null;
		int bestTotal = Integer.MIN_VALUE;
		
		MonteCarlo mc = new MonteCarlo(this, mod);
		mc.play(time);
	
		for (MonteCarlo child : mc.getChildren()) {
			if (child.getTotal() > bestTotal) {
				bestTotal = child.getTotal();
				chosenMoves = child.getMoves();
			}
		}

		return new ArrayList<Move>(chosenMoves);
	}
}
