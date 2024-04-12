package com.svalero.game.managers;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Disposable;
import com.svalero.game.characters.Player;
import com.svalero.game.screen.MainMenuScreen;

public class SpriteManager implements Disposable {

    Player player;
    boolean pause;

    public SpriteManager() {
        initialize();
    }

    private void initialize() {
        player = new Player(new Vector2(0, 0), "idel_down");
        pause = false;
    }
    private void handleGameScreenInput() {
        if (Gdx.input.isKeyPressed(Input.Keys.ESCAPE)) {
            ((Game) Gdx.app.getApplicationListener()).setScreen(new MainMenuScreen());
        }

        if (Gdx.input.isKeyJustPressed(Input.Keys.P)) {
            pause = !pause;
        }
    }

    public void update(float dt) {
        if (!pause) {
            player.manageInput();
        }
        handleGameScreenInput();
    }
    @Override
    public void dispose() {

    }

}
