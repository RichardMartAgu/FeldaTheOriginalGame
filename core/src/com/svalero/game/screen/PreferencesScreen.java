package com.svalero.game.screen;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.kotcrab.vis.ui.VisUI;
import com.kotcrab.vis.ui.widget.VisCheckBox;
import com.svalero.game.Felda;
import com.svalero.game.managers.ConfigurationManager;
import com.svalero.game.managers.SpriteManager;
import com.svalero.game.utils.Constants;

public class PreferencesScreen implements Screen {

    final Felda game;
    private Skin skin;
    private Stage stage;



    public PreferencesScreen(Felda game) {
        this.game = game;

        if (!VisUI.isLoaded()) {
            VisUI.load();
        }

        loadPreferences();

        skin = new Skin(Gdx.files.internal("menu/glassy-ui.json"));
        stage = new Stage();

        Table table = new Table();
        table.setFillParent(true);
        stage.addActor(table);

        Label titleLabel = new Label("SETTINGS", skin, "big");
        table.add(titleLabel).expandX().center().top().padBottom(150f).colspan(2).row();
        titleLabel.setFontScale(1.5f);

        final VisCheckBox checkSound = new VisCheckBox("SOUND");
        checkSound.setChecked(ConfigurationManager.isSoundEnabled());
        checkSound.addListener(new ClickListener() {
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                // Cambiar el estado de la música según el estado del CheckBox
                boolean isChecked = checkSound.isChecked();
                ConfigurationManager.switchMusic(isChecked);
            }
        });

        final SelectBox<String> difficultySelectBox = new SelectBox<>(skin);
        difficultySelectBox.setItems("Easy", "Medium", "Hard");
        difficultySelectBox.setSelected(ConfigurationManager.getDifficulty());
        difficultySelectBox.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                // Guardar la dificultad seleccionada en las preferencias
                String selectedDifficulty = difficultySelectBox.getSelected();
                ConfigurationManager.setDifficulty(selectedDifficulty);
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

        table.add(menuButton).center().padBottom(40).colspan(2).row();
        table.add(difficultySelectBox).center().padBottom(40).colspan(2).row();
        table.add(checkSound).center().padBottom(20).colspan(2).row();

        Gdx.input.setInputProcessor(stage);
    }


    private void loadPreferences() {
    }

    @Override
    public void show() {

        loadPreferences();

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
    public void pause() {

    }

    @Override
    public void resume() {

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
