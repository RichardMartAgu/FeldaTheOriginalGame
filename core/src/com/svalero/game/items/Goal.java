package com.svalero.game.items;

import com.svalero.game.managers.ResourceManager;

public class Goal extends Item {

    public Goal(float x, float y) {
        super(x, y);

        currentFrame = ResourceManager.getRegion("nothing");
        rect.x = x;
        rect.y = y;
        rect.width = currentFrame.getRegionWidth();
        rect.height = currentFrame.getRegionHeight();
    }

    @Override
    public void update(float dt) {

    }
}


