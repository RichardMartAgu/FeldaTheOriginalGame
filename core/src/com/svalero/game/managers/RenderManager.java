package com.svalero.game.managers;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.Disposable;
import com.badlogic.gdx.utils.ScreenUtils;

public class RenderManager implements Disposable {

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

    private void drawPlayer() {
        spriteManager.player.draw(batch);
    }
    public void drawFrame() {
        ScreenUtils.clear(1, 0, 0, 1);
        // Inicia renderizado del juego
        batch.begin();
        // Pinta el HUD
        drawHud();
        // Pinta al jugador
        spriteManager.player.render(batch);
        batch.end();

    }

    private void drawHud() {

    }


    @Override
    public void dispose() {
        batch.dispose();
        font.dispose();
    }
}
