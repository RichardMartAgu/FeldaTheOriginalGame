package com.svalero.game.characters;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Disposable;
import com.svalero.game.managers.ResourceManager;
import com.svalero.game.managers.SpriteManager;

public class Character implements Disposable {

    public enum State {
        RIGHT, LEFT, UP, DOWN, IDLE, DEAD;
    }

    public int hearts;
    public int currentHearts;
    public State state;
    public Vector2 position;
    public Rectangle rect;
    protected boolean dead;

    private Animation<TextureRegion> animation;
    private float stateTime;
    public TextureRegion currentFrame;

    public Character(Vector2 position, int hearts, String animationName) {
        this.position = position;

        animation = new Animation<>(0.15f, ResourceManager.getRegions(animationName));
        currentFrame = animation.getKeyFrame(0);

        rect = new Rectangle(position.x, position.y, currentFrame.getRegionWidth(), currentFrame.getRegionHeight());
    }

    public void render(Batch batch) {
        if (currentFrame != null)
            batch.draw(currentFrame, position.x, position.y);
    }

    public void draw(Batch batch) {
        stateTime += Gdx.graphics.getDeltaTime();

        currentFrame = animation.getKeyFrame(stateTime, true);
        batch.draw(currentFrame, position.x, position.y);
    }

    public void move(float x, float y) {
        position.add(x, y);
        rect.x += x;
        rect.y += y;
    }

    public boolean isDead() {
        return dead;
    }

    @Override
    public void dispose() {

    }

    public void update(float dt, SpriteManager spriteManager) {

    }

    public void checkCollisions(SpriteManager spriteManager) {


    }
}
