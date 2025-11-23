package com.dth.blitzkrieg.managers;

import com.badlogic.gdx.assets.AssetDescriptor;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.assets.loaders.I18NBundleLoader;
import com.badlogic.gdx.assets.loaders.I18NBundleLoader.I18NBundleParameter;
import com.badlogic.gdx.utils.I18NBundle;

import java.util.Locale;

public class LanguageManager {
    public static final String[] LANGUAGES = {"English", "Polski", "Русский"};

    private final AssetManager assetManager = new AssetManager();
    private final String bundle = "i18n/Blitzkrieg";

    public I18NBundle loadBundle(String language) {
	return switch (language) {
	    case "Polski" -> load("pl");
	    case "Русский" -> load("ru");
	    default -> load("en");
	};
    }

    private I18NBundle load(String localeCode) {
	// This method can be called multiple times to change the language, so we need to clear the old assets
	if (assetManager.contains(bundle)) {
	    assetManager.unload(bundle);
	}

	assetManager.load(assetDescriptorForLocaleCode(localeCode));
	assetManager.finishLoading();
	return assetManager.get(bundle, I18NBundle.class);
    }

    private AssetDescriptor<I18NBundle> assetDescriptorForLocaleCode(String localeCode) {
	var locale = new Locale.Builder().setLanguage(localeCode).build();
	I18NBundleParameter params = new I18NBundleLoader.I18NBundleParameter(locale, "UTF-8");
	return new AssetDescriptor<>(bundle, I18NBundle.class, params);
    }
}
