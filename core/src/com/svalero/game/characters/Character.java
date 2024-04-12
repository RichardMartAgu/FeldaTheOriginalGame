package com.svalero.game.characters;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Disposable;
import com.svalero.game.managers.ResourceManager;

public class Character implements Disposable {

    public enum State {
        RIGHT, LEFT, UP, DOWN, IDLE;
    }

    public Vector2 position;
    public Rectangle rect;

    private Animation<TextureRegion> animation;
    private float stateTime;
    private TextureRegion currentFrame;

    public Character(Vector2 position, String animationName) {
        this.position = position;

        animation = new Animation<>(0.15f, ResourceManager.getAnimation(animationName));
        currentFrame = animation.getKeyFrame(0);

        rect = new Rectangle(position.x, position.y, currentFrame.getRegionWidth(), currentFrame.getRegionHeight());
    }

    public void draw(SpriteBatch batch) {
        stateTime += Gdx.graphics.getDeltaTime();

        currentFrame = animation.getKeyFrame(stateTime, true);
        batch.draw(currentFrame, position.x, position.y);
    }
    public void move(float x, float y) {
        position.add(x, y);
        rect.x += x;
        rect.y += y;
    }
    @Override
    public void dispose() {

    }
}
