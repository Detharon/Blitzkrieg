package com.dth.managers;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.dth.blitzkrieg.Player;
import com.dth.blitzkrieg.Risk;

public class LogManager {
	private FileHandle fileHandle;
	private BufferedWriter writer;
	private Risk risk;
	private char delimiter;
	
	public LogManager(Risk risk, char delimiter) {
		this.risk = risk;
		this.delimiter = delimiter;
		fileHandle = generateFileHandle();
		
		try {
			writer = new BufferedWriter(new FileWriter(fileHandle.file().getAbsolutePath()));
		} catch (IOException e) {
			e.printStackTrace(System.out);
		}
	}
	
	public void start() {
		try {
			writer.write(generateDate());
			writer.newLine();
			
			StringBuilder finalString = new StringBuilder("");
			
			String[] values = {"Gracz", "Tura", "Dochod", "Czas"};
			for (int i = 0; i < values.length; i++) {
				finalString.append(values[i]);
				if (i != values.length - 1) {
					finalString.append(delimiter);
				}
			}
			
			writer.write(finalString.toString());
			writer.newLine();			
			writer.flush();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void saveTurn(int player, int turn, int time) {
		int income = risk.getPlayer(player).getIncome();
		
		StringBuilder finalString = new StringBuilder("");	
		
		int[] values = {player, turn, income, time};
		for (int i = 0; i < values.length; i++) {
			finalString.append(values[i]);
			if (i != values.length - 1) {
				finalString.append(delimiter);
			}
		}
		
		try {
			writer.write(finalString.toString());
			writer.newLine();			
			writer.flush();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	// ==============================
	// Helper functions
	// ==============================
	
	private FileHandle generateFileHandle() {
		String folder = "";
		String extension = ".txt";
		
		// Getting AI names		
		ArrayList<String> names = new ArrayList<String>();
		for (Player p : risk.getPlayers()) {
			if (p.getId() == 0) continue;
			
			names.add(p.getAi().getName());
		}
		
		StringBuilder tempFileName = new StringBuilder();
		// Begin filename with folder
		tempFileName.append(folder);
		
		// Put ai names in the filename
		for (String name : names) {
			tempFileName.append(name);
		}
		
		// Check if such file already exists
		boolean canCreate = false;
		int number = 1;
		
		FileHandle potentialFileHandle = Gdx.files.local("");
		while(!canCreate) {
			// Add number and extension to filename
			potentialFileHandle = Gdx.files.local(tempFileName.toString()+number+extension);
			
			if (!potentialFileHandle.exists()) {
				canCreate = true;
			} else {
				number++;
			}
		}
		
		return potentialFileHandle;
	}

	private String generateDate() {
		Calendar calendar = Calendar.getInstance();
		calendar.get(Calendar.DAY_OF_MONTH);
		calendar.get(Calendar.MONTH);
		calendar.get(Calendar.YEAR);
		calendar.get(Calendar.HOUR);
		calendar.get(Calendar.MINUTE);
		
		String date = String.format("Poczatek gry. (%d.%d.%d %d:%d)", 
				calendar.get(Calendar.DAY_OF_MONTH),
				calendar.get(Calendar.MONTH),
				calendar.get(Calendar.YEAR),
				calendar.get(Calendar.HOUR),
				calendar.get(Calendar.MINUTE)
		);
		
		return date;
	}
}
