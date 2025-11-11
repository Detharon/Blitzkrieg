package com.dth.gamestates;

import java.util.ArrayList;
import java.util.HashMap;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Dialog;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.Window;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.dth.actors.MapRegion;
import com.dth.actors.RegionNumber;
import com.dth.algorithm.DefaultMoveGenerator;
import com.dth.algorithm.MinMaxPlayer;
import com.dth.algorithm.MonteCarloPlayer;
import com.dth.algorithm.PassivePlayer;
import com.dth.algorithm.RandomPlayer;
import com.dth.algorithm.RandomPlayout;
import com.dth.blitzkrieg.Blitzkrieg;
import com.dth.blitzkrieg.ContinentIncome;
import com.dth.blitzkrieg.DefaultBorderSetter;
import com.dth.blitzkrieg.Player;
import com.dth.blitzkrieg.Province;
import com.dth.blitzkrieg.Risk;
import com.dth.managers.LogManager;
import com.dth.managers.SoundManager;
import com.dth.managers.ZoomProcessor;

public class Play implements Screen {
	private Viewport screenViewport = new ScreenViewport();
	private Viewport hudViewport = new ScreenViewport();
	private Stage stage = new Stage(screenViewport);
	private Stage hud = new Stage(hudViewport);
	private Table table = new Table();
	
	private Blitzkrieg game;

	private Skin skin = new Skin(Gdx.files.internal("skin/uiskin.json"),
			new TextureAtlas(Gdx.files.internal("skin/uiskin.atlas")));
	
	private MapRegion[] regions = new MapRegion[42];
	private RegionNumber[] armies = new RegionNumber[42];
	
	private Province[] provinces;
	
	private HashMap<Integer, Color> ownerToColor;
	
	private LogManager log;
	
	private int[] turns;
	private ArrayList<ArrayList<Long>> times;
	
	private Risk risk;
	private Thread gameThread;	
	private Label score1, score2;
	private Button stopButton;

	public Play(Blitzkrieg game) {
		this.game = game;
	}
	
	private void createGraph() {
		provinces = new Province[42];

		// Create provinces
		for (int i = 0; i < 42; i++) {
			if (i < 7) provinces[i] = new Province(i, Province.EUROPE);
			else if (i < 19) provinces[i] = new Province(i, Province.ASIA);
			else if (i < 23) provinces[i] = new Province(i, Province.OCEANIA);
			else if (i < 29) provinces[i] = new Province(i, Province.AFRICA);
			else if (i < 38) provinces[i] = new Province(i, Province.N_AMERICA);
			else provinces[i] = new Province(i, Province.S_AMERICA);
		}
	}
	
