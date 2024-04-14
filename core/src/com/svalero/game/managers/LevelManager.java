package com.svalero.game.managers;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.maps.MapLayer;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Vector2;
import com.svalero.game.characters.Player;
import com.svalero.game.items.CollisionObject;

import java.util.ArrayList;
import java.util.List;

public class LevelManager {

    private static final String[] LEVELS = {
            "levels/casa_felda.tmx",
            "levels/otro_nivel.tmx"
    };

    private int currentLevelIndex = 0;

    public TiledMap map;
    public TiledMapTileLayer collisionLayer;
    private List<CollisionObject> collisionObjects;
    public OrthogonalTiledMapRenderer mapRenderer;
    public Batch batch;
    private SpriteManager spriteManager;
    private CameraManager cameraManager;

    public LevelManager(SpriteManager spriteManager) {
        this.spriteManager = spriteManager;
    }

    public void setCameraManager(CameraManager cameraManager) {
        this.cameraManager = cameraManager;
    }

    public void loadCurrentLevel() {
        String levelFile = LEVELS[currentLevelIndex];
        map = new TmxMapLoader().load(levelFile);

        mapRenderer = new OrthogonalTiledMapRenderer(map);

        batch = mapRenderer.getBatch();

        batch.begin();

        spriteManager.player = new Player(new Vector2(30, 10), 3, "idle_down");
        spriteManager.player.position.set(40, 20);

        batch.end();

        loadCollisionLayer();
    }

    public void loadCollisionLayer() {
        collisionObjects = new ArrayList<>();
        MapLayer objectLayer = map.getLayers().get("collision");
        for (MapObject object : objectLayer.getObjects()) {
            if (object instanceof RectangleMapObject) {
                RectangleMapObject rectangleObject = (RectangleMapObject) object;
                float x = rectangleObject.getRectangle().x;
                float y = rectangleObject.getRectangle().y;
                float width = rectangleObject.getRectangle().width;
                float height = rectangleObject.getRectangle().height;
                collisionObjects.add(new CollisionObject(x, y, width, height));
            }
        }

    }
    public List<CollisionObject> getCollisionObjects() {
        return collisionObjects;
    }

    public void nextLevel() {
        currentLevelIndex++;
        if (currentLevelIndex >= LEVELS.length) {
            currentLevelIndex = 0; // Vuelve al primer nivel si se llega al final de la lista
        }
        unloadCurrentLevel();
        loadCurrentLevel();
    }

    public void restartGame() {
        currentLevelIndex = 0;

        unloadCurrentLevel();
        loadCurrentLevel();
    }

    private void unloadCurrentLevel() {
        // Liberar recursos, por ejemplo, puedes establecer los objetos a null
        map.dispose();
        mapRenderer.dispose();
        batch.dispose();
        spriteManager.player = null; // Eliminar referencia al jugador para permitir que el recolector de basura lo elimine
        collisionLayer = null;
    }
}
