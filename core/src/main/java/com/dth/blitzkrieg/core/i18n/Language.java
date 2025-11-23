package com.dth.blitzkrieg.core.i18n;

import java.util.Arrays;
import java.util.Optional;

public enum Language {
    ENGLISH("English", "en"),
    POLISH("Polski", "pl"),
    RUSSIAN("Русский", "ru");

    private final String displayName;
    private final String localeCode;

    Language(String displayName, String localeCode) {
        this.displayName = displayName;
        this.localeCode = localeCode;
    }

    public String getDisplayName() {
        return displayName;
    }

    public String getLocaleCode() {
        return localeCode;
    }

    public static Optional<Language> fromDisplayName(String displayName) {
        return Arrays.stream(values())
            .filter(language -> language.getDisplayName().equals(displayName))
            .findFirst();
    }
}