	private void drawMap() {
		AssetManager manager = game.getManager();
		
		regions[29] = new MapRegion((Texture)manager.get("map/nam1.png"), 29, MapRegion.N_AMERICA, -150, 108);
		stage.addActor(regions[29]);
		
		regions[0] = new MapRegion((Texture)manager.get("map/eu1.png"), 0, MapRegion.EUROPE, -30, 118);
		stage.addActor(regions[0]);
		regions[1] = new MapRegion((Texture)manager.get("map/eu2.png"), 1, MapRegion.EUROPE, 0, 0);
		stage.addActor(regions[1]);
		regions[2] = new MapRegion((Texture)manager.get("map/eu3.png"), 2, MapRegion.EUROPE, 1, 66);
		stage.addActor(regions[2]);
		regions[3] = new MapRegion((Texture)manager.get("map/eu4.png"), 3, MapRegion.EUROPE, 47, 44);
		stage.addActor(regions[3]);
		regions[4] = new MapRegion((Texture)manager.get("map/eu5.png"), 4, MapRegion.EUROPE, 57, 88);
		stage.addActor(regions[4]);
		regions[5] = new MapRegion((Texture)manager.get("map/eu6.png"), 5, MapRegion.EUROPE, 58, -1);
		stage.addActor(regions[5]);
		regions[6] = new MapRegion((Texture)manager.get("map/eu7.png"), 6, MapRegion.EUROPE, 111, 12);
		stage.addActor(regions[6]);
		
		regions[7] = new MapRegion((Texture)manager.get("map/as1.png"), 7, MapRegion.ASIA, 134, -106);
		stage.addActor(regions[7]);
		regions[8] = new MapRegion((Texture)manager.get("map/as2.png"), 8, MapRegion.ASIA, 226, 49);
		stage.addActor(regions[8]);
		regions[9] = new MapRegion((Texture)manager.get("map/as3.png"), 9, MapRegion.ASIA, 208, 0);
		stage.addActor(regions[9]);
		regions[10] = new MapRegion((Texture)manager.get("map/as4.png"), 10, MapRegion.ASIA, 276, 57);
		stage.addActor(regions[10]);
		regions[11] = new MapRegion((Texture)manager.get("map/as5.png"), 11, MapRegion.ASIA, 361, 90);
		stage.addActor(regions[11]);
		regions[12] = new MapRegion((Texture)manager.get("map/as6.png"), 12, MapRegion.ASIA, 354, 56);
		stage.addActor(regions[12]);
		regions[13] = new MapRegion((Texture)manager.get("map/as7.png"), 13, MapRegion.ASIA, 356, -6);
		stage.addActor(regions[13]);
		regions[14] = new MapRegion((Texture)manager.get("map/as8.png"), 14, MapRegion.ASIA, 317, -78);
		stage.addActor(regions[14]);
		regions[15] = new MapRegion((Texture)manager.get("map/as9.png"), 15, MapRegion.ASIA, 271, -132);
		stage.addActor(regions[15]);
		regions[16] = new MapRegion((Texture)manager.get("map/as10.png"), 16, MapRegion.ASIA, 483, 32);
		stage.addActor(regions[16]);
		regions[17] = new MapRegion((Texture)manager.get("map/as11.png"), 17, MapRegion.ASIA, 548, -18);
		stage.addActor(regions[17]);
		regions[18] = new MapRegion((Texture)manager.get("map/as12.png"), 18, MapRegion.ASIA, 408, -152);
		stage.addActor(regions[18]);
		
		regions[19] = new MapRegion((Texture)manager.get("map/oc1.png"), 19, MapRegion.OCEANIA, 426, -205);
		stage.addActor(regions[19]);
		regions[20] = new MapRegion((Texture)manager.get("map/oc2.png"), 20, MapRegion.OCEANIA, 559, -208);
		stage.addActor(regions[20]);
		regions[21] = new MapRegion((Texture)manager.get("map/oc3.png"), 21, MapRegion.OCEANIA, 481, -331);
		stage.addActor(regions[21]);
		regions[22] = new MapRegion((Texture)manager.get("map/oc4.png"), 22, MapRegion.OCEANIA, 542, -357);
		stage.addActor(regions[22]);
		
		regions[23] = new MapRegion((Texture)manager.get("map/af1.png"), 23, MapRegion.AFRICA, -36, -157);
		stage.addActor(regions[23]);
		regions[24] = new MapRegion((Texture)manager.get("map/af2.png"), 24, MapRegion.AFRICA, 73, -78);
		stage.addActor(regions[24]);
		regions[25] = new MapRegion((Texture)manager.get("map/af3.png"), 25, MapRegion.AFRICA, 123, -239);
		stage.addActor(regions[25]);
		regions[26] = new MapRegion((Texture)manager.get("map/af4.png"), 26, MapRegion.AFRICA, 73, -225);
		stage.addActor(regions[26]);
		regions[27] = new MapRegion((Texture)manager.get("map/af5.png"), 27, MapRegion.AFRICA, 81, -319);
		stage.addActor(regions[27]);
		regions[28] = new MapRegion((Texture)manager.get("map/af6.png"), 28, MapRegion.AFRICA, 205, -286);
		stage.addActor(regions[28]);
		
		regions[30] = new MapRegion((Texture)manager.get("map/nam2.png"), 30, MapRegion.N_AMERICA, -280, 40);
		stage.addActor(regions[30]);
		regions[31] = new MapRegion((Texture)manager.get("map/nam3.png"), 31, MapRegion.N_AMERICA, -353, 37);
		stage.addActor(regions[31]);
		regions[32] = new MapRegion((Texture)manager.get("map/nam4.png"), 32, MapRegion.N_AMERICA, -452, 113);
		stage.addActor(regions[32]);
		regions[33] = new MapRegion((Texture)manager.get("map/nam5.png"), 33, MapRegion.N_AMERICA, -538, 93);
		stage.addActor(regions[33]);
		regions[34] = new MapRegion((Texture)manager.get("map/nam6.png"), 34, MapRegion.N_AMERICA, -448, 66);
		stage.addActor(regions[34]);
		regions[35] = new MapRegion((Texture)manager.get("map/nam7.png"), 35, MapRegion.N_AMERICA, -463, -12);
		stage.addActor(regions[35]);
		regions[36] = new MapRegion((Texture)manager.get("map/nam8.png"), 36, MapRegion.N_AMERICA, -415, -40);
		stage.addActor(regions[36]);
		regions[37] = new MapRegion((Texture)manager.get("map/nam9.png"), 37, MapRegion.N_AMERICA, -452, -122);
		stage.addActor(regions[37]);
		
		regions[38] = new MapRegion((Texture)manager.get("map/sam1.png"), 38, MapRegion.S_AMERICA, -323, -177);
		stage.addActor(regions[38]);
		regions[39] = new MapRegion((Texture)manager.get("map/sam2.png"), 39, MapRegion.S_AMERICA, -332, -282);
		stage.addActor(regions[39]);
		regions[40] = new MapRegion((Texture)manager.get("map/sam3.png"), 40, MapRegion.S_AMERICA, -298, -308);
		stage.addActor(regions[40]);
		regions[41] = new MapRegion((Texture)manager.get("map/sam4.png"), 41, MapRegion.S_AMERICA, -283, -405);
		stage.addActor(regions[41]);
		
		for (Province sourceProvince : provinces) {	
			for (Province targetProvince : sourceProvince.getNeighbours()) {
				regions[sourceProvince.getId()].addNeighbour(regions[targetProvince.getId()]);
			}
		}
		
		for (int i = 0; i < regions.length; i++) {			
			armies[i] = new RegionNumber(skin, regions[i], provinces[i].getArmy());
			stage.addActor(armies[i]);
		}
	
		for (Actor actor : regions) {
			actor.addListener(new ClickListener() {
				@Override
				public void enter(InputEvent event, float x, float y, int pointer, Actor fromActor) {
					MapRegion actor = (MapRegion)event.getListenerActor();
					
					float r = actor.getColor().r + 0.55f;
					float g = actor.getColor().g + 0.55f;
					float b = actor.getColor().b + 0.55f;
					
					if (r > 1f) r = 2 - r;
					if (g > 1f) g = 2 - g;
					if (b > 1f) b = 2 - b;

					actor.setColor(new Color(r, g, b, 1f));
					
					for (MapRegion neighbour : actor.getNeighbours()) {
						r = Math.abs(neighbour.getColor().r + 0.33f);
						g = Math.abs(neighbour.getColor().g + 0.33f);
						b = Math.abs(neighbour.getColor().b + 0.33f);
						
						if (r > 1f) r = 2 - r;
						if (g > 1f) g = 2 - g;
						if (b > 1f) b = 2 - b;
						
						neighbour.setColor(new Color(r, g, b, 1f));
					}
				}
				
				@Override
				public void exit(InputEvent event, float x, float y, int pointer, Actor toActor) {
					refreshMap();
				}
			});
		}
	}
	
