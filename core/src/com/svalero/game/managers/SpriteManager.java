package com.svalero.game.managers;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;
import com.svalero.game.Felda;
import com.svalero.game.characters.Character;
import com.svalero.game.characters.Enemy;
import com.svalero.game.characters.Player;
import com.svalero.game.characters.Sword;
import com.svalero.game.screen.GameOverScreen;
import com.svalero.game.screen.MainMenuScreen;
import com.svalero.game.utils.MyContactListener;

public class SpriteManager implements InputProcessor {
    World world;
    Felda game;
    LevelManager levelManager;
    CameraManager cameraManager;
    public Player player;
    public Sword sword;

    private MyContactListener myContactListener;

    Array<Enemy> enemies;
    Array<Body> worldBodies;
    TiledMapTileLayer collisionLayer;
    boolean pause;

    public SpriteManager(Felda game, World world,MyContactListener contactListener) {

        this.game = game;
        this.world = world;
        this.myContactListener = contactListener;
        enemies = new Array<>();
        Gdx.input.setInputProcessor(this);

        worldBodies = new Array<>();
    }

    public void setCameraManager(CameraManager cameraManager) {
        this.cameraManager = cameraManager;
    }

    private void handleGameScreenInput() {
        if (Gdx.input.isKeyPressed(Input.Keys.ESCAPE)) {
            ((Game) Gdx.app.getApplicationListener()).setScreen(new MainMenuScreen(game));
        }

        if (Gdx.input.isKeyJustPressed(Input.Keys.P)) {
            pause = !pause;
        }
    }

    public void setLevelManager(LevelManager levelManager) {
        this.levelManager = levelManager;
    }

    public void update(float dt) {
        if (!pause) {
            player.manageInput(dt);

            if (player.liveState != Character.LiveState.DYING) {
                world.step(dt, 8, 6);
            } else {
                world.step(0, 0, 0); // Detiene el mundo físico
            }

            world.getBodies(worldBodies);

            for (Body body : worldBodies) {
                if (world.isLocked()) continue;

                if (body.getUserData() instanceof Enemy) {
                    Enemy enemy = (Enemy) body.getUserData();
                    if (enemy.liveState == Enemy.LiveState.DEAD) {
                        worldBodies.removeValue(enemy.getBody(), true);
                        world.destroyBody(body);
                        enemies.removeValue(enemy, true);
                    }
                }
            }

            if (player.liveState == Player.LiveState.DEAD) {
                game.setScreen(new GameOverScreen(game));
            }

            if(player.liveState == Character.LiveState.HIT){
                world.setContactListener(null);
            }else{
                world.setContactListener(myContactListener);
            }



            player.update(dt, this);

            for (Enemy enemy : enemies) {
                enemy.update(dt, this);
            }

            attackEnemies();

        }
        handleGameScreenInput();
    }

    public void attackEnemies() {
    }

    @Override
    public boolean keyDown(int keycode) {
        return false;
    }

    @Override
    public boolean keyUp(int keycode) {
        return false;
    }

    @Override
    public boolean keyTyped(char character) {
        return false;
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        return false;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        return false;
    }

    @Override
    public boolean touchCancelled(int screenX, int screenY, int pointer, int button) {
        return false;
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        return false;
    }

    @Override
    public boolean mouseMoved(int screenX, int screenY) {
        return false;
    }

    @Override
    public boolean scrolled(float amountX, float amountY) {
        return false;
    }

    public void setCollisionLayer(TiledMapTileLayer collisionLayer) {
        this.collisionLayer = collisionLayer;
    }
}
