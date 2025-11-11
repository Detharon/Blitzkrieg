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
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.dth.blitzkrieg.Blitzkrieg;
import com.dth.managers.SoundManager;

public class Menu implements Screen {
	private Viewport viewport = new ScreenViewport();
	private Stage stage = new Stage(viewport);
	private Table table = new Table();
	
	private Blitzkrieg game;
	
	private Skin skin = new Skin(Gdx.files.internal("skin/uiskin.json"),
			new TextureAtlas(Gdx.files.internal("skin/uiskin.atlas")));
	
	public Menu(Blitzkrieg game) {
		this.game = game;
	}

	@Override
	public void show() {
		TextButton buttonPlay = new TextButton("Nowa gra", skin);
		buttonPlay.addListener(new ClickListener() {
			@Override
	        public void clicked(InputEvent event, float x, float y) {
				SoundManager.play("click");
	            ((Game)Gdx.app.getApplicationListener()).setScreen(new Play(game));
	        }
		});
		table.add(buttonPlay).padBottom(10).row();
		
		TextButton buttonOptions = new TextButton("Opcje", skin);
		buttonOptions.addListener(new ClickListener() {
			@Override
	        public void clicked(InputEvent event, float x, float y) {
				SoundManager.play("click");
	            ((Game)Gdx.app.getApplicationListener()).setScreen(new Options(game));
	        }
		});
		table.add(buttonOptions).padBottom(10).row();
		
		TextButton buttonAbout = new TextButton("O programie", skin);
		buttonAbout.addListener(new ClickListener() {
			@Override
	        public void clicked(InputEvent event, float x, float y) {
				SoundManager.play("click");
	            ((Game)Gdx.app.getApplicationListener()).setScreen(new About(game));
	        }
		});
		table.add(buttonAbout).padBottom(10).row();
		
		TextButton buttonExit = new TextButton("Wyjście", skin);
		buttonExit.addListener(new ClickListener() {
			@Override
	        public void clicked(InputEvent event, float x, float y) {
				SoundManager.play("click");
	            Gdx.app.exit();
	        }
		});
		table.add(buttonExit).row();
			
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
		dispose();
	}
	
	@Override
	public void dispose() {
		stage.dispose();
	}

}
