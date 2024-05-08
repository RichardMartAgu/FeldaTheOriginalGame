package com.svalero.game.items;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;


public abstract class Item {

    public Vector2 position;
    TextureRegion currentFrame;
    public Rectangle rect;

    public int score;

    public Item(float x, float y) {

        position = new Vector2(x, y);
        rect = new Rectangle();
    }

    public void render(Batch batch) {
        batch.draw(currentFrame, position.x, position.y);
    }

    public abstract void update(float dt);
}
