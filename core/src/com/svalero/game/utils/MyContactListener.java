package com.svalero.game.utils;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.physics.box2d.*;
import com.svalero.game.characters.*;
import com.svalero.game.items.Goal;
import com.svalero.game.items.Heart;
import com.svalero.game.items.Item;
import com.svalero.game.items.Rupia;
import com.svalero.game.managers.ResourceManager;
import com.svalero.game.screen.GameScreen;

public class MyContactListener implements ContactListener {


    @Override
    public void beginContact(Contact contact) {
        // Método llamado cuando dos cuerpos comienzan a colisionar

        Fixture fixtureA = contact.getFixtureA();
        Fixture fixtureB = contact.getFixtureB();

        Object userDataA = fixtureA.getBody().getUserData();
        Object userDataB = fixtureB.getBody().getUserData();


        if (userDataA instanceof Player && userDataB instanceof Enemy) {
            Player player = (Player) userDataA;
            player.hit(1, fixtureB.getBody().getPosition());
            ResourceManager.getSound(Constants.SOUND + "hurt.mp3").play();
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

}
