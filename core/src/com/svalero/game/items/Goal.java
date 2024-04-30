package com.svalero.game.items;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.svalero.game.managers.ResourceManager;

public class Goal extends Item {

    public Goal(Vector2 position, int score, World world) {
        super(position, world);
        body.setUserData(this);;
    }
}