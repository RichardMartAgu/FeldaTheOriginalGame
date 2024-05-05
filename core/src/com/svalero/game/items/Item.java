package com.svalero.game.items;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.utils.Disposable;
import com.svalero.game.characters.Character;
import com.svalero.game.managers.SpriteManager;
import com.svalero.game.utils.Constants;


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
