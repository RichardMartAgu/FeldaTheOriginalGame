package com.svalero.game.items;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.svalero.game.characters.Character;
import com.svalero.game.managers.SpriteManager;
import com.svalero.game.utils.Constants;


public abstract class Item {
    public enum State {
        NORMAL,COLLECTED;
    }

    public Vector2 position;
    TextureRegion currentFrame;
    public State state = State.NORMAL;

    public int score;
    public Body body;
    public float stateTime;

    public Item(Vector2 position, World world) {
        this.position = position;

        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.StaticBody;
        bodyDef.position.set(position.x,position.y);
        body = world.createBody(bodyDef);
        body.setUserData(this);

        PolygonShape shape = new PolygonShape();
        shape.setAsBox(Constants.PLAYER_WIDTH / 2f, Constants.PLAYER_HEIGHT / 2f);

        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = shape;
        fixtureDef.density = 0f;
        fixtureDef.friction = 0f;
        fixtureDef.restitution = 0f;

        body.createFixture(fixtureDef);
        shape.dispose();
    }


    public void render(Batch batch) {
        if (currentFrame != null)
            batch.draw(currentFrame, position.x, position.y);

    }
    public void collected(){
        System.out.println("Cogido");
        state = State.COLLECTED;
    }

    public void update(float dt, SpriteManager spriteManager) {
        stateTime += dt;
    }
    public Body getBody() {
        return this.body;
    }
    public Vector2 getPosition() {
        return position;
    }
}
