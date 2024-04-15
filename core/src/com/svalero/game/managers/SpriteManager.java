package com.svalero.game.managers;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.Array;
import com.svalero.game.Felda;
import com.svalero.game.characters.Enemy;
import com.svalero.game.characters.GreenEnemy;
import com.svalero.game.characters.Player;
import com.svalero.game.items.CollisionObject;
import com.svalero.game.screen.MainMenuScreen;
import com.svalero.game.utils.Constants;

import java.util.List;

public class SpriteManager implements InputProcessor {

    Felda game;
    LevelManager levelManager;
    CameraManager cameraManager;
    public Player player;
    Array<Enemy> enemies;

    TiledMapTileLayer collisionLayer;
    boolean pause;

    public SpriteManager(Felda game) {

        this.game = game;
        enemies = new Array<>();
        Gdx.input.setInputProcessor(this);
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
        }

        handleGameScreenInput();

        player.update(dt, this);

        for (Enemy enemy : enemies) {
            enemy.update(dt,this);
        }
        attackEnemies();

        checkCollision();
    }
    public void attackEnemies() {
        // Verificar si el jugador está atacando

        if (player.isAttackInProgress) {
            // Obtener la hitbox de la espada del jugador
            Rectangle swordRectangle = player.getSwordRectangle();

            // Lógica para detectar colisiones entre la espada del jugador y los enemigos
            for (Enemy enemy : enemies) {

                if (enemy.hitBox.overlaps(swordRectangle)) {
                    enemy.reduceLife(1); // Restar un corazón al enemigo golpeado
                    System.out.println(enemy.hearts);
                }
            }
        }
    }
    public void removeEnemy(Enemy enemy) {
        enemies.removeValue(enemy, true);
    }


    private void checkCollision() {
        float playerX = player.getPosition().x;
        float playerY = player.getPosition().y;

        // Obtener el tamaño del jugador (ancho y alto)
        float playerWidth = Constants.PLAYER_WIDTH;
        float playerHeight = Constants.PLAYER_HEIGHT;

        // Calcular las posiciones de las esquinas del jugador
        float leftX = playerX;
        float rightX = playerX + playerWidth;
        float topY = playerY + playerHeight;
        float bottomY = playerY;

        List<CollisionObject> collisionObjects = levelManager.getCollisionObjects();

        boolean isCollidingLeft = false;
        boolean isCollidingRight = false;
        boolean isCollidingUp = false;
        boolean isCollidingDown = false;

        for (CollisionObject object : collisionObjects) {
            Rectangle objectBounds = object.getBounds();
            if (objectBounds.contains(leftX, playerY + playerHeight / 2)) {
                isCollidingLeft = true;
            }
            if (objectBounds.contains(rightX, playerY + playerHeight / 2)) {
                isCollidingRight = true;
            }
            if (objectBounds.contains(playerX + playerWidth / 2, topY)) {
                isCollidingUp = true;
            }
            if (objectBounds.contains(playerX + playerWidth / 2, bottomY)) {
                isCollidingDown = true;
            }
        }

        // Actualizar el estado de colisión del jugador
        player.setCollidingLeft(isCollidingLeft);
        player.setCollidingRight(isCollidingRight);
        player.setCollidingUp(isCollidingUp);
        player.setCollidingDown(isCollidingDown);
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
