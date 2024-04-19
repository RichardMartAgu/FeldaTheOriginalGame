package com.svalero.game.utils;

import com.badlogic.gdx.physics.box2d.*;
import com.svalero.game.characters.Character;
import com.svalero.game.characters.Enemy;
import com.svalero.game.characters.Player;
import com.svalero.game.characters.Sword;
import com.svalero.game.items.Heart;
import com.svalero.game.items.Item;
import com.svalero.game.items.Rupia;
import com.svalero.game.managers.ResourceManager;

public class MyContactListener implements ContactListener {
    @Override
    public void beginContact(Contact contact) {
        // Método llamado cuando dos cuerpos comienzan a colisionar
        Body bodySword = contact.getFixtureA().getBody();
        Body bodyEnemy = contact.getFixtureB().getBody();
        Body bodyPlayer = contact.getFixtureA().getBody();
        Body bodyItem = contact.getFixtureB().getBody();

        if (bodySword.getUserData() instanceof Sword && bodyEnemy.getUserData() instanceof Enemy) {
            Enemy enemy = (Enemy) bodyEnemy.getUserData();
            enemy.hit(1, bodySword.getPosition());;
            ResourceManager.getSound(Constants.SOUND + "hurt_bubble.mp3").play();
        }

        if (bodyPlayer.getUserData() instanceof Player && bodyEnemy.getUserData() instanceof Enemy) {

            Player player = (Player) bodyPlayer.getUserData();
                player.hit(1, bodyEnemy.getPosition());
                ResourceManager.getSound(Constants.SOUND + "hurt.mp3").play();
        }

        if (bodyPlayer.getUserData() instanceof Player && bodyItem.getUserData() instanceof Item) {
            Item item = (Item) bodyItem.getUserData(); // Debes obtener el objeto Item
            Player player = (Player) bodyPlayer.getUserData();
            if (item instanceof Heart) {
                ResourceManager.getSound(Constants.SOUND + "collect_heart.mp3").play();
                item.collected();
                player.addHeart();
            } else if (item instanceof Rupia) {
                player.addRupia(item.score);
                ResourceManager.getSound(Constants.SOUND + "collect_rupia.mp3").play();
                item.collected();
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

    // Otros métodos de la interfaz ContactListener que puedes implementar si los necesitas
    // ...
}
