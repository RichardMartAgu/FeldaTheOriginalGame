package com.svalero.game.characters;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.svalero.game.managers.ResourceManager;
import com.svalero.game.managers.SpriteManager;
import com.svalero.game.utils.Constants;

import static com.svalero.game.characters.Character.State.*;

public class Player extends Character {

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
    Animation<TextureRegion> rightAnimation, idleRightAnimation, leftAnimation, idleLeftAnimation,
            upAnimation, idleUpAnimation, downAnimation, idleDownAnimation, attackRightAnimation, attackLeftAnimation,
            attackUpAnimation, attackDownAnimation;

    public Player(Vector2 position, int hearts, String animationName) {
        super(position, hearts, animationName);

        currentHearts = hearts;

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

    public void manageInput() {

        if(Gdx.input.isKeyJustPressed(Input.Keys.SPACE) && !isAttackInProgress){
            playAttackAnimation();
        }

        if (!isAttackInProgress) {
            if (Gdx.input.isKeyPressed(Input.Keys.RIGHT) && !isCollidingRight()) {
                move(Constants.PLAYER_SPEED, 0);
                state = State.RIGHT;
                previousState = State.RIGHT;
            } else if (Gdx.input.isKeyPressed(Input.Keys.LEFT)&& !isCollidingLeft()) {
                move(-Constants.PLAYER_SPEED, 0);
                state = State.LEFT;
                previousState = State.LEFT;
            } else if (Gdx.input.isKeyPressed(Input.Keys.UP)&& !isCollidingUp()) {
                move(0, Constants.PLAYER_SPEED);
                state = State.UP;
                previousState = State.UP;
            } else if (Gdx.input.isKeyPressed(Input.Keys.DOWN)&& !isCollidingDown()) {
                move(0, -Constants.PLAYER_SPEED);
                state = State.DOWN;
                previousState = State.DOWN;
            } else {
                // Si ninguna tecla está presionada, el personaje está en estado inactivo
                state = State.IDLE;
            }
        }

        if (position.x <= 0)
            position.x = 0;

        if (position.y <= 0)
            position.y = 0;

    }

    public void update(float dt, SpriteManager spriteManager) {
        stateTime += dt;

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
                    break;
                case LEFT:
                    currentFrame = attackLeftAnimation.getKeyFrame(stateTime, true);
                    break;
                case UP:
                    currentFrame = attackUpAnimation.getKeyFrame(stateTime, true);
                    break;
                case DOWN:
                    currentFrame = attackDownAnimation.getKeyFrame(stateTime, true);
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
    public void setCollidingRight(boolean colliding) {
        collidingRight  = colliding;
    }
    public boolean isCollidingRight() {
        return collidingRight ;
    }
    public void setCollidingUp(boolean colliding) {
        collidingUp = colliding;
    }
    public boolean isCollidingUp() {
        return collidingUp;
    }
    public void setCollidingDown(boolean colliding) {
        collidingDown  = colliding;
    }
    public boolean isCollidingDown() {
        return collidingDown ;
    }
    public void setCollidingLeft(boolean colliding) {
        collidingLeft = colliding;
    }
    public boolean isCollidingLeft() {
        return collidingLeft;
    }

}
