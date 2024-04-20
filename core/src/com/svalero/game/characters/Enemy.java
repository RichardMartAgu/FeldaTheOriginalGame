package com.svalero.game.characters;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.svalero.game.managers.SpriteManager;
import com.svalero.game.utils.Constants;

public class Enemy extends Character {


    public Vector2 attackOrigin;

    public enum EnemyType {
        green, gray, blue
    }

    public Body body;

    protected EnemyType type;
    Animation<TextureRegion> rightAnimation, leftAnimation, idleAnimation, dieAnimation;

    public Enemy(Vector2 position, int hearts, World world) {
        super(position);

        currentHearts = hearts;

        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.DynamicBody; // Cuerpo dinámico
        bodyDef.position.set(position.x, position.y);
        body = world.createBody(bodyDef);

        PolygonShape shape = new PolygonShape();
        shape.setAsBox(Constants.PLAYER_WIDTH / 2f, Constants.PLAYER_HEIGHT / 2f);

        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = shape;
        fixtureDef.density = 1f;
        fixtureDef.friction = 1f;
        fixtureDef.restitution = 0.2f;

        body.createFixture(fixtureDef);
        shape.dispose();

        liveState = LiveState.NORMAL;
    }

    @Override
    public void update(float dt, SpriteManager spriteManager) {
    }

    public void hit(int damage, Vector2 attackOrigin) {
        if (liveState == LiveState.NORMAL) {
            currentHearts -= damage;

            if (currentHearts <= 0) {
                body.setLinearVelocity(1, 1);
                liveState = LiveState.DYING;
                stateTime = 0;
            } else {
                this.attackOrigin = attackOrigin;
                liveState = LiveState.HIT;
            }
        }
    }

    public Body getBody() {
        return this.body;
    }
}