	private void drawHUD() {
		TextButton buttonMenu = new TextButton("Menu", skin);
		buttonMenu.addListener(new ClickListener() {
			@Override
	        public void clicked(InputEvent event, float x, float y) {
				menuButtonClicked();
	        }
		});
		
		TextButton buttonPlay = new TextButton("Nowa gra", skin);
		buttonPlay.addListener(new ClickListener() {
			@Override
	        public void clicked(InputEvent event, float x, float y) {
				newGameButtonClicked();
	        }
		});
		
		TextButton buttonNext = new TextButton("Następna tura", skin);
		buttonNext.addListener(new ClickListener() {
			@Override
	        public void clicked(InputEvent event, float x, float y) {
				nextTurnButtonClicked();
	        }
		});
		
		TextButton buttonAllTurns = new TextButton("Wszystkie tury", skin);
		buttonAllTurns.addListener(new ClickListener() {
			@Override
	        public void clicked(InputEvent event, float x, float y) {
				allTurnsButtonClicked();
	        }
		});
		
		stopButton = new TextButton("Stop", skin);
		stopButton.addListener(new ClickListener() {
			@Override
	        public void clicked(InputEvent event, float x, float y) {
				stopButtonClicked();
	        }
		});
		
		table = new Table();
		table.setFillParent(true);
		table.add(buttonMenu);
		table.add(buttonPlay);
		table.add(buttonNext);
		table.add(buttonAllTurns);
		table.add(stopButton);
		table.top();
		table.left();
		
		hud.addActor(table);
		
		Window w = new Window("Gracze", skin);
		w.setMovable(false);
		
		Table windowTable = new Table();
		windowTable.setFillParent(true);
		windowTable.add(w).width(170).height(27*risk.getPlayers().size());
		windowTable.right();
		windowTable.top();
		
		hud.addActor(windowTable);
		
		Table wt = new Table();
		wt.pad(10).left().padTop(20);
		
		// Player 1

		String name = game.getPreferences().getPlayer1();
		if (name == "") name = "Losowy";
		
		Label p1 = new Label(name, skin);
		p1.setColor(ownerToColor.get(1));
		wt.add(p1).left().padRight(10);

		score1 = new Label(String.valueOf(risk.getPlayer(1).getIncome()), skin);
		wt.add(score1);

		wt.row();

		// Player 2
		
		name = game.getPreferences().getPlayer2();
		if (name == "") name = "Losowy";

		Label p2 = new Label(name, skin);
		p2.setColor(ownerToColor.get(2));
		wt.add(p2).left().padRight(10);

		score2 = new Label(String.valueOf(risk.getPlayer(2).getIncome()), skin);
		wt.add(score2);

		wt.row();
		wt.setFillParent(true);
		w.addActor(wt);
	}
	
