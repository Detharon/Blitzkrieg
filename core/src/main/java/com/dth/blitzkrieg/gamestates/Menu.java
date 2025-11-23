package com.dth.blitzkrieg.gamestates;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.I18NBundle;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.dth.blitzkrieg.core.Blitzkrieg;
import com.dth.blitzkrieg.managers.LanguageBundle;
import com.dth.blitzkrieg.managers.SkinManager;
import com.dth.blitzkrieg.managers.SoundManager;

public class Menu implements Screen {

    private final Viewport viewport = new ScreenViewport();
    private final Stage stage = new Stage(viewport);
    private final Table table = new Table();

    private final Blitzkrieg game;

    private final Skin skin = SkinManager.loadUISkin();

    private final I18NBundle localization;

    public Menu(Blitzkrieg game) {
	this.game = game;

	var preferences = game.getPreferences();
	this.localization = new LanguageBundle().load(preferences.getLanguage());
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

    private TextButton clickableButton(String buttonName, Runnable action) {
	TextButton button = new TextButton(buttonName, skin);
	button.addListener(new ClickListener() {
	    @Override
	    public void clicked(InputEvent event, float x, float y) {
		SoundManager.play("click");
		action.run();
	    }
	});
	return button;
    }

    private TextButton newScreenButton(String buttonName, Screen screen) {
	return clickableButton(
	    buttonName,
	    () -> ((Game) Gdx.app.getApplicationListener()).setScreen(screen)
	);
    }

    private TextButton playButton() {
	return newScreenButton(
	    localization.get("newGame"),
	    new Play(game)
	);
    }

    private TextButton optionsButton() {
	return newScreenButton(
	    localization.get("options"),
	    new Options(game)
	);
    }

    private TextButton aboutButton() {
	return newScreenButton(
	    localization.get("aboutApp"),
	    new About(game)
	);
    }

    private TextButton exitButton() {
	return clickableButton(
	    localization.get("exit"),
	    () -> Gdx.app.exit()
	);
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
