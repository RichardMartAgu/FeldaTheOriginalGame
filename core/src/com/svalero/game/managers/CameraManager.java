package com.svalero.game.managers;

import com.badlogic.gdx.graphics.OrthographicCamera;

import static com.svalero.game.utils.Constants.CAMERA_HEIGHT;
import static com.svalero.game.utils.Constants.CAMERA_WIDTH;

public class CameraManager {
    SpriteManager spriteManager;
    LevelManager levelManager;
    OrthographicCamera camera;

    public CameraManager(SpriteManager spriteManager, LevelManager levelManager) {
        this.spriteManager = spriteManager;
        this.levelManager = levelManager;

        init();
    }

    public void init() {
        // Crea una cámara y muestra 16x16 unidades del mundo
        camera = new OrthographicCamera();
        camera.setToOrtho(false, CAMERA_WIDTH, CAMERA_HEIGHT);
        camera.update();
    }

    public void handleCamera() {

        //Definir los límites del mapa para cada nivel
        float levelWith = 0;
        if (levelManager.currentLevelIndex == 0)
            levelWith = 320;
        else if (levelManager.currentLevelIndex == 1)
            levelWith = 1120;
        else if (levelManager.currentLevelIndex == 2)
            levelWith = 640;
        else if (levelManager.currentLevelIndex == 3)
            levelWith = 480;

        if (spriteManager.player.getPosition().x < CAMERA_WIDTH / 2f)
            camera.position.set(CAMERA_WIDTH / 2f, CAMERA_HEIGHT / 2f, 0);
        else if (spriteManager.player.getPosition().x > levelWith - CAMERA_WIDTH / 2f)
            // Si el jugador está cerca del borde derecho, centrar la cámara en el lado derecho de la pantalla
            camera.position.set(levelWith - CAMERA_WIDTH / 2f, CAMERA_HEIGHT / 2f, 0);
        else
            camera.position.set(spriteManager.player.getPosition().x, CAMERA_HEIGHT / 2f, 0);

        camera.update();
        levelManager.mapRenderer.setView(camera);
        levelManager.mapRenderer.render(new int[]{0, 1, 2, 3});
    }

}