	private void refresh() {
		refreshMap();
		refreshHud();
	}
	
	private void refreshMap() {
		for (int i = 0; i < provinces.length; i++) {
			armies[i].setArmy(provinces[i].getArmy());
			regions[i].setColor(ownerToColor.get(provinces[regions[i].getId()].getOwner()));
		}
	}
	
	private void refreshHud() {
		if (!game.getPreferences().getPlayer1().equals("Brak")) score1.setText(String.valueOf(risk.getPlayer(1).getIncome()));
		if (!game.getPreferences().getPlayer2().equals("Brak")) score2.setText(String.valueOf(risk.getPlayer(2).getIncome()));
	}
	
	private void checkForEnd() {
		if (risk.hasEnded()) {
			SoundManager.play("end");
			
			Dialog dialog = new Dialog("Koniec", skin, "dialog") {
			    public void result(Object obj) {
			        remove();
			    }
			};
			
			int numOfPlayers = risk.getPlayers().size() - 1;
			long totalTime[] = new long[numOfPlayers];
			
			for (int i = 0; i < numOfPlayers; i++) {
				for (long f : times.get(i)) {
					totalTime[i] += f;
				}
			}
			
			StringBuilder text = new StringBuilder("Gra zakończona.\n");
			
			int turnSum = 0;
			for (int i = 0; i < numOfPlayers; i++) {
				turnSum += turns[i];
			}
			
			text.append(String.format("Liczba tur %d.\n", turnSum));
			
			for (int i = 0; i < risk.getPlayers().size() - 1; i++) {
				text.append(String.format("Gracz %d:\n", i+1));
				text.append(String.format("Całkowity czas %dms.\n", totalTime[i]));
				text.append(String.format("Średni czas %dms.\n", totalTime[i]/(turns[i])));
			}
			
			dialog.setWidth(250);
			dialog.setHeight(250);			
			dialog.setX(Gdx.graphics.getWidth() / 2 - dialog.getWidth() / 2);
			dialog.setY(Gdx.graphics.getHeight() / 2 - dialog.getHeight() / 2);
			dialog.text(text.toString());
			dialog.button("Ok", true);
			dialog.key(Keys.ENTER, true);
			hud.addActor(dialog);
		}
	}
	
