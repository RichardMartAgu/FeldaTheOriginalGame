package com.svalero.game.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.World;
import com.svalero.game.Felda;
import com.svalero.game.managers.CameraManager;
import com.svalero.game.managers.LevelManager;
import com.svalero.game.managers.RenderManager;
import com.svalero.game.managers.SpriteManager;
import com.svalero.game.utils.MyContactListener;

public class GameScreen implements Screen {

    Felda game;
    SpriteManager spriteManager;
    RenderManager renderManager;
    LevelManager levelManager;
    Box2DDebugRenderer debugRenderer;
    OrthographicCamera camera = new OrthographicCamera();

    private CameraManager cameraManager;
    World world;

    public GameScreen(Felda game) {
        this.game = game;

        world = new World(new Vector2(0, 0), true);
        world.setContactListener(new MyContactListener());
        debugRenderer = new Box2DDebugRenderer();
        spriteManager = new SpriteManager(game, world);
        levelManager = new LevelManager(spriteManager, world);
        levelManager.loadCurrentLevel();
        levelManager.loadCollisionLayer();

        cameraManager = new CameraManager(spriteManager, levelManager);
        renderManager = new RenderManager(spriteManager, cameraManager, levelManager, levelManager.batch);

        camera.setToOrtho(false, Gdx.graphics.getWidth() / 200f, Gdx.graphics.getHeight() / 200f);
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

        camera.update();

        debugRenderer.render(world, camera.combined);
        debugRenderer.render(world, cameraManager.getCamera().combined);

        cameraManager.handleCamera();
        spriteManager.update(dt);
        renderManager.drawFrame();
    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {
        world.dispose();
        debugRenderer.dispose();
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
