package com.svalero.game.characters;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.svalero.game.utils.Constants;

import static com.svalero.game.utils.Constants.PLAYER_HEIGHT;
import static com.svalero.game.utils.Constants.PLAYER_WIDTH;

public class Sword {
    private Body sword;
    public Fixture swordFixture;

    public Vector2 position;
    World world;

    TextureRegion currentFrame;
    public Rectangle rect;

    public Sword(World world) {
        this.world = world;
        rect = new Rectangle();

        rect.width = Constants.SWORD_WIDTH;
        rect.height = Constants.SWORD_HEIGHT;

        // Crear la forma de la espada
        PolygonShape swordShape = new PolygonShape();
        swordShape.setAsBox(PLAYER_WIDTH / 10f, PLAYER_HEIGHT / 10f);

        BodyDef bodySword = new BodyDef();
        bodySword.type = BodyDef.BodyType.StaticBody;
        bodySword.position.set(0, 0);
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