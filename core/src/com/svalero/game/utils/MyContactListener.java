package com.svalero.game.utils;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.physics.box2d.*;
import com.svalero.game.characters.Enemy;
import com.svalero.game.characters.Player;

public class MyContactListener implements ContactListener {
    @Override
    public void beginContact(Contact contact) {
        // Método llamado cuando dos cuerpos comienzan a colisionar
        Body bodyPLayer = contact.getFixtureA().getBody();
        Body bodyEnemy = contact.getFixtureB().getBody();

        if (bodyPLayer.getUserData() instanceof Player && bodyEnemy.getUserData() instanceof Enemy) {
            Enemy enemy = (Enemy) bodyEnemy.getUserData();
            enemy.hit();
            System.out.println("Player collided with enemy!");

        }
    }


    @Override
    public void endContact(Contact contact) {
        // Método llamado cuando dos cuerpos dejan de colisionar
    }

    @Override
    public void preSolve(Contact contact, Manifold oldManifold) {

    }

    @Override
    public void postSolve(Contact contact, ContactImpulse impulse) {

    }

    // Otros métodos de la interfaz ContactListener que puedes implementar si los necesitas
    // ...
}
