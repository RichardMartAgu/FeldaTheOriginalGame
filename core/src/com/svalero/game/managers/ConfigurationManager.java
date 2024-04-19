package com.svalero.game.managers;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.svalero.game.utils.Constants;

public class ConfigurationManager {

    private static Preferences settings = Gdx.app.getPreferences(Constants.APP_NAME);;

    public static boolean isSoundEnabled() {
        return settings.getBoolean("sound",true);
    }
}
