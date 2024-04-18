package com.svalero.game.items;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;
import com.svalero.game.managers.ResourceManager;

public class Heart extends Item {

    public Heart(Vector2 position, int score, World world) {
        super(position, world);
        this.score = score;
        body.setUserData(this);

        currentFrame = ResourceManager.getRegion("heart");
    }

    public void update(float dt) {
        stateTime += dt;
        Vector2 currentPosition = body.getPosition();
        position.set(currentPosition.x, currentPosition.y);

    }

}


