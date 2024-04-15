package com.svalero.game.managers;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.maps.MapLayer;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.math.Rectangle;
import com.svalero.game.characters.Enemy;

import static com.svalero.game.utils.Constants.*;

public class RenderManager {
    private ShapeRenderer shapeRenderer;
    Batch batch;
    BitmapFont font;
    SpriteManager spriteManager;
    CameraManager cameraManager;
    LevelManager levelManager;

    public RenderManager(SpriteManager spriteManager, CameraManager cameraManager,LevelManager levelManager, Batch batch) {
        shapeRenderer = new ShapeRenderer();

        this.spriteManager = spriteManager;
        this.cameraManager = cameraManager;
        this.levelManager = levelManager;
        this.batch = batch;

        font = new BitmapFont();
    }

    public void drawFrame() {
        // Inicia renderizado del juego
        batch.begin();

        //Capas inferiores
        renderLayer("terrain");
        renderLayer("terrain2");

        // Pinta el HUD
        drawHud();

        // Pinta al enemigo
        for (Enemy enemy : spriteManager.enemies)
            enemy.render(batch);

        // Pinta al jugador
        spriteManager.player.render(batch);

        //Capas superiores
        renderLayer("nature");
        renderLayer("ornamentals");

        batch.end();

        // Antes de comenzar el batch
        shapeRenderer.begin(ShapeRenderer.ShapeType.Line);
        shapeRenderer.setColor(Color.RED);

// Dibuja las hitboxes de los enemigos
        for (Enemy enemy : spriteManager.enemies) {
            Rectangle enemyRect = enemy.hitBox;
            shapeRenderer.rect(enemyRect.x, enemyRect.y, enemyRect.width, enemyRect.height);
        }

// Dibuja la hitbox del jugador
        Rectangle swordRect = spriteManager.player.getSwordRectangle();
        shapeRenderer.rect(swordRect.x, swordRect.y, swordRect.width, swordRect.height);

        Rectangle playerRect = spriteManager.player.hitBox;
        shapeRenderer.rect(playerRect.x, playerRect.y, playerRect.width, playerRect.height);
        System.out.println( "position render"+playerRect.x+playerRect.y);

// Finaliza el dibujo de hitboxes
        shapeRenderer.end();



    }

    private void drawHud() {

        int currentLevel = 1; // Obtén el nivel actual del SpriteManager
        String levelText = "Nivel " + currentLevel +": Casa de Felda";
        font.draw(batch, levelText, cameraManager.camera.position.x - CAMERA_WIDTH / 2 + 10 , CAMERA_HEIGHT - PLAYER_HEIGHT + TILE_HEIGHT -10);

        batch.draw(ResourceManager.getRegion("heart"), cameraManager.camera.position.x - CAMERA_WIDTH / 2 + 10, CAMERA_HEIGHT - TILE_HEIGHT -10 );
        font.draw(batch, " x " + spriteManager.player.currentHearts, cameraManager.camera.position.x - CAMERA_WIDTH / 2 + PLAYER_WIDTH + 10, CAMERA_HEIGHT - TILE_HEIGHT / 2 -10 );

        batch.draw(ResourceManager.getRegion("rupia"), cameraManager.camera.position.x - CAMERA_WIDTH / 2 + 10 + TILE_WIDTH * 2, CAMERA_HEIGHT - PLAYER_HEIGHT -13);
        font.draw(batch, " x " + spriteManager.player.currentHearts, cameraManager.camera.position.x - CAMERA_WIDTH / 2 + PLAYER_WIDTH * 4 + 10, CAMERA_HEIGHT - TILE_HEIGHT / 2 - 10);

    }
    private void renderLayer(String layerName) {
        int layerIndex = levelManager.map.getLayers().getIndex(layerName);
        if (layerIndex != -1) {
            MapLayer layer = levelManager.map.getLayers().get(layerIndex);
            if (layer instanceof TiledMapTileLayer) {
                levelManager.mapRenderer.renderTileLayer((TiledMapTileLayer) layer);
            }
        }
    }

}
