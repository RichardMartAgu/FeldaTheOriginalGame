package com.svalero.game.screen;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.kotcrab.vis.ui.VisUI;
import com.kotcrab.vis.ui.widget.VisCheckBox;
import com.svalero.game.Felda;
import com.svalero.game.managers.ConfigurationManager;
import com.svalero.game.managers.SpriteManager;

public class PauseGameScreen extends ScreenAdapter {


    final Felda game;
    private Skin skin;
    private Stage stage;

    private GameScreen gameScreen;
    private SpriteManager spriteManager;

    public PauseGameScreen(Felda game, GameScreen gameScreen,SpriteManager spriteManager) {
        this.game = game;
        this.gameScreen = gameScreen;
        this.spriteManager = spriteManager;
        skin = new Skin(Gdx.files.internal("menu/glassy-ui.json"));
        stage = new Stage();

        if (!VisUI.isLoaded()) {
            VisUI.load();
        }


        Table table = new Table();
        table.setFillParent(true);
        stage.addActor(table);

        Label titleLabel = new Label("Pause", skin, "big");
        table.add(titleLabel).expandX().center().top().padBottom(70f).colspan(2).row();
        titleLabel.setFontScale(1.7f);

        TextButton playButton = new TextButton("Continue", skin);
        playButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                dispose();
                ((Game) Gdx.app.getApplicationListener()).setScreen(gameScreen);
                spriteManager.quitPause();

            }
        });

        final VisCheckBox checkSound = new VisCheckBox("SOUND");
        checkSound.setChecked(ConfigurationManager.isSoundEnabled());
        checkSound.addListener(new ClickListener() {
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                // Cambiar el estado de la música según el estado del CheckBox
                boolean isChecked = checkSound.isChecked();
                ConfigurationManager.switchMusic(isChecked);
            }
        });

        Button menuButton = new TextButton("Main menu", skin);
        menuButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                dispose();
                ((Game) Gdx.app.getApplicationListener()).setScreen(new MainMenuScreen(game));
            }
        });

        TextButton exitButton = new TextButton("Exit", skin);
        exitButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                Gdx.app.exit();
            }
        });

        table.add(playButton).center().padBottom(20).colspan(2).row();
        table.add(menuButton).center().padBottom(20).colspan(2).row();
        table.add(exitButton).center().padBottom(20).colspan(2).row();
        table.add(checkSound).center().colspan(2);
    }

    @Override
    public void show() {
        Gdx.input.setInputProcessor(stage);
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0.2f, 0.2f, 0.2f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        stage.act(Math.min(Gdx.graphics.getDeltaTime(), 1 / 30f));
        stage.draw();
    }

    @Override
    public void resize(int width, int height) {
    }

    @Override
    public void hide() {
        stage.clear();
    }

    @Override
    public void dispose() {
        stage.dispose();
        skin.dispose();
    }
}

