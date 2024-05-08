
package com.svalero.game.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.svalero.game.Felda;
import com.svalero.game.managers.ResourceManager;

import static com.svalero.game.utils.Constants.SCREEN_HEIGHT;

public class PrologueScreen implements Screen {

    private Texture prologueTexture;
    private Image prologueImage;
    private Stage stage;
    private boolean prologueDone = false;

    private Felda game;

    public PrologueScreen(Felda game) {
        this.game = game;

        prologueTexture = new Texture(Gdx.files.internal("prologue.png"));
        prologueImage = new Image(prologueTexture);
        stage = new Stage();
    }

    @Override
    public void show() {

        Table table = new Table();
        table.setFillParent(true);
        table.center();

        prologueImage.addAction(Actions.sequence(Actions.alpha(0), Actions.fadeIn(1f),
                Actions.delay(1.5f), Actions.run(new Runnable() {
                    @Override
                    public void run() {
                        prologueDone = true;
                    }
                })
        ));

        table.row().height(SCREEN_HEIGHT);
        table.add(prologueImage).center();
        stage.addActor(table);

        ResourceManager.loadAllResources();
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0.2f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        stage.act();
        stage.draw();

        if (Gdx.input.isKeyJustPressed(Input.Keys.ANY_KEY)) {
            prologueDone = true;
        }

        if (ResourceManager.update()) {
            if (prologueDone) {
                game.setScreen(new GameScreen(game));
            }
        }

    }

    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {
        dispose();
    }

    @Override
    public void dispose() {
        prologueTexture.dispose();
        stage.dispose();
    }
}
