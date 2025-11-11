package com.dth.actors;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.utils.Disposable;

public class RegionNumber extends Actor implements Disposable {
	private Skin skin;
	private BitmapFont font;
	private MapRegion parent;
	private int army;
	
	public RegionNumber(Skin skin, MapRegion parent, int army) {
		this.skin = skin;
		font = skin.getFont("default-font");
		
		this.parent = parent;
		this.army = army;
	}
	
	public void setArmy(int army) {
		this.army = army;
	}

	@Override
	public void dispose() {
		skin.dispose();
		font.dispose();
	}
	
	@Override
	public void draw(Batch b, float parentAlpha) {
		float fontHeight = font.getBounds(String.valueOf(army)).height;
		float fontWidth = font.getBounds(String.valueOf(army)).width;
		float border = 6;
		
		b.setColor(Color.WHITE);
		b.draw(skin.getRegion("textfield"), parent.getX() + parent.getWidth() / 2 - border / 2,
				parent.getY() + parent.getHeight() / 2 - border /  2 - fontHeight, fontWidth + border, fontHeight + border);
		
		font.setColor(getRegionColor());
		font.draw(b, String.valueOf(army), parent.getX() + parent.getWidth() / 2, parent.getY() + parent.getHeight() / 2);
	}
	
	private Color getRegionColor() {
		switch(parent.getRegion()) {
			case 0:
				return Color.valueOf("0078ff");
			case 1:
				return Color.RED;
			case 2:
				return Color.GREEN;
			case 3:
				return Color.CYAN;
			case 4:
				return Color.MAGENTA;
			case 5:
				return Color.YELLOW;
			default:
				return Color.WHITE;
		}

	}

}
