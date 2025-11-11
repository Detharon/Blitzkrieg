package com.dth.blitzkrieg;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;

public class RiskPreferences {
	// constants
    private static final String PREF_PLAYER1 = "player1";
    private static final String PREF_PLAYER2 = "player2";
    private static final String PREF_NEUTRALS = "neutrals";
    private static final String PLAYER1_SETTING = "player1setting";
    private static final String PLAYER2_SETTING = "player2setting";
    private static final String LOGGING = "logging";
    
    private static final String PREFS_NAME = "risk";
    
    public RiskPreferences() {}
    
    protected Preferences getPrefs()
    {
        return Gdx.app.getPreferences(PREFS_NAME);
    }
    
    public String getPlayer1() {
    	return getPrefs().getString(PREF_PLAYER1);
    }
    
    public void setPlayer1(String player1) {
    	getPrefs().putString(PREF_PLAYER1, player1);
        getPrefs().flush();
    }
    
    public String getPlayer2() {
    	return getPrefs().getString(PREF_PLAYER2);
    }
    
    public void setPlayer2(String player2) {
    	getPrefs().putString(PREF_PLAYER2, player2);
        getPrefs().flush();
    }
    
    public int getNeutrals() {
    	return getPrefs().getInteger(PREF_NEUTRALS);
    }
    
    public void setNeutrals(int neutrals) {
    	getPrefs().putInteger(PREF_NEUTRALS, neutrals);
    	getPrefs().flush();
    }
    
    public int getPlayer1Setting() {
    	return getPrefs().getInteger(PLAYER1_SETTING);
    }
    
    public void setPlayer1Setting(int value) {
    	getPrefs().putInteger(PLAYER1_SETTING, value);
    	getPrefs().flush();
    }
    
    public int getPlayer2Setting() {
    	return getPrefs().getInteger(PLAYER2_SETTING);
    }
    
    public void setPlayer2Setting(int value) {
    	getPrefs().putInteger(PLAYER2_SETTING, value);
    	getPrefs().flush();
    }
    
    public void setMonteCarloTime(int value) {
    	getPrefs().putInteger(PLAYER2_SETTING, value);
    	getPrefs().flush();
    }
    
    public boolean isLogging() {
    	return getPrefs().getBoolean(LOGGING);
    }
    
    public void setLogging(boolean value) {
    	getPrefs().putBoolean(LOGGING, value);
    	getPrefs().flush();
    }
}
