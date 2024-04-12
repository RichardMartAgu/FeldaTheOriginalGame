package com.svalero.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.svalero.game.screen.SplashScreen;


public class Felda extends Game {
    public boolean paused;


    @Override
    public void create() {
        ((Game) Gdx.app.getApplicationListener()).setScreen(new SplashScreen(this));
    }


    @Override
    public void render() {
        super.render();
    }

    @Override
    public void dispose() {
        super.dispose();
    }
}
