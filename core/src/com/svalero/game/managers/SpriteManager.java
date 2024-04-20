package com.svalero.game.managers;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;
import com.svalero.game.Felda;
import com.svalero.game.characters.Character;
import com.svalero.game.characters.Enemy;
import com.svalero.game.characters.Player;
import com.svalero.game.characters.Sword;
import com.svalero.game.items.Heart;
import com.svalero.game.items.Item;
import com.svalero.game.items.Rupia;
import com.svalero.game.screen.GameOverScreen;
import com.svalero.game.screen.MainMenuScreen;
import com.svalero.game.utils.Constants;
import com.svalero.game.utils.MyContactListener;

public class SpriteManager implements InputProcessor {
    World world;
    Felda game;
    LevelManager levelManager;
    CameraManager cameraManager;
    public Player player;
    public Rupia rupia;
    public Sword sword;
    Music music;
    private MyContactListener myContactListener;
    Array<Item> items;
    Array<Enemy> enemies;
    Array<Body> worldBodies;
    boolean pause;

    public SpriteManager(Felda game, World world, MyContactListener contactListener) {

        this.game = game;
        this.world = world;
        this.myContactListener = contactListener;
        enemies = new Array<>();
        items = new Array<>();
        Gdx.input.setInputProcessor(this);

        worldBodies = new Array<>();
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
                music.stop();
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
                        int random = MathUtils.random(1, 2);
                        if (random == 1) {
                            // Agrega una rupia en la posición del enemigo
                            Rupia rupia = new Rupia(new Vector2(enemy.position.x, enemy.position.y), 1, world);
                            items.add(rupia);

                        } else if (random == 2) {
                            // Agrega un corazón en la posición del enemigo
                            Heart heart = new Heart(new Vector2(enemy.position.x, enemy.position.y), 1, world);
                            items.add(heart);
                        }
                        enemies.removeValue(enemy, true);
                    }
                }

                if (body.getUserData() instanceof Item) {
                    Item item = (Item) body.getUserData();
                    if (item.state == Item.State.COLLECTED) {
                        worldBodies.removeValue(item.getBody(), true);
                        world.destroyBody(body);
                        items.removeValue(item, true);

                    }
                }
            }

            if (player.liveState == Player.LiveState.DEAD) {
             ResourceManager.getSound(Constants.SOUND + "lose.mp3").play();

                game.setScreen(new GameOverScreen(game));
            }

            if (player.liveState == Character.LiveState.HIT) {
                world.setContactListener(null);
            } else {
                world.setContactListener(myContactListener);
            }

            player.update(dt, this);

            for (Enemy enemy : enemies) {
                enemy.update(dt, this);
            }
            for (Item items : items) {
                items.update(dt, this);
            }
        }
        handleGameScreenInput();
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

}
