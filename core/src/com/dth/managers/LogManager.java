package com.dth.managers;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.utils.I18NBundle;
import com.dth.blitzkrieg.Player;
import com.dth.blitzkrieg.Risk;

public class LogManager {
    private final Risk risk;
    private final char delimiter;
    private final I18NBundle localization;
    private BufferedWriter writer;

    public LogManager(Risk risk, char delimiter, I18NBundle localization) {
	this.risk = risk;
	this.delimiter = delimiter;
	this.localization = localization;
	FileHandle fileHandle = generateFileHandle();

	try {
	    writer = new BufferedWriter(new FileWriter(fileHandle.file().getAbsolutePath()));
	} catch (IOException e) {
	    e.printStackTrace(System.out);
	}

    }

    public void start() {
	try {
	    writer.write(dateHeader());
	    writer.newLine();

	    StringBuilder finalString = new StringBuilder();

	    String[] values = {
		localization.get("playerLog"),
		localization.get("turnLog"),
		localization.get("incomeLog"),
		localization.get("timeLog")
	    };

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
	String extension = ".csv";

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
	while (!canCreate) {
	    // Add number and extension to filename
	    potentialFileHandle = Gdx.files.local(tempFileName.toString() + number + extension);

	    if (!potentialFileHandle.exists()) {
		canCreate = true;
	    } else {
		number++;
	    }
	}

	return potentialFileHandle;
    }

    private String dateHeader() {
	return localization.format(
	    "beginningOfGameLog",
	    LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"))
	);
    }
}
