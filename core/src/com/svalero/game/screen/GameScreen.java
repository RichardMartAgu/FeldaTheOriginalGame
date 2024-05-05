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

    public static Felda game;
    SpriteManager spriteManager;
    RenderManager renderManager;
    LevelManager levelManager;
    Box2DDebugRenderer debugRenderer;
    OrthographicCamera camera = new OrthographicCamera();
    private MyContactListener myContactListener;


    private CameraManager cameraManager;
    World world;

    public GameScreen(Felda game) {
        this.game = game;

        world = new World(new Vector2(0, 0), true);
        myContactListener = new MyContactListener();
        world.setContactListener(myContactListener);
        debugRenderer = new Box2DDebugRenderer();
        spriteManager = new SpriteManager(game, world, myContactListener,this);
        levelManager = new LevelManager(spriteManager, world);
        levelManager.loadCurrentLevel();
        setLevelManagerForSpriteManager(levelManager);
        cameraManager = new CameraManager(spriteManager, levelManager);
        renderManager = new RenderManager(spriteManager, cameraManager, levelManager, levelManager.batch);
        levelManager.setCameraManager(cameraManager);

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
    public void setLevelManagerForSpriteManager(LevelManager levelManager) {
        spriteManager.setLevelManager(levelManager);
    }

}
