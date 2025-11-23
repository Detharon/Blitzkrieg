package com.dth.blitzkrieg.core.map;

import java.util.List;

// Part of the map.json, Gdx requires public fields to deserialize them
public class Map {
    public String name;
    public String description;
    public List<Region> regions;
}
