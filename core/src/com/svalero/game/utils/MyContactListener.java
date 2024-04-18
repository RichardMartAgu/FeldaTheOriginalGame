package com.svalero.game.utils;

import com.badlogic.gdx.physics.box2d.*;
import com.svalero.game.characters.Character;
import com.svalero.game.characters.Enemy;
import com.svalero.game.characters.Player;
import com.svalero.game.characters.Sword;

public class MyContactListener implements ContactListener {
    @Override
    public void beginContact(Contact contact) {
        // Método llamado cuando dos cuerpos comienzan a colisionar
        Body bodySword = contact.getFixtureA().getBody();
        Body bodyEnemy = contact.getFixtureB().getBody();
        Body bodyPlayer = contact.getFixtureA().getBody();

        if (bodySword.getUserData() instanceof Sword && bodyEnemy.getUserData() instanceof Enemy) {
            Enemy enemy = (Enemy) bodyEnemy.getUserData();
            enemy.hit(1, bodySword.getPosition());
            System.out.println("golpeando");
        }

        if (bodyPlayer.getUserData() instanceof Player && bodyEnemy.getUserData() instanceof Enemy) {

            Player player = (Player) bodyPlayer.getUserData();
                player.hit(1, bodyEnemy.getPosition());
                System.out.println("player hit");

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
