package com.svalero.game.items;

import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.utils.Disposable;

public class CollisionObject implements Disposable {
    private Body body;

    public CollisionObject(float x, float y, float width, float height, World world) {
        // Definir la configuración del cuerpo
        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.StaticBody;
        bodyDef.position.set(x - 10 + width / 2f, y - 12 + height / 2f);

        // Crear el cuerpo en el mundo
        body = world.createBody(bodyDef);
        PolygonShape shape = new PolygonShape();
        shape.setAsBox(width / 2f, height / 2f);

        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = shape;

        body.createFixture(fixtureDef);

        shape.dispose();
    }

    @Override
    public void dispose() {

    }
}
