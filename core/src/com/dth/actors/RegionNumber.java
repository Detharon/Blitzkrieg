package com.dth.actors;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.utils.Disposable;

public class RegionNumber extends Actor implements Disposable {
    private final Skin skin;
    private final BitmapFont font;
    private final MapRegion parent;
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

    public void draw(Batch b, float parentAlpha) {
	String text = String.valueOf(army);
	GlyphLayout layout = new GlyphLayout(font, text);

	float border = 6;
	float fontWidth = layout.width;
	float fontHeight = layout.height;

	b.setColor(Color.WHITE);
	b.draw(
	    skin.getRegion("textfield"),
	    parent.getX() + parent.getWidth() / 2 - border / 2,
	    parent.getY() + parent.getHeight() / 2 - border / 2 - fontHeight,
	    fontWidth + border,
	    fontHeight + border
	);

	font.setColor(getRegionColor());
	font.draw(b, layout,
	    parent.getX() + parent.getWidth() / 2,
	    parent.getY() + parent.getHeight() / 2
	);
    }

    private Color getRegionColor() {
	return switch (parent.getRegion()) {
	    case 0 -> Color.valueOf("0078ff");
	    case 1 -> Color.RED;
	    case 2 -> Color.GREEN;
	    case 3 -> Color.CYAN;
	    case 4 -> Color.MAGENTA;
	    case 5 -> Color.YELLOW;
	    default -> Color.WHITE;
	};
    }
}
