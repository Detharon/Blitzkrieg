package com.dth.blitzkrieg;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Texture;
import com.dth.gamestates.Menu;
import com.dth.managers.SoundManager;

public class Blitzkrieg extends Game {
	public static AssetManager manager = new AssetManager();
	public static RiskPreferences preferences = new RiskPreferences();
	
	public RiskPreferences getPreferences() {
		return preferences;
	}
	
	public AssetManager getManager() {
		return manager;
	}

	@Override
	public void create() {
		setScreen(new Menu(this));
		
		loadSounds();
		loadGfx();
	}
	
	private void loadGfx() {
		//TODO: use texture atlas
		
		String[] textures = {"eu1.png", "eu2.png", "eu3.png", "eu4.png", "eu5.png", "eu6.png", "eu7.png",
				"as1.png", "as2.png", "as3.png", "as4.png", "as5.png", "as6.png", "as7.png", "as8.png", "as9.png",
				"as10.png", "as11.png", "as12.png", "oc1.png", "oc2.png", "oc3.png", "oc4.png",
				"af1.png", "af2.png", "af3.png", "af4.png", "af5.png", "af6.png",
				"nam1.png", "nam2.png", "nam3.png", "nam4.png", "nam5.png", "nam6.png", "nam7.png", "nam8.png", "nam9.png",
				"sam1.png", "sam2.png", "sam3.png", "sam4.png"
				};
		
		for (String texture : textures) {
			manager.load(Gdx.files.internal("map/"+texture).path(), Texture.class);
		}
		
		while(!manager.update());
	}
	
	private void loadSounds() {
		String[] sounds = {"click.wav", "end.wav", "move1.wav", "move2.wav"};
		
		for (String sound : sounds) {
			SoundManager.load(Gdx.files.internal("sounds/"+sound).path(), sound.substring(0, sound.indexOf(".")));
		}
	}	

	@Override
	public void dispose() {
		manager.dispose();
	}
}
