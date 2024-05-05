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

        Body bodySword = contact.getFixtureA().getBody();
        Body bodyPlayer = contact.getFixtureA().getBody();
        Body bodyEnemy = contact.getFixtureB().getBody();


        if (bodyPlayer.getUserData() instanceof Player && bodyEnemy.getUserData() instanceof Enemy) {
            Player player = (Player) bodyPlayer.getUserData();
            player.hit(1, bodyEnemy.getPosition());
            ResourceManager.getSound(Constants.SOUND + "hurt.mp3").play();
        }

        if (bodySword.getUserData() instanceof Sword && bodyEnemy.getUserData() instanceof Enemy) {
            Enemy enemy = (Enemy) bodyEnemy.getUserData();
            if (enemy instanceof GreenEnemy) {
                ResourceManager.getSound(Constants.SOUND + "hurt_bubble.mp3").play();
                enemy.hit(1, bodySword.getPosition());
            } else if (enemy instanceof GrayEnemy) {
                ResourceManager.getSound(Constants.SOUND + "hurt_bubble.mp3").play();
                enemy.hit(1, bodySword.getPosition());
            } else if (enemy instanceof BlueEnemy) {
                ResourceManager.getSound(Constants.SOUND + "hurt_bubble.mp3").play();
                enemy.hit(1, bodySword.getPosition());
            } else if (enemy instanceof BlueProjectile) {
                ResourceManager.getSound(Constants.SOUND + "hurt_bubble.mp3").play();
                enemy.hit(1, bodySword.getPosition());
            }
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
