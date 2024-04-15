package com.svalero.game.characters;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.svalero.game.managers.ResourceManager;
import com.svalero.game.managers.SpriteManager;
import com.svalero.game.utils.Constants;

public class GreenEnemy extends Enemy {
    Vector2 direction = new Vector2();
    private static final float DETECTION_DISTANCE = 100;
    private static final float MOVEMENT_SPEED = 60f;

    private Vector2 position;
    private Player player;


    public GreenEnemy(Vector2 position, int hearts, String animationName, Player player) {
        super(position, hearts);
        this.player = player;
        this.position = position;
        this.hearts = 3;


        rightAnimation = new Animation<TextureRegion>(0.15f, ResourceManager.getRegions("green_bubble_right"));
        leftAnimation = new Animation<TextureRegion>(0.15f, ResourceManager.getRegions("green_bubble_left"));
        idleAnimation = new Animation<TextureRegion>(0.15f, ResourceManager.getRegions("green_bubble_down"));
        dieAnimation = new Animation<TextureRegion>(0.15f, ResourceManager.getRegions("green_bubble_die"));

        this.hitBox.width = Constants.PLAYER_WIDTH;
        this.hitBox.height = Constants.PLAYER_HEIGHT;
    }

    public void update(float dt, SpriteManager spriteManager) {
        stateTime += dt;

        this.hitBox.setPosition(position.x, position.y);

        if (isDying) {
            // Verificar si la animación de muerte ha terminado
            if (dieAnimation.isAnimationFinished(stateTime)) {
                // Eliminar al enemigo del juego
                die(spriteManager);
                return; // No continuar con la actualización si el enemigo está muerto
            }
            // Si la animación de muerte no ha terminado, actualizar el fotograma actual
            currentFrame = dieAnimation.getKeyFrame(stateTime, true);
            return;
        }
        // Calcular la distancia entre el enemigo y el jugador
        float distanceToPlayer = position.dst(player.getPosition());

        // Si el jugador está dentro de la distancia de detección
        if (distanceToPlayer <= DETECTION_DISTANCE) {
            // Obtener la dirección hacia la que debe moverse el enemigo para alcanzar al jugador
            direction = player.getPosition().cpy().sub(position).nor();

            // Mover al enemigo en la dirección del jugador con la velocidad de movimiento
            position.add(direction.scl(MOVEMENT_SPEED * dt));

            // Actualizar la animación según la dirección del movimiento
            if (direction.x > 0) {
                // Mover hacia la derecha
                currentFrame = rightAnimation.getKeyFrame(stateTime, true);
            } else if (direction.x < 0) {
                // Mover hacia la izquierda
                currentFrame = leftAnimation.getKeyFrame(stateTime, true);
            }
        } else {
            // Si el jugador está fuera de la distancia de detección, el enemigo está inactivo
            currentFrame = idleAnimation.getKeyFrame(stateTime, true);
        }
    }

}