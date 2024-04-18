package com.svalero.game.characters;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Disposable;
import com.svalero.game.managers.SpriteManager;

public abstract class Character implements Disposable {


    public enum State {
        RIGHT, LEFT, UP, DOWN, IDLE;
    }

    public enum LiveState{
        DEAD,DYING,NORMAL, HIT;
    }

    public int currentHearts;
    public Vector2 position;
    public LiveState liveState = LiveState.NORMAL;
    public float stateTime;
    public TextureRegion currentFrame;

    public Character(Vector2 position) {
        this.position = position;
    }

    public void render(Batch batch) {
        if (currentFrame != null)
            batch.draw(currentFrame, position.x, position.y);
    }
    @Override
    public void dispose() {

    }

    public void update(float dt, SpriteManager spriteManager) {
        stateTime += dt;
    }
    public Vector2 getPosition() {
        return position;
    }
}
