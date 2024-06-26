package com.svalero.game.managers;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.svalero.game.utils.Constants;

public class ConfigurationManager {

    private static Preferences settings = Gdx.app.getPreferences(Constants.APP_NAME);

    public static boolean isSoundEnabled() {
        return settings.getBoolean("sound");
    }

    public static void switchMusic(boolean value) {
        ConfigurationManager.setSoundEnabled(value);
        if (SpriteManager.music != null) {
            if (value) {
                // Iniciar la música
                SpriteManager.music.play();
            } else {
                // Detener la música
                SpriteManager.music.stop();
            }
        }
    }

    public static void setSoundEnabled(boolean value) {
        Preferences preferences = Gdx.app.getPreferences(Constants.APP_NAME);
        preferences.putBoolean("sound", value);
        preferences.flush();
    }

    public static String getDifficulty() {
        return settings.getString("difficulty", "Medium");
    }

    public static void setDifficulty(String value) {
        Preferences preferences = Gdx.app.getPreferences(Constants.APP_NAME);
        settings.putString("difficulty", value);
        preferences.flush();
    }

}
