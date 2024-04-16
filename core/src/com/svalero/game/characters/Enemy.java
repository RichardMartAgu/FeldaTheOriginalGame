package com.svalero.game.characters;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.svalero.game.managers.SpriteManager;
import com.svalero.game.utils.Constants;

public class Enemy extends Character {


    public enum EnemyType {
        green, gray, yellow
    }

    public Body body;

    protected EnemyType type;
    public boolean isDying = false;

    Animation<TextureRegion> rightAnimation, leftAnimation, idleAnimation, dieAnimation;

    public Enemy(Vector2 position, int hearts, World world) {
        super(position, hearts);

        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.DynamicBody; // Cuerpo dinámico
        bodyDef.position.set(position.x, position.y);
        body = world.createBody(bodyDef);

        PolygonShape shape = new PolygonShape();
        shape.setAsBox(Constants.PLAYER_WIDTH / 2f, Constants.PLAYER_HEIGHT / 2f);

        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = shape;
        fixtureDef.density = 2f;
        fixtureDef.friction = 20f;
        fixtureDef.restitution = 0.2f;

        body.createFixture(fixtureDef);
        shape.dispose();

    }


    @Override
    public void update(float dt, SpriteManager spriteManager) {
    }



    public void reduceLife(int amount) {
        if (isDying) return;
        hearts -= amount;
        if (hearts <= 0) {
            isDying = true;
        }
    }

    public void handleCollision(Vector2 playerPosition) {
        // Calcular la dirección desde el enemigo hacia el jugador
        Vector2 directionToPlayer = playerPosition.cpy().sub(position).nor();

        // Definir la cantidad de retroceso y la duración
        float backwardDistance = 50; // La cantidad de píxeles que el enemigo se desplaza hacia atrás
        float backwardDuration = 0.1f; // Duración en segundos durante la cual se aplica el retroceso

        // Calcular el desplazamiento hacia atrás del enemigo
        Vector2 backwardMovement = directionToPlayer.scl(backwardDistance);

        // Calcular el paso de movimiento para cada frame
        float stepX = backwardMovement.x / (backwardDuration / Gdx.graphics.getDeltaTime());
        float stepY = backwardMovement.y / (backwardDuration / Gdx.graphics.getDeltaTime());

        // Mover el enemigo píxel por píxel en la dirección opuesta al jugador
        float elapsed = 0;
        while (elapsed < backwardDuration) {
            position.sub(stepX, stepY);
            elapsed += Gdx.graphics.getDeltaTime();
        }
    }

    public Body getBody() {
        return this.body;
    }
}
