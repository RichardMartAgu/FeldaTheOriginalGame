package com.svalero.game.managers;

import com.badlogic.gdx.graphics.OrthographicCamera;

import static com.svalero.game.utils.Constants.*;

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
        camera.setToOrtho(false, TILES_IN_CAMERA * TILE_WIDTH, TILES_IN_CAMERA * TILE_HEIGHT);
        camera.update();
    }

    public void handleCamera() {
        // Fija la cámara para seguir al personaje en el centro de la pantalla y altura fija (eje y)
        if (spriteManager.player.getPosition().x < TILES_IN_CAMERA * TILE_WIDTH / 2)
            camera.position.set(TILES_IN_CAMERA * TILE_WIDTH / 2 , TILES_IN_CAMERA * TILE_HEIGHT / 2, 0);
        else
            camera.position.set(spriteManager.player.getPosition().x, TILES_IN_CAMERA * TILE_HEIGHT / 2, 0);


        camera.update();
        levelManager.mapRenderer.setView(camera);
        levelManager.mapRenderer.render(new int[]{0, 1,2,3});
    }
    public OrthographicCamera getCamera() {
        return camera;
    }

}
