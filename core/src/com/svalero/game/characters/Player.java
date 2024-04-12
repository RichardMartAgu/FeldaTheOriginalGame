package com.svalero.game.characters;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.math.Vector2;

public class Player extends Character {


    public Character.State state;
    float stateTime;


    public Player(Vector2 position, String animationName) {
        super(position, animationName);
    }

    public void manageInput() {
        if (Gdx.input.isKeyPressed(Input.Keys.RIGHT)) {
            move(20, 0);
        }
        if (Gdx.input.isKeyPressed(Input.Keys.LEFT)) {
            move(-20, 0);
        }
        if (Gdx.input.isKeyPressed(Input.Keys.UP)) {
            move(0, 20);
        }
        if (Gdx.input.isKeyPressed(Input.Keys.DOWN)) {
            move(0, -20);
        }
    }

}
