package com.svalero.game.items;

import com.badlogic.gdx.physics.box2d.*;

public class CollisionObject {
    private Body body;

    public CollisionObject(float x, float y, float width, float height, World world) {
        // Definir la configuración del cuerpo
        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.StaticBody; // El objeto de colisión no se moverá
        bodyDef.position.set(x -10 + width  / 2f, y -12 + height / 2f);

        // Crear el cuerpo en el mundo
        body = world.createBody(bodyDef);

        // Crear la forma geométrica para representar el objeto de colisión (en este caso, un rectángulo)
        PolygonShape shape = new PolygonShape();
        shape.setAsBox(width / 2f, height / 2f); // Dividir el ancho y la altura por 2 para obtener la mitad

        // Definir la fixture del cuerpo
        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = shape;

        // Agregar la fixture al cuerpo
        body.createFixture(fixtureDef);

        // Liberar la memoria de la forma geométrica
        shape.dispose();
    }

}
