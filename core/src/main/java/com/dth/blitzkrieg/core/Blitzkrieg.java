package com.dth.blitzkrieg.core;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Pixmap;
import com.dth.blitzkrieg.gamestates.Menu;
import com.dth.blitzkrieg.managers.SoundManager;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Collections;
import java.util.List;
import java.util.stream.Stream;

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
	streamMapImages().forEach(imageName ->
	    manager.load(Gdx.files.internal(imageName).path(), Pixmap.class)
	);

	while (!manager.update());
    }

    private List<String> streamMapImages() {
	Path directory = Paths.get("map/Europe");
	try (Stream<Path> files = Files.list(directory)) {
	    return files
		.filter(Files::isRegularFile)
		.map(Path::toString)
		.filter(string -> string.toLowerCase().endsWith(".png"))
		.toList();
	} catch (IOException e) {
	    e.printStackTrace();
	}
	return Collections.emptyList();
    }

    private void loadSounds() {
	String[] sounds = {"click.wav", "end.wav", "move1.wav", "move2.wav"};

	for (String sound : sounds) {
	    SoundManager.load(Gdx.files.internal("sounds/" + sound).path(), sound.substring(0, sound.indexOf(".")));
	}
    }

    @Override
    public void dispose() {
	manager.dispose();
    }
}
