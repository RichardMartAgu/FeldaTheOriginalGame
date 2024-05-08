package com.svalero.game.characters;


import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;
import com.svalero.game.managers.ResourceManager;
import com.svalero.game.managers.SpriteManager;
import com.svalero.game.utils.Constants;

public class BlueProjectile extends Enemy {


    World world;
    private static final float MOVEMENT_SPEED = 800f;

    private Player player;
    private static final float LIFETIME = 3f;
    private float elapsedTime = 0f;


    public BlueProjectile(Vector2 position, int hearts, Player player, World world) {
        super(position, hearts, world);
        this.player = player;
        currentHearts = hearts;
        this.type = EnemyType.projectile;
        body.setUserData(this);

        currentFrame = ResourceManager.getRegion("blue_projectile_right");

        rect.width = currentFrame.getRegionWidth();
        rect.height = currentFrame.getRegionHeight();

        this.world = world;

        rightAnimation = new Animation<TextureRegion>(0.15f, ResourceManager.getRegions("blue_projectile_right"));
        leftAnimation = new Animation<TextureRegion>(0.15f, ResourceManager.getRegions("blue_projectile_left"));
        dieAnimation = new Animation<TextureRegion>(0.15f, ResourceManager.getRegions("blue_projectile_die"));

    }

    public void update(float dt, SpriteManager spriteManager) {
        stateTime += dt;
        elapsedTime += dt;

        Vector2 currentPosition = body.getPosition();
        position.set(currentPosition.x, currentPosition.y);

        rect.x = currentPosition.x;
        rect.y = currentPosition.y;

        // Comprobar si el proyectil ha superado su tiempo de vida
        if (liveState == LiveState.NORMAL && elapsedTime > LIFETIME) {
            liveState = LiveState.DYING;
        }

        if (liveState == LiveState.DYING) {
            currentFrame = dieAnimation.getKeyFrame(stateTime, true);
            if (dieAnimation.isAnimationFinished(stateTime)) {
                ResourceManager.getSound(Constants.SOUND + "explosion.mp3").play();
                liveState = LiveState.DEAD;
            }
        }

        // Calcular la distancia entre el enemigo y el jugador
        Vector2 playerPosition = player.getPosition();

        if (!(liveState == LiveState.DYING || liveState == LiveState.DEAD)) {
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

        }
    }
}
