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

public abstract class Character implements Disposable {

    public abstract void die(SpriteManager spriteManager);

    public enum State {
        RIGHT, LEFT, UP, DOWN, IDLE, DEAD;
    }

    public int hearts;
    public int currentHearts;
    public State state;
    public Vector2 position;
    public Rectangle hitBox;
    protected boolean dead = false;

    private Animation<TextureRegion> animation;
    public float stateTime;
    public TextureRegion currentFrame;

    public Character(Vector2 position, int hearts) {
        this.position = position;

        hitBox = new Rectangle();
    }

    public void render(Batch batch) {
        if (currentFrame != null)
            batch.draw(currentFrame, position.x, position.y);
    }


    public void move(float x, float y,float dt) {
        position.add(x * dt, y * dt);
        System.out.println( "posicion" +position.x + position.y);

        hitBox.setPosition(position.x, position.y);
        System.out.println( "posicion" +hitBox.x + hitBox.y);
    }



    @Override
    public void dispose() {

    }

    public void update(float dt, SpriteManager spriteManager) {

    }

    public void checkCollisions(SpriteManager spriteManager) {


    }
    public void die(){

    }
    public Vector2 getPosition() {
        return position;
    }
}
