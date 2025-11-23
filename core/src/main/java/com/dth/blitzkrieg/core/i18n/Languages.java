package com.dth.blitzkrieg.core.i18n;

import java.util.Arrays;

public class Languages {
    public static String[] getDisplayNames() {
	return Arrays.stream(Language.values())
	    .map(Language::getDisplayName)
	    .toArray(String[]::new);
    }
}
