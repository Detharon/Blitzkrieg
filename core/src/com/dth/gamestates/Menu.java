package com.dth.gamestates;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.I18NBundle;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.dth.blitzkrieg.Blitzkrieg;
import com.dth.managers.LanguageManager;
import com.dth.managers.SoundManager;

public class Menu implements Screen {

    private final Viewport viewport = new ScreenViewport();
    private final Stage stage = new Stage(viewport);
    private final Table table = new Table();

    private final Blitzkrieg game;

    private final Skin skin = new Skin(Gdx.files.internal("skin/uiskin.json"),
	new TextureAtlas(Gdx.files.internal("skin/uiskin.atlas")));

    private final I18NBundle localization;

    public Menu(Blitzkrieg game) {
	this.game = game;

	var preferences = game.getPreferences();
	this.localization = new LanguageManager().loadBundle(preferences.getLanguage());
    }

    @Override
    public void show() {
	table.add(playButton()).padBottom(10).row();
	table.add(optionsButton()).padBottom(10).row();
	table.add(aboutButton()).padBottom(10).row();
	table.add(exitButton()).row();

	table.setFillParent(true);
	stage.addActor(table);
	Gdx.input.setInputProcessor(stage);
    }

    private TextButton playButton() {
	TextButton playButton = new TextButton(localization.get("newGame"), skin);
	playButton.addListener(new ClickListener() {
	    @Override
	    public void clicked(InputEvent event, float x, float y) {
		SoundManager.play("click");
		((Game) Gdx.app.getApplicationListener()).setScreen(new Play(game));
	    }
	});
	return playButton;
    }

    private TextButton optionsButton() {
	TextButton optionsButton = new TextButton(localization.get("options"), skin);
	optionsButton.addListener(new ClickListener() {
	    @Override
	    public void clicked(InputEvent event, float x, float y) {
		SoundManager.play("click");
		((Game) Gdx.app.getApplicationListener()).setScreen(new Options(game));
	    }
	});
	return optionsButton;
    }

    private TextButton aboutButton() {
	TextButton aboutButton = new TextButton(localization.get("aboutApp"), skin);
	aboutButton.addListener(new ClickListener() {
	    @Override
	    public void clicked(InputEvent event, float x, float y) {
		SoundManager.play("click");
		((Game) Gdx.app.getApplicationListener()).setScreen(new About(game));
	    }
	});
	return aboutButton;
    }

    private TextButton exitButton() {
	TextButton exitButton = new TextButton(localization.get("exit"), skin);
	exitButton.addListener(new ClickListener() {
	    @Override
	    public void clicked(InputEvent event, float x, float y) {
		SoundManager.play("click");
		Gdx.app.exit();
	    }
	});
	return exitButton;
    }

    @Override
    public void render(float delta) {
	Gdx.gl.glClearColor(0, 0, 0, 1);
	Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

	stage.act(delta);
	stage.draw();
    }

    @Override
    public void resize(int width, int height) {
	viewport.update(width, height, true);
    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {
	dispose();
    }

    @Override
    public void dispose() {
	stage.dispose();
    }

}
