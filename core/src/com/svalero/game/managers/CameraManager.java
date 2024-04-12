package com.svalero.game.managers;

import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.dongbat.jbump.util.MathUtils;
import com.svalero.game.screen.LevelManager;

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
    public void update() {
    }

    public void handleCamera() {
        // Fija la cámara para seguir al personaje en el centro de la pantalla y altura fija (eje y)
        if (spriteManager.player.position.x < TILES_IN_CAMERA * TILE_WIDTH / 2)
            camera.position.set(TILES_IN_CAMERA * TILE_WIDTH / 2 , TILES_IN_CAMERA * TILE_HEIGHT / 2, 0);
        else
            camera.position.set(spriteManager.player.position.x, TILES_IN_CAMERA * TILE_HEIGHT / 2, 0);
        //camera.zoom = 1 / 2f;
        camera.update();
        levelManager.mapRenderer.setView(camera);
        // Renderiza las capas 0 (background) y 1 (terrain) según se parametricen en el Tiled
        levelManager.mapRenderer.render(new int[]{0, 1});
    }

}
