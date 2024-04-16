package com.svalero.game.screen;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.svalero.game.Felda;
import com.svalero.game.utils.Constants;

public class PreferencesScreen implements Screen {

    final Felda game;
    private Skin skin;
    private Stage stage;

    Preferences settings;

    public PreferencesScreen(Felda game) {
        this.game = game;

        loadPreferences();

        skin = new Skin(Gdx.files.internal("menu/glassy-ui.json"));
        stage = new Stage();

        Table table = new Table();
        table.setFillParent(true);
        stage.addActor(table);

        Label titleLabel = new Label("SETTINGS", skin, "big");
        table.add(titleLabel).expandX().center().top().padBottom(150f).colspan(2).row();
        titleLabel.setFontScale(1.5f);

        final CheckBox checkSound = new CheckBox(" SOUND", skin);
        checkSound.setChecked(settings.getBoolean("sound"));
        checkSound.getLabel().setFontScale(1.5f); // Escala el texto del CheckBox
        checkSound.setSize(200f, 50f);
        checkSound.addListener(new ClickListener() {
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                settings.putBoolean("sound", checkSound.isChecked());
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
        table.add(checkSound).center().padBottom(20).colspan(2).row();
    }

    private void loadPreferences() {

        settings = Gdx.app.getPreferences(Constants.APP_NAME);

        // Coloca los valores por defecto (para la primera ejecución)
        if (!settings.contains("sound"))
            settings.putBoolean("sound", true);
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
