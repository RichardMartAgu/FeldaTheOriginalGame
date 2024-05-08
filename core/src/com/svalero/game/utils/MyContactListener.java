package com.svalero.game.utils;

import com.badlogic.gdx.physics.box2d.*;
import com.svalero.game.characters.Enemy;
import com.svalero.game.characters.Player;
import com.svalero.game.managers.ResourceManager;

public class MyContactListener implements ContactListener {


    @Override
    public void beginContact(Contact contact) {
        // Método llamado cuando dos cuerpos comienzan a colisionar

        Fixture fixtureA = contact.getFixtureA();
        Fixture fixtureB = contact.getFixtureB();

        Object userDataA = fixtureA.getBody().getUserData();
        Object userDataB = fixtureB.getBody().getUserData();

        //Cuando un enemigo colisiona con el jugador
        if (userDataA instanceof Player && userDataB instanceof Enemy) {
            Player player = (Player) userDataA;
            //Golpeamos al jugador
            player.hit(1, fixtureB.getBody().getPosition());
            //hacemos el sonido de daño al jugador
            ResourceManager.getSound(Constants.SOUND + "hurt.mp3").play();
        }

    }

    @Override
    public void endContact(Contact contact) {
    }

    @Override
    public void preSolve(Contact contact, Manifold oldManifold) {

    }

    @Override
    public void postSolve(Contact contact, ContactImpulse impulse) {

    }

}
