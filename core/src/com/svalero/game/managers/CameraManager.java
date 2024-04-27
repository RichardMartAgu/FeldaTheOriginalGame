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
        float cameraX = spriteManager.player.getPosition().x; // Posición X del jugador
        float cameraY = spriteManager.player.getPosition().y; // Posición Y del jugador
        float halfViewportWidth = camera.viewportWidth / 2; // Mitad del ancho de la cámara
        float halfViewportHeight = camera.viewportHeight / 2; // Mitad de la altura de la cámara
        float levelWidth = TILES_IN_CAMERA * TILE_WIDTH; // Ancho total del nivel en píxeles
        float levelHeight = TILES_IN_CAMERA * TILE_HEIGHT; // Alto total del nivel en píxeles

        // Restringir la cámara en el eje X
        if (cameraX - halfViewportWidth < 0) {
            cameraX = halfViewportWidth; // Ajustar la posición X de la cámara al límite izquierdo
        } else if (cameraX + halfViewportWidth > levelWidth) {
            cameraX = levelWidth - halfViewportWidth; // Ajustar la posición X de la cámara al límite derecho
        }

        // Restringir la cámara en el eje Y
        if (cameraY - halfViewportHeight < 0) {
            cameraY = halfViewportHeight; // Ajustar la posición Y de la cámara al límite superior
        } else if (cameraY + halfViewportHeight > levelHeight) {
            cameraY = levelHeight - halfViewportHeight; // Ajustar la posición Y de la cámara al límite inferior
        }

        // Configurar la nueva posición de la cámara
        camera.position.set(cameraX, cameraY, 0);


        camera.update();
        levelManager.mapRenderer.setView(camera);
        levelManager.mapRenderer.render(new int[]{0, 1,2,3});
    }
    public OrthographicCamera getCamera() {
        return camera;
    }

}
