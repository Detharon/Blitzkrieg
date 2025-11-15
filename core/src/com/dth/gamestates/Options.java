package com.dth.gamestates;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.CheckBox;
import com.badlogic.gdx.scenes.scene2d.ui.Dialog;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.SelectBox;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Slider;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.I18NBundle;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.dth.blitzkrieg.Blitzkrieg;
import com.dth.blitzkrieg.RiskPreferences;
import com.dth.managers.LanguageManager;
import com.dth.managers.SoundManager;

import java.util.function.Consumer;

public class Options implements Screen {
    private final Blitzkrieg game;

    private final Viewport viewport = new ScreenViewport();
    private final Stage stage = new Stage(viewport);

    private final Skin skin = new Skin(Gdx.files.internal("skin/uiskin.json"),
	new TextureAtlas(Gdx.files.internal("skin/uiskin.atlas")));

    private final RiskPreferences prefs;

    // Player types
    public String[] playerTypes;
    public static final int PLAYER_PASSIVE = 0;
    public static final int PLAYER_RANDOM = 1;
    public static final int PLAYER_MINMAX = 2;
    public static final int PLAYER_MINMAX_MOD = 3;
    public static final int PLAYER_MONTECARLO = 4;
    public static final int PLAYER_MONTECARLO_MOD = 5;

    // Widgets
    private Label player1valueLabel, player2valueLabel;
    private SelectBox<String> player1sb, player2sb, languageSelector;
    private Slider player1slider, player2slider, neutralSlider;
    private CheckBox historyCheckBox;

    // Labels that can change when the language changes
    private Label optionLabel, player1Label, player2Label;
    private Label neutralLabel, makeLogLabel, languageLabel;

    // Buttons that can change when the language changes
    private TextButton buttonSave, buttonReturn;

    // Localization
    private final LanguageManager languageManager = new LanguageManager();
    private I18NBundle localization;

    public Options(Blitzkrieg game) {
	this.game = game;
	this.prefs = game.getPreferences();
	this.localization = languageManager.loadBundle(prefs.getLanguage());
	setPlayerTypes();
    }

