package com.svalero.game.characters;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.svalero.game.managers.ConfigurationManager;
import com.svalero.game.managers.SpriteManager;
import com.svalero.game.utils.Constants;

public class Enemy extends Character {


    public Vector2 attackOrigin;

    public enum EnemyType {
        green, gray, blue,projectile
    }

    public Body body;

    protected EnemyType type;
    Animation<TextureRegion> rightAnimation, leftAnimation, idleAnimation, dieAnimation;
    private static float MOVEMENT_SPEED = 800f;
    private static final float EASY_MOVEMENT_SPEED = 500f;
    private static final float MEDIUM_MOVEMENT_SPEED = 1000f;
    private static final float HARD_MOVEMENT_SPEED = 2000f;

    private static final int EASY_EXTRA_HEARTS = -1;
    private static final int MEDIUM_EXTRA_HEARTS = 0;
    private static final int HARD_EXTRA_HEARTS = 1;

    public Enemy(Vector2 position, int hearts, World world) {
        super(position);

        currentHearts = hearts;

        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.DynamicBody;
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
                liveState = LiveState.HIT;
                this.attackOrigin = attackOrigin;

            }
        }
    }
    public void setDifficultyStats(int hearts) {
        String difficulty = ConfigurationManager.getDifficulty();

        switch (difficulty) {
            case "Easy":
                currentHearts = hearts + EASY_EXTRA_HEARTS;
                MOVEMENT_SPEED = EASY_MOVEMENT_SPEED;
                break;
            case "Medium":
                currentHearts = hearts + MEDIUM_EXTRA_HEARTS;
                MOVEMENT_SPEED = MEDIUM_MOVEMENT_SPEED;
                break;
            case "Hard":
                currentHearts = hearts + HARD_EXTRA_HEARTS;
                MOVEMENT_SPEED = HARD_MOVEMENT_SPEED;
                break;
            default:
                // Manejar caso por defecto (puede ser un valor medio)
                currentHearts = hearts + MEDIUM_EXTRA_HEARTS;
                MOVEMENT_SPEED = MEDIUM_MOVEMENT_SPEED;
                break;
        }
    }

    public Body getBody() {
        return this.body;
    }
}
