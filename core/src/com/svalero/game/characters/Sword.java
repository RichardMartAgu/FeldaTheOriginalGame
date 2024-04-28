package com.svalero.game.characters;

import com.badlogic.gdx.physics.box2d.*;

import static com.svalero.game.utils.Constants.PLAYER_HEIGHT;
import static com.svalero.game.utils.Constants.PLAYER_WIDTH;

public class Sword {
    private Body sword;
    public Fixture swordFixture;

    World world;

    public Sword( World world) {

        this.world = world;

        // Crear la forma de la espada
        PolygonShape swordShape = new PolygonShape();
        swordShape.setAsBox(PLAYER_WIDTH / 10f, PLAYER_HEIGHT / 10f);

        BodyDef bodySword = new BodyDef();
        bodySword.type = BodyDef.BodyType.StaticBody;
        bodySword.position.set(0,0);
        sword = world.createBody(bodySword);
        sword.setUserData(this);

        FixtureDef swordFixtureDef = new FixtureDef();
        swordFixtureDef.shape = swordShape;
        swordFixtureDef.density = 0f;
        swordFixtureDef.friction = 0f;
        swordFixtureDef.restitution = 0f;
        swordFixtureDef.isSensor = true;
        swordFixture = sword.createFixture(swordFixtureDef);

        sword.setBullet(true);

        swordShape.dispose();
    }


    public Fixture getSwordFixture() {
        return swordFixture;
    }

    public Body getSwordBody() {
        return sword;
    }
}