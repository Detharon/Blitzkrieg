package com.dth.blitzkrieg.core;

public record Move(int from, int to, int army, boolean lastMove) {
    public Move() {
	this(-1, -1, 0, true);
    }

    public Move(boolean lastMove) {
	this(-1, -1, 0, lastMove);
    }

    public Move(int from, int to, int army) {
	this(from, to, army, false);
    }
}
