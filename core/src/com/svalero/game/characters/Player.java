package com.svalero.game.characters;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.svalero.game.managers.ResourceManager;
import com.svalero.game.managers.SpriteManager;
import com.svalero.game.utils.Constants;

import static com.svalero.game.utils.Constants.*;

public class Player extends Character {
    private Body body;
    private Body sword;
    public Fixture swordFixture;
    private Vector2 position;
    public boolean isIdleInProgress = false;
    public boolean isAttackInProgress = false;
    public State previousState;
    public int rupias;
    private boolean collidingRight = false;
    private boolean collidingLeft = false;
    private boolean collidingUp = false;
    private boolean collidingDown = false;
    public Character.State state;
    float stateTime;
    PolygonShape swordShape = new PolygonShape();
    int distanceSword = 8;

    Animation<TextureRegion> rightAnimation, idleRightAnimation, leftAnimation, idleLeftAnimation,
            upAnimation, idleUpAnimation, downAnimation, idleDownAnimation, attackRightAnimation, attackLeftAnimation,
            attackUpAnimation, attackDownAnimation;

    public Player(Vector2 position, int hearts, World world) {
        super(position, hearts);
        this.position = position;

        currentHearts = hearts;

        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.DynamicBody;
        bodyDef.position.set(position.x, position.y);
        body = world.createBody(bodyDef);

        PolygonShape shape = new PolygonShape();
        shape.setAsBox(Constants.PLAYER_WIDTH / 2f, Constants.PLAYER_HEIGHT / 2f);

        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = shape;
        fixtureDef.density = 0f;
        fixtureDef.friction = 0.4f;
        fixtureDef.restitution = 0.2f;
        body.createFixture(fixtureDef);
        shape.dispose();

//        Body de la espada

        BodyDef bodySword = new BodyDef();
        bodySword.type = BodyDef.BodyType.DynamicBody;
        bodySword.position.set(0, 0);
        sword = world.createBody(bodySword);

        PolygonShape swordShape = new PolygonShape();
        swordShape.setAsBox(PLAYER_WIDTH / 10f, PLAYER_HEIGHT / 10f);
        FixtureDef swordFixtureDef = new FixtureDef();
        swordFixtureDef.shape = swordShape;
        swordFixtureDef.density = 0f;
        swordFixtureDef.friction = 0f;
        swordFixtureDef.restitution = 0f;

        swordFixtureDef.isSensor = true;
        swordFixture = sword.createFixture(swordFixtureDef);
        swordShape.dispose();

        rightAnimation = new Animation<TextureRegion>(0.15f, ResourceManager.getRegions("idle_right"));
        leftAnimation = new Animation<TextureRegion>(0.15f, ResourceManager.getRegions("idle_left"));
        upAnimation = new Animation<TextureRegion>(0.15f, ResourceManager.getRegions("walk_up"));
        downAnimation = new Animation<TextureRegion>(0.15f, ResourceManager.getRegions("walk_down"));
        idleRightAnimation = new Animation<TextureRegion>(0.40f, ResourceManager.getRegions("walk_right"));
        idleLeftAnimation = new Animation<TextureRegion>(0.40f, ResourceManager.getRegions("idle_left"));
        idleUpAnimation = new Animation<TextureRegion>(0.40f, ResourceManager.getRegions("idle_up"));
        idleDownAnimation = new Animation<TextureRegion>(0.80f, ResourceManager.getRegions("idle_down"));

        attackUpAnimation = new Animation<TextureRegion>(0.040f, ResourceManager.getRegions("atack_up"));
        attackDownAnimation = new Animation<TextureRegion>(0.040f, ResourceManager.getRegions("atack_down"));
        attackRightAnimation = new Animation<TextureRegion>(0.040f, ResourceManager.getRegions("atack_right"));
        attackLeftAnimation = new Animation<TextureRegion>(0.040f, ResourceManager.getRegions("atack_left"));

        previousState = State.IDLE;

    }

    public void manageInput(float dt) {

        if (Gdx.input.isKeyJustPressed(Input.Keys.SPACE) && !isAttackInProgress) {
            playAttackAnimation();
        }

        if (!isAttackInProgress) {
            if (Gdx.input.isKeyPressed(Input.Keys.RIGHT) && !isCollidingRight()) {
                body.applyLinearImpulse(new Vector2(Constants.PLAYER_SPEED, 0), body.getWorldCenter(), true);
                state = State.RIGHT;
                previousState = State.RIGHT;
            } else if (Gdx.input.isKeyPressed(Input.Keys.LEFT) && !isCollidingLeft()) {
                body.applyLinearImpulse(new Vector2(-Constants.PLAYER_SPEED, 0), body.getWorldCenter(), true);
                state = State.LEFT;
                previousState = State.LEFT;
            } else if (Gdx.input.isKeyPressed(Input.Keys.UP) && !isCollidingUp()) {
                body.applyLinearImpulse(new Vector2(0, Constants.PLAYER_SPEED), body.getWorldCenter(), true);
                state = State.UP;
                previousState = State.UP;
            } else if (Gdx.input.isKeyPressed(Input.Keys.DOWN) && !isCollidingDown()) {
                body.applyLinearImpulse(new Vector2(0, -Constants.PLAYER_SPEED), body.getWorldCenter(), true);
                state = State.DOWN;
                previousState = State.DOWN;
            } else {
                body.setLinearVelocity(0, 0);
                // Si ninguna tecla está presionada, el personaje está en estado inactivo
                state = State.IDLE;
            }
        }
    }

