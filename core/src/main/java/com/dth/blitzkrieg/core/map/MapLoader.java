package com.dth.blitzkrieg.core.map;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.utils.Json;

// Part of the map.json, Gdx requires public fields to deserialize them
public class MapLoader {
    public static Map loadMap(String name) {
	Json json = new Json();
	String location = "map/" + name + "/map.json";
	return json.fromJson(Map.class, Gdx.files.internal(location));
    }
}
