package com.svalero.game.managers;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.maps.MapLayer;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.svalero.game.characters.Enemy;
import com.svalero.game.items.Item;

import static com.svalero.game.utils.Constants.*;

public class RenderManager {
    private ShapeRenderer shapeRenderer;
    Batch batch;
    BitmapFont font;
    SpriteManager spriteManager;
    CameraManager cameraManager;
    LevelManager levelManager;

    public RenderManager(SpriteManager spriteManager, CameraManager cameraManager, LevelManager levelManager, Batch batch) {
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

        // Pinta al enemigo
        for (Enemy enemy : spriteManager.enemies)
            enemy.render(batch);

        for (Item item : spriteManager.items)
            item.render(batch);

        // Pinta al jugador
        spriteManager.player.render(batch);

        //Capas superiores
        renderLayer("nature");
        renderLayer("ornamentals");

        // Pinta el HUD
        drawHud();
        batch.setProjectionMatrix(cameraManager.camera.combined);
        batch.end();

    }

    private void drawHud() {

        int currentLevel = 1; // Obtén el nivel actual del SpriteManager
        String levelText = "Nivel " + currentLevel + ": Casa de Felda";
        font.draw(batch, levelText, cameraManager.camera.position.x - CAMERA_WIDTH / 2f +10 , CAMERA_HEIGHT -10 );

        batch.draw(ResourceManager.getRegion("heart"), cameraManager.camera.position.x - CAMERA_WIDTH / 2f +10 , CAMERA_HEIGHT -47);
        font.draw(batch, " x " + spriteManager.player.currentHearts, cameraManager.camera.position.x - CAMERA_WIDTH / 2f +22, CAMERA_HEIGHT-35);

        batch.draw(ResourceManager.getRegion("rupia"), cameraManager.camera.position.x - CAMERA_WIDTH / 2f +54 , CAMERA_HEIGHT-47);
        font.draw(batch, " x " + spriteManager.player.rupias, cameraManager.camera.position.x - CAMERA_WIDTH / 2f +66 , CAMERA_HEIGHT-35);

    }

    private void renderLayer(String layerName) {
        int layerIndex = levelManager.map.getLayers().getIndex(layerName);
        if (layerIndex != -1) {
            MapLayer layer = levelManager.map.getLayers().get(layerIndex);
            if (layer instanceof TiledMapTileLayer) {

            }
        }
    }

}
