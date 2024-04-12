package com.svalero.game.screen;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.maps.MapLayer;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Vector2;
import com.svalero.game.characters.Player;
import com.svalero.game.managers.CameraManager;
import com.svalero.game.managers.SpriteManager;
import com.svalero.game.utils.Constants;

public class LevelManager {

    TiledMap map;
    TiledMapTileLayer collisionLayer;
    LevelManager levelManager;
    MapLayer objectLayer;
    public OrthogonalTiledMapRenderer mapRenderer;
    SpriteManager spriteManager;
    CameraManager cameraManager;
    public Batch batch;

    public LevelManager(SpriteManager spriteManager) {
        this.spriteManager = spriteManager;
    }
    public void setCameraManager(CameraManager cameraManager) {
        this.cameraManager = cameraManager;
    }
    public void setLevelManager(LevelManager levelManager) {
        this.levelManager = levelManager;
    }

    public void loadCurrentLevel() {

        map = new TmxMapLoader().load("levels/casa_felda.tmx");
        collisionLayer = (TiledMapTileLayer) map.getLayers().get("terrain");
        objectLayer = map.getLayers().get("objects");
        mapRenderer = new OrthogonalTiledMapRenderer(map);
        batch = mapRenderer.getBatch();
        // Crea el jugador y lo posiciona al inicio de la pantalla
        spriteManager.player = new Player(new Vector2(0, 0),"idel_left");
        // posición inicial del jugador
        spriteManager.player.position.set(0 * Constants.TILE_WIDTH, 10 * Constants.TILE_HEIGHT);

//        loadEnemies();
//        loadItems();
//        playCurrentLevelMusic();
    }
}
