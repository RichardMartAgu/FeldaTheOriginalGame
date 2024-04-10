package com.svalero.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.svalero.game.screen.MainMenuScreen;


public class Felda extends Game {
	public boolean paused;

	
	@Override
	public void create () {
		((Game) Gdx.app.getApplicationListener()).setScreen(new MainMenuScreen());
	}


	@Override
	public void render () {
		super.render();
	}
	
	@Override
	public void dispose () {

	}
}