    @Override
    public void show() {
	Table table = new Table();

	optionLabel = new Label(localization.get("options"), skin);
	table.add(optionLabel).colspan(4);

	// First row (1st player)

	table.row().padTop(15).left();

	player1Label = new Label(localization.format("player", "1"), skin);
	table.add(player1Label).padRight(15);

	player1sb = new SelectBox<>(skin);
	player1sb.setItems(playerTypes);
	player1sb.addListener(new ChangeListener() {
	    @Override
	    public void changed(ChangeEvent event, Actor actor) {
		player1Changed();
	    }
	});
	table.add(player1sb).left();

	player1valueLabel = new Label("", skin);
	player1slider = new Slider(0f, 0f, 1f, false, skin);
	player1slider.addListener(new ChangeListener() {
	    @Override
	    public void changed(ChangeEvent event, Actor actor) {
		player1valueLabel.setText(Float.toString(player1slider.getValue()));
	    }
	});
	table.add(player1slider);
	player1slider.setVisible(true);

	player1slider.setValue(0);
	player1valueLabel.setText("2");

	table.add(player1valueLabel).padLeft(5).left().width(25);

	table.row().padTop(15);

	// Second row (2nd player)

	player2Label = new Label(localization.format("player", "2"), skin);
	table.add(player2Label).padRight(15).left();

	player2sb = new SelectBox<>(skin);
	player2sb.setItems(playerTypes);
	player2sb.addListener(new ChangeListener() {
	    @Override
	    public void changed(ChangeEvent event, Actor actor) {
		player2Changed();
	    }
	});
	table.add(player2sb).left();

	player2valueLabel = new Label("", skin);
	player2slider = new Slider(0f, 0f, 1f, false, skin);
	player2slider.addListener(new ChangeListener() {
	    @Override
	    public void changed(ChangeEvent event, Actor actor) {
		player2valueLabel.setText(Float.toString(player2slider.getValue()));
	    }
	});
	table.add(player2slider);
	player2slider.setVisible(true);

	player2slider.setValue(0);
	player2valueLabel.setText("2");

	table.add(player2valueLabel).padLeft(5).left().width(25);

	table.row().padTop(15);

	// Third row (neutral armies)
	neutralLabel = new Label(localization.get("neutralArmies") + ":", skin);
	table.add(neutralLabel).padRight(15).left();

	final Label neutralValueLabel = new Label("", skin);

	neutralSlider = new Slider(0f, 10f, 1f, false, skin);
	neutralSlider.addListener(new ChangeListener() {
	    @Override
	    public void changed(ChangeEvent event, Actor actor) {
		neutralValueLabel.setText(Float.toString(neutralSlider.getValue()));
	    }
	});
	table.add(neutralSlider);

	neutralSlider.setValue(2);
	neutralValueLabel.setText(Float.toString(neutralSlider.getValue()));

	table.add(neutralValueLabel).padLeft(5).width(25).left();

	table.row().padTop(15);

	makeLogLabel = new Label(localization.get("saveHistory") + ":", skin);
	table.add(makeLogLabel).padRight(15).left();

	historyCheckBox = new CheckBox("", skin);
	table.add(historyCheckBox).left();

	table.row().padTop(15);

	languageLabel = new Label(localization.get("language") + ":", skin);
	table.add(languageLabel).padRight(15).left();
	languageSelector = new SelectBox<>(skin);
	languageSelector.setItems(LanguageManager.LANGUAGES);
	languageSelector.addListener(new ChangeListener() {
	    @Override
	    public void changed(ChangeEvent event, Actor actor) {
		languageChanged();
	    }
	});
	table.add(languageSelector).left();
	table.row().padTop(15);

	buttonSave = new TextButton(localization.get("saveChanges"), skin);
	buttonSave.addListener(new ClickListener() {
	    @Override
	    public void clicked(InputEvent event, float x, float y) {
		saveButtonPressed();
	    }
	});
	table.add(buttonSave).right().fill().padRight(15);

	buttonReturn = new TextButton(localization.get("back"), skin);
	buttonReturn.addListener(new ClickListener() {
	    @Override
	    public void clicked(InputEvent event, float x, float y) {
		SoundManager.play("click");
		((Game) Gdx.app.getApplicationListener()).setScreen(new Menu(game));
	    }
	});
	table.add(buttonReturn).right().fill();

	table.setFillParent(true);
	stage.addActor(table);
	Gdx.input.setInputProcessor(stage);

	loadOptions();
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

    // =========================
    // Player changes
    // =========================

    private void player1Changed() {
	playerChanged(player1sb, player1slider, this::showPlayer1Settings);
    }

    private void player2Changed() {
	playerChanged(player2sb, player2slider, this::showPlayer2Settings);
    }

    private void playerChanged(SelectBox<String> playerSelectBox, Slider playerSlider, Consumer<Boolean> showPlayer) {
	switch (playerSelectBox.getSelectedIndex()) {
	    case PLAYER_PASSIVE, PLAYER_RANDOM -> showPlayer.accept(false);
	    case PLAYER_MINMAX, PLAYER_MINMAX_MOD -> minMaxPlayerSelected(playerSlider, showPlayer);
	    case PLAYER_MONTECARLO, PLAYER_MONTECARLO_MOD -> monteCarloPlayerSelected(playerSlider, showPlayer);
	}
    }

    private void minMaxPlayerSelected(Slider playerSlider, Consumer<Boolean> showPlayer) {
	showPlayer.accept(true);
	playerSlider.setRange(1, 8);
	playerSlider.setValue(4);
	playerSlider.setStepSize(1f);
    }

    private void monteCarloPlayerSelected(Slider playerSlider, Consumer<Boolean> showPlayer) {
	showPlayer.accept(true);
	playerSlider.setRange(300, 10000);
	playerSlider.setValue(2000);
	playerSlider.setStepSize(100f);
    }

    // =========================
    // Button presses
    // =========================

    private void languageChanged() {
	localization = languageManager.loadBundle(languageSelector.getSelected());
	optionLabel.setText(localization.get("options"));
	player1Label.setText(localization.format("player", "1"));
	player2Label.setText(localization.format("player", "2"));
	neutralLabel.setText(localization.get("neutralArmies") + ":");
	makeLogLabel.setText(localization.get("saveHistory") + ":");
	languageLabel.setText(localization.get("language") + ":");
	buttonSave.setText(localization.get("saveChanges"));
	buttonReturn.setText(localization.get("back"));

	setPlayerTypes();
	int selectedPlayer1 = player1sb.getSelectedIndex();
	player1sb.setItems(playerTypes);
	player1sb.setSelectedIndex(selectedPlayer1);
	int selectedPlayer2 = player2sb.getSelectedIndex();
	player2sb.setItems(playerTypes);
	player2sb.setSelectedIndex(selectedPlayer2);
    }

    private void saveButtonPressed() {
	SoundManager.play("click");

	game.getPreferences().setPlayer1(player1sb.getSelectedIndex());
	game.getPreferences().setPlayer1Setting((int) player1slider.getValue());

	game.getPreferences().setPlayer2(player2sb.getSelectedIndex());
	game.getPreferences().setPlayer2Setting((int) player2slider.getValue());

	game.getPreferences().setNeutrals((int) neutralSlider.getValue());

	game.getPreferences().setLogging(historyCheckBox.isChecked());
	game.getPreferences().setLanguage(languageSelector.getSelected());

	Dialog d = new Dialog(localization.get("success"), skin) {
	    public void result(Object obj) {
		remove();
	    }
	};
	d.text(localization.get("changesSaved"));
	d.button("Ok", true);

	d.setWidth(275);
	d.setX(Gdx.graphics.getWidth() / 2f - d.getWidth() / 2);
	d.setY(Gdx.graphics.getHeight() / 2f - d.getHeight() / 2);

	stage.addActor(d);
    }

    // =========================
    // Helper functions
    // =========================

    private void setPlayerTypes() {
	this.playerTypes = new String[]{
	    localization.get("passive"),
	    localization.get("random"),
	    localization.get("minMax"),
	    localization.get("minMaxMod"),
	    localization.get("monteCarlo"),
	    localization.get("monteCarloMod")
	};
    }

    private void loadOptions() {
	player1sb.setSelectedIndex(prefs.getPlayer1());
	player1Changed();
	player1slider.setValue(prefs.getPlayer1Setting());

	player2sb.setSelectedIndex(prefs.getPlayer2());
	player2Changed();
	player2slider.setValue(prefs.getPlayer2Setting());

	neutralSlider.setValue(prefs.getNeutrals());
	historyCheckBox.setChecked(prefs.isLogging());
	languageSelector.setSelected(prefs.getLanguage());
    }

    private void showPlayer1Settings(boolean show) {
	player1valueLabel.setVisible(show);
	player1slider.setVisible(show);
    }

    private void showPlayer2Settings(boolean show) {
	player2valueLabel.setVisible(show);
	player2slider.setVisible(show);
    }
}
