package com.svalero.game.managers;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.maps.MapLayer;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.objects.TiledMapTileMapObject;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;
import com.svalero.game.characters.Enemy;
import com.svalero.game.characters.GreenEnemy;
import com.svalero.game.characters.Player;
import com.svalero.game.characters.Sword;
import com.svalero.game.items.CollisionObject;
import com.svalero.game.items.Goal;
import com.svalero.game.items.Item;
import com.svalero.game.items.Rupia;
import com.svalero.game.utils.Constants;

import java.util.ArrayList;
import java.util.List;

public class LevelManager {

    private static final String[] LEVELS = {
            "levels/casa_felda.tmx",
            "levels/otro_nivel.tmx"
    };
    World world;
    private int currentLevelIndex = 0;
    public Array<Item> items;
    public TiledMap map;
    public TiledMapTileLayer collisionLayer;
    private List<CollisionObject> collisionObjects;
    public OrthogonalTiledMapRenderer mapRenderer;
    public Batch batch;
    private SpriteManager spriteManager;
    private CameraManager cameraManager;

    public LevelManager(SpriteManager spriteManager, World world) {
        this.spriteManager = spriteManager;
        this.world = world;
    }
    private void playCurrentLevelMusic() {

        if (ConfigurationManager.isSoundEnabled()) {
            spriteManager.music = ResourceManager.getMusic(Constants.MUSIC + "bso.mp3");
            spriteManager.music.setLooping(true);
            spriteManager.music.setVolume(.2f);
            spriteManager.music.play();

        }
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

        spriteManager.player = new Player(new Vector2(30, 10), 5,world,
                spriteManager.sword = new Sword(world));

        spriteManager.player.getPosition().set(40, 20);

        loadCollisionLayer();
        playCurrentLevelMusic();
        loadEnemies();
        loadItems();
        batch.end();

    }
    public void loadEnemies() {

        Enemy enemy = null;
        // Carga los objetos "enemigo" del TiledMap
        for (MapObject object : map.getLayers().get("objects").getObjects()) {
            if (object instanceof TiledMapTileMapObject) {
                TiledMapTileMapObject tile = (TiledMapTileMapObject) object;
                if (tile.getProperties().containsKey("enemy")) {
                    String enemyType = (String) tile.getProperties().get("enemy");
                    switch (enemyType) {
                        case "green":
                            enemy = new GreenEnemy(new Vector2(tile.getX(), tile.getY()), 2, spriteManager.player,world);
                            break;
                    }
                    spriteManager.enemies.add(enemy);

                }

            }
        }
    }
    private void loadItems() {

        Item item = null;
        // Carga los objetos "enemigo" del TiledMap
        for (MapObject object : map.getLayers().get("objects").getObjects()) {
            if (object instanceof TiledMapTileMapObject) {
                TiledMapTileMapObject tile = (TiledMapTileMapObject) object;
                if (tile.getProperties().containsKey("item")) {
                    String itemType = (String) tile.getProperties().get("item");
                    if (itemType.equals("rupia")) {
                        int score = Integer.parseInt((String) tile.getProperties().get("score"));

                        item = new Rupia(new Vector2(tile.getX(), tile.getY()), score,world);

                    }
                } else if (tile.getProperties().containsKey("goal")) {

                }
            }
        }
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
                collisionObjects.add(new CollisionObject(x, y, width, height,world));
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

        map.dispose();
        mapRenderer.dispose();
        batch.dispose();
        spriteManager.player = null;
        collisionLayer = null;
    }
}
