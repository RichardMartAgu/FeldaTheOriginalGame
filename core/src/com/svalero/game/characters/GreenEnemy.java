package com.svalero.game.characters;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.utils.Timer;
import com.svalero.game.managers.ResourceManager;
import com.svalero.game.managers.SpriteManager;
import com.svalero.game.utils.Constants;

public class GreenEnemy extends Enemy {

    Vector2 direction = new Vector2();
    private static final float DETECTION_DISTANCE = 100;
    private static final float MOVEMENT_SPEED = 800f;

    private Vector2 position;
    private Player player;


    public GreenEnemy(Vector2 position, int hearts, Player player,World world) {
        super(position, hearts,world);
        this.player = player;
        this.position = position;
        this.hearts = 3;


        rightAnimation = new Animation<TextureRegion>(0.15f, ResourceManager.getRegions("green_bubble_right"));
        leftAnimation = new Animation<TextureRegion>(0.15f, ResourceManager.getRegions("green_bubble_left"));
        idleAnimation = new Animation<TextureRegion>(0.15f, ResourceManager.getRegions("green_bubble_down"));
        dieAnimation = new Animation<TextureRegion>(0.15f, ResourceManager.getRegions("green_bubble_die"));

    }

    public void update(float dt, SpriteManager spriteManager) {
        stateTime += dt;

        Vector2 currentPosition = body.getPosition();
        position.set(currentPosition.x, currentPosition.y);

        if(state == State.DYING){
            dieAnimation.getKeyFrame(stateTime, true);
        if (dieAnimation.isAnimationFinished(stateTime)){
            state = State.DEAD;
            stateTime += dt;
            }
        }

        // Calcular la distancia entre el enemigo y el jugador
        Vector2 playerPosition = player.getPosition();
        float distanceToPlayer = position.dst(playerPosition);

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


}