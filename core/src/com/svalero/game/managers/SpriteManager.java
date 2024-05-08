package com.svalero.game.managers;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;
import com.svalero.game.Felda;
import com.svalero.game.characters.Character;
import com.svalero.game.characters.*;
import com.svalero.game.items.Goal;
import com.svalero.game.items.Heart;
import com.svalero.game.items.Item;
import com.svalero.game.items.Rupia;
import com.svalero.game.screen.GameOverScreen;
import com.svalero.game.screen.GameScreen;
import com.svalero.game.screen.PauseGameScreen;
import com.svalero.game.utils.Constants;
import com.svalero.game.utils.MyContactListener;

import java.util.Iterator;

public class SpriteManager implements InputProcessor {
    World world;
    Felda game;
    LevelManager levelManager;

    public Player player;
    public Sword sword;
    public static Music music;
    private MyContactListener myContactListener;
    private GameScreen gameScreen;

    Array<Item> items;
    Array<Enemy> enemies;
    Array<Body> worldBodies;
    boolean pause;


    public SpriteManager(Felda game, World world, MyContactListener contactListener, GameScreen gameScreen) {

        this.game = game;
        this.world = world;
        this.myContactListener = contactListener;
        this.gameScreen = gameScreen;
        enemies = new Array<>();
        items = new Array<>();
        Gdx.input.setInputProcessor(this);

        worldBodies = new Array<>();
    }


    private void handleGameScreenInput() {
        //Cuando apretamos scape vamos al modo pausa
        if (Gdx.input.isKeyJustPressed(Input.Keys.ESCAPE)) {
            pause = !pause;
            game.setScreen(new PauseGameScreen(game, gameScreen, this));
        }
    }



    public void update(float dt) {
        // Se actualiza el juego solo si no está en pausa
        if (!pause) {
            // Se maneja la entrada del jugador
            player.manageInput(dt);

            // Se actualiza el mundo físico del juego
            if (player.liveState != Character.LiveState.DYING) {
                world.step(1 / 60f, 6, 2);
            } else {
                // Si el jugador está muriendo, se detiene el mundo físico
                music.stop();
                world.step(0, 0, 0); // Detiene el mundo físico
            }

            // Se obtienen los cuerpos del mundo físico
            world.getBodies(worldBodies);

            for (Body body : worldBodies) {
                if (world.isLocked()) continue;

                if (body.getUserData() instanceof Enemy) {
                    Enemy enemy = (Enemy) body.getUserData();
                    if (enemy.liveState == Enemy.LiveState.DEAD) {
                        worldBodies.removeValue(enemy.getBody(), true);
                        world.destroyBody(body);

                        // Se elige aleatoriamente entre agregar una rupia o un corazón en la posición del enemigo muerto
                        int random = MathUtils.random(1, 2);
                        if (random == 1) {
                            // Agrega una rupia en la posición del enemigo
                            Rupia rupia = new Rupia(enemy.position.x, enemy.position.y, 1);
                            items.add(rupia);

                        } else if (random == 2) {
                            // Agrega un corazón en la posición del enemigo
                            Heart heart = new Heart(enemy.position.x, enemy.position.y, 1);
                            items.add(heart);
                        }
                        enemies.removeValue(enemy, true);
                    }
                }

            }

            // Si el jugador está muerto, se reproduce el sonido de perder y se muestra la pantalla de Game Over
            if (player.liveState == Player.LiveState.DEAD) {
                ResourceManager.getSound(Constants.SOUND + "lose.mp3").play();
                game.setScreen(new GameOverScreen(game));
            }

            // Si el jugador está golpeado, se desactiva el listener de colisiones
            if (player.liveState == Character.LiveState.HIT) {
                world.setContactListener(null);
            } else {
                // Si no, se activa el listener de colisiones
                world.setContactListener(myContactListener);
            }

            player.update(dt, this);

            for (Enemy enemy : enemies) {
                if (enemy instanceof BlueEnemy) {
                    // Si el enemigo es de tipo BlueEnemy y está disparando, se crea un proyectil
                    if (((BlueEnemy) enemy).shoot) {
                        BlueProjectile blueProjectile = new BlueProjectile(new Vector2(enemy.position.x, enemy.position.y), 1, player, world);
                        enemies.add(blueProjectile);
                    }
                }
                // Se actualiza el enemigo
                enemy.update(dt, this);
            }

        }
        // Se maneja el metodo para entrar en pause
        handleGameScreenInput();
        // Se actualizan los ítems y los enemigos
        updateItems();
        updateEnemy();

    }

    private void updateItems() {

        Item item;
        Iterator<Item> iterItems = items.iterator();
        while (iterItems.hasNext()) {
            item = iterItems.next();
            if (item instanceof Heart) {
                if (item.rect.overlaps(player.rect)) {
                    // Se agrega un corazón al jugador
                    player.addHeart();
                    // Se elimina el corazón de la lista de ítems
                    iterItems.remove();
                    // Si el sonido está habilitado, se reproduce el sonido de recolección de corazón
                    if (ConfigurationManager.isSoundEnabled()) {
                        ResourceManager.getSound(Constants.SOUND + "collect_heart.mp3").play();

                    }
                }
            } else if (item instanceof Rupia) {
                if (item.rect.overlaps(player.rect)) {
                    // Se agrega la cantidad de rupias al jugador
                    player.addRupia(item.score);
                    // Se elimina la rupia de la lista de ítems
                    iterItems.remove();
                    // Si el sonido está habilitado, se reproduce el sonido de recolección de rupia
                    if (ConfigurationManager.isSoundEnabled()) {
                        ResourceManager.getSound(Constants.SOUND + "collect_rupia.mp3").play();

                    }
                }
            } else if (item instanceof Goal) {
                // Si el jugador colisiona con la meta
                if (item.rect.overlaps(player.rect)) {
                    // Se carga el siguiente nivel
                    levelManager.nextLevel();
                    // Se reproduce el sonido de pasar al siguiente nivel
                    ResourceManager.getSound(Constants.SOUND + "next_level.mp3").play();

                }
            }
        }
    }

    private void updateEnemy() {

        Enemy enemy;
        Iterator<Enemy> iterEnemies = enemies.iterator();
        while (iterEnemies.hasNext()) {
            enemy = iterEnemies.next();

            if (enemy.rect.overlaps(sword.rect)) {
                if (ConfigurationManager.isSoundEnabled()) {
                    // Si el sonido está habilitado, se reproduce el sonido de impacto
                    ResourceManager.getSound(Constants.SOUND + "hurt_bubble.mp3").play();
                    // Se aplica un golpe al enemigo
                    enemy.hit(1, (new Vector2(sword.rect.x, sword.rect.y)));
                    // Se reposiciona la espada
                    sword.rect.setPosition(0, 0);
                }
            }
        }
    }

    public void setLevelManager(LevelManager levelManager) {
        this.levelManager = levelManager;
    }

    public void quitPause() {
        pause = !pause;
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
