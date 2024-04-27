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


    public enum InvulnerabilityState {
        TRUE, FALSE, REFRESH;
    }

    public InvulnerabilityState invulnerabilityState = InvulnerabilityState.FALSE;
    World world;
    private static final float DETECTION_DISTANCE = 100;
    private static final float MOVEMENT_SPEED = 200f;
    int repeatCount = 0;

    float refreshTime;
    float timeToInvulnerability = 5;
    private Vector2 position;
    private Player player;

    Animation<TextureRegion> invulnerabilityAnimation;

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
        invulnerabilityAnimation = new Animation<TextureRegion>(1f, ResourceManager.getRegions("invulnerability"));


    }

    public void update(float dt, SpriteManager spriteManager) {
        stateTime += dt;
        refreshTime += dt;

        Vector2 currentPosition = body.getPosition();
        position.set(currentPosition.x, currentPosition.y);

        if (liveState == LiveState.HIT) {
            Vector2 repulsionDirection = body.getPosition().cpy().sub(attackOrigin).nor();
            // Aplicar una fuerza repulsiva al cuerpo
            float repulsionForceMagnitude = 90000000000f; // Ajusta la magnitud según lo deseado
            body.applyLinearImpulse(repulsionDirection.scl(repulsionForceMagnitude), body.getWorldCenter(), true);
            liveState = LiveState.NORMAL;
        }

        if (liveState == LiveState.DYING) {
            currentFrame = dieAnimation.getKeyFrame(stateTime, true);
            if (dieAnimation.isAnimationFinished(stateTime)) {
                ResourceManager.getSound(Constants.SOUND + "die_bubble.mp3").play();
                liveState = LiveState.DEAD;
                stateTime += dt;
            }
        }

        // Calcular la distancia entre el enemigo y el jugador
        Vector2 playerPosition = player.getPosition();
        float distanceToPlayer = position.dst(playerPosition);

        if (!(liveState == LiveState.DYING || liveState == LiveState.DEAD)) {
            if (invulnerabilityState == InvulnerabilityState.TRUE) {
                currentFrame = invulnerabilityAnimation.getKeyFrame(stateTime, true);
                body.setLinearVelocity(0, 0);
                body.setActive(false);
            } else {
                // Si el jugador está dentro de la distancia de detección
                if (distanceToPlayer <= DETECTION_DISTANCE) {
                    // Obtener la dirección hacia la que debe moverse el enemigo para alcanzar al jugador
                    Vector2 direction = playerPosition.cpy().sub(currentPosition).nor();


                    // Aplicar una fuerza lineal al cuerpo del enemigo en la dirección del jugador
                    body.applyLinearImpulse(direction.scl(MOVEMENT_SPEED), body.getWorldCenter(), true);

                    // Actualizar la animación según la dirección del movimiento

                    if (direction.x > 0) {
                        // Mover hacia la derecha
                        currentFrame = rightAnimation.getKeyFrame(stateTime, true);
                    } else if (direction.x < 0) {
                        // Mover hacia la izquierda
                        currentFrame = leftAnimation.getKeyFrame(stateTime, true);
                    }

                } else {
                    body.setLinearVelocity(1, 1);
                    // Si el jugador está fuera de la distancia de detección, el enemigo está inactivo
                    currentFrame = idleAnimation.getKeyFrame(stateTime, true);
                }
            }
            timeToInvulnerability = MathUtils.random(4, 15);
            if (invulnerabilityState == InvulnerabilityState.FALSE && refreshTime > timeToInvulnerability) {
                boolean invulnerability = MathUtils.randomBoolean(0.2f);
                if (invulnerability) {
                    invulnerabilityState = InvulnerabilityState.TRUE;
                    repeatCount = 0;
                    stateTime = 0;
                }
            }
            int numRepeats = MathUtils.random(3, 7);
            if (invulnerabilityState == InvulnerabilityState.TRUE && invulnerabilityAnimation.isAnimationFinished(stateTime)) {
                repeatCount++;

                if (repeatCount >= numRepeats) {
                    invulnerabilityState = InvulnerabilityState.FALSE;
                    body.setActive(true);
                    refreshTime = 0;
                    repeatCount = 0;
                }
            }
        }
    }
}






