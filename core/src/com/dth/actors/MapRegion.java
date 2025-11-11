package com.dth.actors;

import java.util.ArrayList;
import java.util.List;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.utils.Disposable;

public class MapRegion extends Actor implements Disposable {
	public static int EUROPE = 0;
	public static int AFRICA = 1;
	public static int ASIA = 2;
	public static int OCEANIA = 3;
	public static int N_AMERICA = 4;
	public static int S_AMERICA = 5;
	
	private int id;
	
	private int region;
	
	private Texture texture;
		
	private ArrayList<MapRegion> neighbours;
	
	private float x;
	private float y;

	
	public MapRegion (Texture texture, int id, int region, float x, float y) {
		this.id = id;
		
		this.texture = texture;		
		this.region = region;
		
		this.x = x;
		this.y = y;
		
		neighbours = new ArrayList<MapRegion>(4);
		
		setBounds(x, y, getWidth(), getHeight());
	}
	
	public int getId() {
		return id;
	}
	
	public int getRegion() {
		return region;
	}

	public void addNeighbour(MapRegion neighbour) {
		neighbours.add(neighbour);
	}
	
	public void addNeighbours(List<MapRegion> neighbours) {
		neighbours.addAll(neighbours);
	}
	
	public List<MapRegion> getNeighbours() {
		return neighbours;
	}
	
	@Override
	public void dispose() {
		texture.dispose();
	}
	
	@Override
	public float getWidth() {
		return texture.getWidth();
	}
	
	@Override
	public float getHeight() {
		return texture.getHeight();
	}
	
	@Override
	public void act(float delta) {
		super.act(delta);
	}
	
	@Override
	public void draw(Batch b, float parentAlpha) {
		// Set color
		b.setColor(this.getColor());
		b.draw(texture, x, y);
	}

}
