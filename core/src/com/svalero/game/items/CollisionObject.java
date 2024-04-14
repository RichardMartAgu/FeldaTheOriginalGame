package com.svalero.game.items;

import com.badlogic.gdx.math.Rectangle;

public class CollisionObject {
    private Rectangle bounds;

    public CollisionObject(float x, float y, float width, float height) {
        bounds = new Rectangle(x, y, width, height);
    }

    public Rectangle getBounds() {
        return bounds;
    }

    // Otros métodos, como para detectar colisiones o realizar acciones específicas
}
