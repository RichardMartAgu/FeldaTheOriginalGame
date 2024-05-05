package com.svalero.game.items;

import com.svalero.game.managers.ResourceManager;

public class Rupia extends Item {


    public Rupia(float x, float y, int score) {
        super(x, y);
        this.score = score;

        currentFrame = ResourceManager.getRegion("rupia");
        rect.x = x;
        rect.y = y;
        rect.width = currentFrame.getRegionWidth();
        rect.height = currentFrame.getRegionHeight();
    }

    @Override
    public void update(float dt) {
        System.out.println(rect);
    }


}

