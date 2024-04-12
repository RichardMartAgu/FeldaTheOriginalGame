package com.svalero.game.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.svalero.game.Felda;
import com.svalero.game.managers.CameraManager;
import com.svalero.game.managers.RenderManager;
import com.svalero.game.managers.ResourceManager;
import com.svalero.game.managers.SpriteManager;

public class GameScreen implements Screen {

    Felda game;
    SpriteManager spriteManager;
    RenderManager renderManager;
    ResourceManager resourceManager;
    LevelManager levelManager;
    private OrthogonalTiledMapRenderer mapRenderer;
    private CameraManager cameraManager;
    private TiledMap tiledMap;

    public GameScreen(Felda game) {
        this.game = game;


        spriteManager = new SpriteManager(game);
        levelManager = new LevelManager(spriteManager);
        levelManager.loadCurrentLevel();

        spriteManager.setLevelManager(levelManager);
        cameraManager = new CameraManager(spriteManager, levelManager);
        levelManager.setCameraManager(cameraManager);
        spriteManager.setCameraManager(cameraManager);
        renderManager = new RenderManager(spriteManager, cameraManager, levelManager.batch);
    }

    @Override
    public void show() {

    }


    @Override
    public void render(float dt) {
        // Pinta el fondo de la pantalla de azul oscuro (RGB + alpha)
        Gdx.gl.glClearColor(0, 0, 0.2f, 1);
        // Limpia la pantalla
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        cameraManager.handleCamera();
        spriteManager.update(dt);
        renderManager.drawFrame();

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
