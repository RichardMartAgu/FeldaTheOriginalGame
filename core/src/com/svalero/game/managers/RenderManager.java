package com.svalero.game.managers;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.ScreenUtils;

import static com.svalero.game.utils.Constants.*;

public class RenderManager {

    Batch batch;
    BitmapFont font;
    SpriteManager spriteManager;
    CameraManager cameraManager;

    public RenderManager(SpriteManager spriteManager, CameraManager cameraManager, Batch batch) {
        this.spriteManager = spriteManager;
        this.cameraManager = cameraManager;
        this.batch = batch;
        font = new BitmapFont();
    }

    public void drawFrame() {
        // Inicia renderizado del juego
        batch.begin();
        // Pinta el HUD
        drawHud();
        // Pinta al jugador
        spriteManager.player.render(batch);
        batch.end();
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

}
