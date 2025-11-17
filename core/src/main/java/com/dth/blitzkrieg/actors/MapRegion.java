package com.dth.blitzkrieg.actors;

import java.util.ArrayList;
import java.util.List;

import com.badlogic.gdx.graphics.Pixmap;
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

    private final Texture texture;
    private final Pixmap pixmap;

    private ArrayList<MapRegion> neighbours;

    private float x;
    private float y;

    public MapRegion(Pixmap pixmap, int id, int region, float x, float y) {
	this.id = id;

	this.pixmap = pixmap;
	this.texture = new Texture(pixmap);
	this.region = region;

	this.x = x;
	this.y = y;

	neighbours = new ArrayList<>(4);

	setBounds(x, y, getWidth(), getHeight());
    }

    @Override
    public Actor hit(float x, float y, boolean touchable) {
	// First let Scene2D do simple bounds check
	if (!isVisible() || x < 0 || y < 0 || x >= getWidth() || y >= getHeight()) {
	    return null;
	}

	// libGDX Pixmap is flipped vertically vs Scene2D coordinates
	int ix = (int) x;
	int iy = pixmap.getHeight() - 1 - (int) y;

	int pixel = pixmap.getPixel(ix, iy);
	int alpha = pixel & 0x000000ff;  // last byte is alpha

	return alpha > 10 ? this : null;
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
