package com.svalero.game.characters;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;
import com.svalero.game.managers.ResourceManager;
import com.svalero.game.managers.SpriteManager;
import com.svalero.game.utils.Constants;

public class GrayEnemy extends Enemy {


    public enum VisibilityState {
        TRUE, FALSE, REFRESH;
    }

    private float timeSinceDisappear = 0;
    private final float DISAPPEAR_TIME = 6f;
    public VisibilityState visibilityState = VisibilityState.TRUE;
    World world;
    private static final float DETECTION_DISTANCE = 100;
    private static final float MOVEMENT_SPEED = 800f;

    private Vector2 position;
    private Player player;

    Animation<TextureRegion> voidAnimation;

    public GrayEnemy(Vector2 position, int hearts, Player player, World world) {

        super(position, hearts, world);
        this.player = player;
        this.position = position;
        currentHearts = hearts;
        this.type = Enemy.EnemyType.gray;
        body.setUserData(this);

        this.world = world;

        rightAnimation = new Animation<TextureRegion>(0.15f, ResourceManager.getRegions("gray_bubble_right"));
        leftAnimation = new Animation<TextureRegion>(0.15f, ResourceManager.getRegions("gray_bubble_left"));
        idleAnimation = new Animation<TextureRegion>(0.15f, ResourceManager.getRegions("gray_bubble_down"));
        dieAnimation = new Animation<TextureRegion>(0.15f, ResourceManager.getRegions("grey_bubble_die"));
        voidAnimation = new Animation<TextureRegion>(0.15f, ResourceManager.getRegions("nothing"));

    }

    public void update(float dt, SpriteManager spriteManager) {
        stateTime += dt;
        System.out.println(currentHearts);

        Vector2 currentPosition = body.getPosition();
        position.set(currentPosition.x, currentPosition.y);

        if (liveState == Character.LiveState.HIT) {
            Vector2 repulsionDirection = body.getPosition().cpy().sub(attackOrigin).nor();
            // Aplicar una fuerza repulsiva al cuerpo
            float repulsionForceMagnitude = 90000000000f; // Ajusta la magnitud según lo deseado
            body.applyLinearImpulse(repulsionDirection.scl(repulsionForceMagnitude), body.getWorldCenter(), true);
            liveState = Character.LiveState.NORMAL;
        }

        if(liveState == LiveState.DYING){
            currentFrame = dieAnimation.getKeyFrame(stateTime, true);
            if (dieAnimation.isAnimationFinished(stateTime)){
                ResourceManager.getSound(Constants.SOUND + "die_bubble.mp3").play();
                liveState = LiveState.DEAD;
                stateTime += dt;
            }
        }

        // Calcular la distancia entre el enemigo y el jugador
        Vector2 playerPosition = player.getPosition();
        float distanceToPlayer = position.dst(playerPosition);

        if (!(liveState == Character.LiveState.DYING || liveState == Character.LiveState.DEAD)) {
            if (distanceToPlayer <= DETECTION_DISTANCE) {
                // Calcular si el enemigo debe desaparecer

                if (visibilityState == VisibilityState.TRUE || visibilityState == VisibilityState.REFRESH) {
                    boolean shouldDisappear = MathUtils.randomBoolean(0.9f);
                    if (shouldDisappear && visibilityState != VisibilityState.REFRESH) {
                        currentFrame = voidAnimation.getKeyFrame(stateTime, true);

                        body.setLinearVelocity(0, 0);

                        if (visibilityState == VisibilityState.TRUE) {
                            visibilityState = VisibilityState.FALSE;
                        }

                        // Calcular una nueva posición aleatoria detrás del jugador
                        float randomX = playerPosition.x + MathUtils.random(-DETECTION_DISTANCE, DETECTION_DISTANCE);
                        float randomY = playerPosition.y + MathUtils.random(-DETECTION_DISTANCE, DETECTION_DISTANCE);
                        Vector2 newPosition = new Vector2(randomX, randomY);

                        // Mover al enemigo a la nueva posición
                        body.setTransform(newPosition, 0);

                    } else {

                        Vector2 direction = playerPosition.cpy().sub(currentPosition).nor();

                        body.applyLinearImpulse(direction.scl(MOVEMENT_SPEED), body.getWorldCenter(), true);

                        if (direction.x > 0) {
                            // Mover hacia la derecha
                            currentFrame = rightAnimation.getKeyFrame(stateTime, true);
                        } else if (direction.x < 0) {
                            // Mover hacia la izquierda
                            currentFrame = leftAnimation.getKeyFrame(stateTime, true);
                        }
                    }
                }
            } else {
                body.setLinearVelocity(1, 1);
                // Si el jugador está fuera de la distancia de detección, el enemigo está inactivo
                currentFrame = idleAnimation.getKeyFrame(stateTime, true);
            }

            if (visibilityState == VisibilityState.FALSE || visibilityState == VisibilityState.TRUE) {
                timeSinceDisappear = 0;
                visibilityState = VisibilityState.REFRESH;
            }
            timeSinceDisappear += dt;
            if (timeSinceDisappear >= DISAPPEAR_TIME) {
                visibilityState = VisibilityState.TRUE;

            }
        }
    }
}



