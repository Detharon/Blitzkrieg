package com.dth.blitzkrieg.managers;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;

public class SkinManager {

    public static Skin loadUISkin() {
	return new Skin(
	    Gdx.files.internal("skin/uiskin.json"),
	    new TextureAtlas(Gdx.files.internal("skin/uiskin.atlas"))
	);
    }

}
