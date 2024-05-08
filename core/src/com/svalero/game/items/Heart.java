package com.svalero.game.items;

import com.svalero.game.managers.ResourceManager;

public class Heart extends Item {

    public Heart(float x, float y, int score) {
        super(x, y);
        this.score = score;

        currentFrame = ResourceManager.getRegion("heart");
        rect.x = x;
        rect.y = y;
        rect.width = currentFrame.getRegionWidth();
        rect.height = currentFrame.getRegionHeight();

    }

    public void update(float dt) {

    }

}


