package com.dth.blitzkrieg.gamestates;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.Window;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.I18NBundle;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.dth.blitzkrieg.core.Blitzkrieg;
import com.dth.blitzkrieg.managers.LanguageBundle;
import com.dth.blitzkrieg.managers.SkinManager;
import com.dth.blitzkrieg.managers.SoundManager;

public class About implements Screen {
    private Viewport viewport = new ScreenViewport();
    private Stage stage = new Stage(viewport);
    private Table table = new Table();
    private Blitzkrieg game;

    private final Skin skin = SkinManager.loadUISkin();

    private final I18NBundle localization;

    public About(Blitzkrieg game) {
	this.game = game;
	var preferences = game.getPreferences();
	this.localization = new LanguageBundle().load(preferences.getLanguage());
    }

    @Override
    public void show() {
	Window window = new Window(localization.get("aboutApp"), skin);

	String text = localization.get("appPurpose")
	    + ".\n\n" + localization.format("author", "Piotr Strąk")
	    + "\n" + localization.format("supervisor", "dr Przemysław Juszczuk");
	Label label = new Label(text, skin);
	label.setWrap(true);
	window.add(label).width(350).row();

	TextButton buttonExit = new TextButton(localization.get("back"), skin);
	buttonExit.addListener(new ClickListener() {
	    @Override
	    public void clicked(InputEvent event, float x, float y) {
		SoundManager.play("click");
		((Game) Gdx.app.getApplicationListener()).setScreen(new Menu(game));
	    }
	});
	window.add(buttonExit).row();

	table.add(window).row();
	table.setFillParent(true);
	stage.addActor(table);
	Gdx.input.setInputProcessor(stage);
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
	// TODO Auto-generated method stub

    }

    @Override
    public void resume() {
	// TODO Auto-generated method stub

    }

    @Override
    public void hide() {
	// TODO Auto-generated method stub

    }

    @Override
    public void dispose() {
	// TODO Auto-generated method stub
    }
}