	@Override
	public void show() {
		ownerToColor = new HashMap<Integer, Color>();
		ownerToColor.put(0, Color.WHITE);
		ownerToColor.put(1, Color.RED);
		ownerToColor.put(2, Color.BLUE);
		
		InputMultiplexer multiplexer = new InputMultiplexer();
		multiplexer.addProcessor(hud);
		multiplexer.addProcessor(stage);
		multiplexer.addProcessor(new ZoomProcessor(stage));
		
		Gdx.input.setInputProcessor(multiplexer);
		
		ContinentIncome ci = new ContinentIncome();
		ci.addIncome(Province.EUROPE, 5);
		ci.addIncome(Province.ASIA, 7);
		ci.addIncome(Province.N_AMERICA, 5);
		ci.addIncome(Province.AFRICA, 3);
		ci.addIncome(Province.OCEANIA, 2);
		ci.addIncome(Province.S_AMERICA, 2);

		createGraph();
		risk = new Risk(provinces, ci);
		risk.setBorders(new DefaultBorderSetter());
		
		drawMap();
		
		// Set neutrals
		int neutrals = game.getPreferences().getNeutrals();
		if (neutrals == 0) neutrals = 2;
		
		for (Province province : provinces) {
			province.setArmy(neutrals);
		}
		
		// Set players

		risk.addPlayer(new Player(0, 0, null));
		
		String[] playerTypes = new String[2];
		playerTypes[0] = game.getPreferences().getPlayer1();
		playerTypes[1] = game.getPreferences().getPlayer2();
		
		int[] playerSettings = new int[2];
		playerSettings[0] = game.getPreferences().getPlayer1Setting();
		playerSettings[1] = game.getPreferences().getPlayer2Setting();
		
		if (playerTypes[0] == "") {
			playerTypes[0] = "Losowy";
		}
		
		if (playerTypes[1] == "") {
			playerTypes[1] = "Losowy";
		}
		
		for (int i = 0; i < playerTypes.length; i++) {
			String playerType = playerTypes[i];
			int playerSetting = playerSettings[i];
			
			if (playerType.equals("Pasywny")) {
				risk.addPlayer(new Player(i + 1, 3, new PassivePlayer(risk, i + 1)));
			}
			else if (playerType.equals("Losowy")) {
				risk.addPlayer(new Player(i + 1, 3, new RandomPlayer(risk, i + 1)));
			}
			else if (playerType.equals("Minimax")) {
				risk.addPlayer(new Player(i + 1, 3, new MinMaxPlayer(new DefaultMoveGenerator(), risk, i + 1, playerSetting-1, false)));
			}
			else if (playerType.equals("MinimaxMod")) {
				risk.addPlayer(new Player(i + 1, 3, new MinMaxPlayer(new DefaultMoveGenerator(), risk, i + 1, playerSetting-1, true)));
			}			
			else if (playerType.equals("MonteCarlo")) {
				risk.addPlayer(new Player(i + 1, 3, new MonteCarloPlayer(new DefaultMoveGenerator(), new RandomPlayout(), risk, i + 1, playerSetting, false)));
			}
			else if (playerType.equals("MonteCarloMod")) {
				risk.addPlayer(new Player(i + 1, 3, new MonteCarloPlayer(new DefaultMoveGenerator(), new RandomPlayout(), risk, i + 1, playerSetting, true)));
			}
		}
		
		// Add regions to players
		assignRegions();
		
		// Draw hud and refresh map
		
		drawHUD();
		refresh();
		
		// Initialize counters
		
		turns = new int[risk.getPlayers().size()-1];
		times = new ArrayList<ArrayList<Long>>();
		
		for (int i = 0; i < risk.getPlayers().size() - 1; i++) {
			turns[i] = 0;
			times.add(new ArrayList<Long>(100));
		}
		
		// Create and initialize logger
		if (game.getPreferences().isLogging()) {
			log = new LogManager(risk, ',');
			log.start();
		}
	}
	
