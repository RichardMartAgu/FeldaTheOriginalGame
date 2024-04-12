package com.svalero.game.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.svalero.game.Felda;
import com.svalero.game.managers.RenderManager;
import com.svalero.game.managers.ResourceManager;
import com.svalero.game.managers.SpriteManager;

/**
 * Pantalla de Juego, donde el usuario juega la partida
 * @author Santiago Faci
 * @version curso 2014-2015
 */
public class GameScreen implements Screen {

	SpriteManager spriteManager;
	RenderManager renderManager;
	
	@Override
	public void show() {
		ResourceManager.loadAllResources();
		// FIXME La carga debería hacerse en un SplashScreen antes de llegar a ésta
		while (!ResourceManager.update()) {}

		spriteManager = new SpriteManager();
		renderManager = new RenderManager(spriteManager);
	}
	
	@Override
	public void render(float dt) {

		spriteManager.update(dt);
		renderManager.draw();
	}
	
	@Override
	public void hide() {
		
	}
	
	@Override
	public void dispose() {
	}

	@Override
	public void resize(int width, int height) {
	}

	@Override
	public void pause() {
	}

	@Override
	public void resume() {
	}
}
