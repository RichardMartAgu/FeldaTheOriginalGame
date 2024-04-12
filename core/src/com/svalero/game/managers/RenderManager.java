package com.svalero.game.managers;

import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.Disposable;
import com.badlogic.gdx.utils.ScreenUtils;

public class RenderManager implements Disposable {

    SpriteBatch batch;
    BitmapFont font;
    SpriteManager spriteManager;

    public RenderManager(SpriteManager spriteManager) {
        this.spriteManager = spriteManager;
        initialize();
    }

    private void initialize() {
        batch = new SpriteBatch();
        font = new BitmapFont();
    }

    private void drawPlayer() {
        spriteManager.player.draw(batch);
    }

    private void drawHud() {

    }

    public void draw() {
        ScreenUtils.clear(1, 0, 0, 1);

        batch.begin();
        drawPlayer();
        drawHud();
        batch.end();
    }

    @Override
    public void dispose() {
        batch.dispose();
        font.dispose();
    }
}