	private void assignRegions() {
		for (int i = 1; i < risk.getPlayers().size(); i++) {
			boolean found = false;
			while(!found) {
				int chosenProvince = MathUtils.random(0, provinces.length - 1);
				
				// Was the province already chosen?
				if (provinces[chosenProvince].getOwner() != 0) continue;
				
				// Make sure that neighbours are neutral
				boolean unique = true;
				for (Province neighbour : provinces[chosenProvince].getNeighbours()) {
					if (neighbour.getOwner() != 0) unique = false;
				}
				
				if (unique) {
					provinces[chosenProvince].setOwner(i);
					found = true;
				}
			}
		}
	}

	@Override
	public void render(float delta) {
		Gdx.gl.glClearColor(0, 0.05f, 0.15f, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		
		stage.act(delta);
		stage.draw();
		
		hud.draw();
	}

	@Override
	public void resize(int width, int height) {
		screenViewport.update(width, height, true);
		hudViewport.update(width, height, true);
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
		
	}
	
	// ==============================
	// Helpers
	// ==============================
	
	private void nextTurn() {
		int whichPlayer = risk.getWhichPlayer();
		
		long begin = System.currentTimeMillis();
		risk.nextTurn();
		long end = System.currentTimeMillis();
		
		long totalTime = end-begin;
		
		times.get(whichPlayer-1).add(totalTime);
		turns[whichPlayer-1]++;
		
		// Store information in log
		if (game.getPreferences().isLogging()) {
			log.saveTurn(whichPlayer, turns[whichPlayer-1], (int)totalTime);
		}
		
		refresh();
		checkForEnd();
	}
	
	private void playNextTurnSound() {
		int whichPlayer = risk.getWhichPlayer();
		
		switch (whichPlayer) {
			case 1:
				SoundManager.play("move1");
				break;
			case 2:
				SoundManager.play("move2");
				break;
		}
	}
	
	// ==============================
	// Buttons
	// ==============================
	
	private void menuButtonClicked() {
		SoundManager.play("click");
		
		// If the game is running, stop it.
		/*try {
			if (gameThread != null && gameThread.isAlive()) {
				gameThread.interrupt();
			}
			gameThread.join();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}*/
		
        ((Game)Gdx.app.getApplicationListener()).setScreen(new Menu(game));
	}
	
	private void newGameButtonClicked() {
		SoundManager.play("click");
		
		// If the game is running, stop it.
		/*try {
			if (gameThread != null && gameThread.isAlive()) {
				gameThread.interrupt();
			}
			gameThread.join();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}*/
		
		// Play next game		
        ((Game)Gdx.app.getApplicationListener()).setScreen(new Play(game));
	}
	
	private void nextTurnButtonClicked() {
		if (gameThread == null || !gameThread.isAlive()) {
			playNextTurnSound();
			nextTurn();
		}
	}
	
	private void allTurnsButtonClicked() {
		if (gameThread == null || !gameThread.isAlive()) {
			gameThread = new Thread(new Runnable() {
				public void run() { 
				while (!risk.hasEnded()) {
					nextTurn();
					try {
						Thread.sleep(100);
					} catch (InterruptedException e) {
						break;
					}
				}
			}});
			
			gameThread.start();
		}
	}
	
	private void stopButtonClicked() {
		if (gameThread != null)
		gameThread.interrupt();
	}
	
}
