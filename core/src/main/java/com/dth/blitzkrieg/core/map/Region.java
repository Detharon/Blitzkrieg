package com.dth.blitzkrieg.core.map;

import java.util.List;

// Part of the map.json, Gdx requires public fields to deserialize them
public class Region {
    public int id;
    public String name;
    public int income;
    public List<Province> provinces;
}