    public void update(float dt, SpriteManager spriteManager) {
        stateTime += dt;

        position.set(body.getPosition().x, body.getPosition().y);

        if (!isAttackInProgress) {
            // Si no está atacando, muestra la animación correspondiente al estado del jugador
            switch (state) {
                case RIGHT:
                    currentFrame = rightAnimation.getKeyFrame(stateTime, true);
                    break;
                case LEFT:
                    currentFrame = leftAnimation.getKeyFrame(stateTime, true);
                    break;
                case UP:
                    currentFrame = upAnimation.getKeyFrame(stateTime, true);
                    break;
                case DOWN:
                    currentFrame = downAnimation.getKeyFrame(stateTime, true);
                    break;
                case IDLE:
                    ((PolygonShape) swordFixture.getShape()).setAsBox(0, 0,
                            new Vector2(0, 0),
                            0f);
                    playIdleAnimation();
                    // Muestra la animación correspondiente al estado inactivo basado en previousState
                    switch (previousState) {
                        case RIGHT:
                            currentFrame = idleRightAnimation.getKeyFrame(stateTime, true);
                            break;
                        case LEFT:
                            currentFrame = idleLeftAnimation.getKeyFrame(stateTime, true);
                            break;
                        case UP:
                            currentFrame = idleUpAnimation.getKeyFrame(stateTime, true);
                            break;
                        case DOWN:
                            currentFrame = idleDownAnimation.getKeyFrame(stateTime, true);
                            break;
                    }
                    break;
            }
        } else {

            switch (previousState) {
                case RIGHT:
                    currentFrame = attackRightAnimation.getKeyFrame(stateTime, true);
                    ((PolygonShape) swordFixture.getShape()).setAsBox(SWORD_WIDTH, SWORD_HEIGHT,
                            new Vector2(position.x + distanceSword, position.y),
                            0f);
                    break;
                case LEFT:
                    currentFrame = attackLeftAnimation.getKeyFrame(stateTime, true);
                    ((PolygonShape) swordFixture.getShape()).setAsBox(SWORD_WIDTH, SWORD_HEIGHT,
                            new Vector2(position.x - distanceSword, position.y),
                            0f);
                    break;
                case UP:
                    currentFrame = attackUpAnimation.getKeyFrame(stateTime, true);
                    ((PolygonShape) swordFixture.getShape()).setAsBox(Constants.SWORD_HEIGHT, SWORD_WIDTH,
                            new Vector2(position.x , position.y + distanceSword),
                            0f);
                    break;
                case DOWN:
                    currentFrame = attackDownAnimation.getKeyFrame(stateTime, true);
                    ((PolygonShape) swordFixture.getShape()).setAsBox(Constants.SWORD_HEIGHT, Constants.SWORD_WIDTH,
                            new Vector2(position.x , position.y - distanceSword),
                            0f);
                    break;
            }
        }
        if (attackUpAnimation.isAnimationFinished(stateTime) ||
                attackDownAnimation.isAnimationFinished(stateTime) ||
                attackRightAnimation.isAnimationFinished(stateTime) ||
                attackLeftAnimation.isAnimationFinished(stateTime)) {
            isAttackInProgress = false;
        }

        if (isIdleInProgress && idleUpAnimation.isAnimationFinished(stateTime) || idleDownAnimation.isAnimationFinished(stateTime) || idleRightAnimation.isAnimationFinished(stateTime) || idleLeftAnimation.isAnimationFinished(stateTime)) {
            isIdleInProgress = false;
        }
        if (isAttackInProgress && attackUpAnimation.isAnimationFinished(stateTime) || attackDownAnimation.isAnimationFinished(stateTime) || attackRightAnimation.isAnimationFinished(stateTime) || attackLeftAnimation.isAnimationFinished(stateTime)) {
            isAttackInProgress = false;
        }
    }

    public void playIdleAnimation() {
        if (!isIdleInProgress) {
            isIdleInProgress = true;
        }
    }

    public void playAttackAnimation() {
        if (!isAttackInProgress) {
            isAttackInProgress = true;
            stateTime = 0;
        }
    }

    public Body getBody() {
        return body;
    }

    public void setCollidingRight(boolean colliding) {
        collidingRight = colliding;
    }

    public boolean isCollidingRight() {
        return collidingRight;
    }

    public void setCollidingUp(boolean colliding) {
        collidingUp = colliding;
    }

    public boolean isCollidingUp() {
        return collidingUp;
    }

    public void setCollidingDown(boolean colliding) {
        collidingDown = colliding;
    }

    public boolean isCollidingDown() {
        return collidingDown;
    }

    public void setCollidingLeft(boolean colliding) {
        collidingLeft = colliding;
    }

    public boolean isCollidingLeft() {
        return collidingLeft;
    }

    public Fixture getSwordFixture() {
        return swordFixture;
    }
}