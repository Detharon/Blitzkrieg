package com.dth.blitzkrieg.core.map;

import java.util.List;

// Part of the map.json, Gdx requires public fields to deserialize them
public class Province {
    public int id;
    public String texture;
    public int offset_x;
    public int offset_y;
    public List<Integer> neighbors;
}
