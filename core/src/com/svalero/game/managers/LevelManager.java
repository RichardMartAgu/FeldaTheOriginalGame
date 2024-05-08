package com.svalero.game.managers;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.maps.MapLayer;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.objects.TiledMapTileMapObject;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;
import com.svalero.game.characters.*;
import com.svalero.game.items.CollisionObject;
import com.svalero.game.items.Goal;
import com.svalero.game.items.Item;
import com.svalero.game.items.Rupia;
import com.svalero.game.screen.WinScreen;
import com.svalero.game.utils.Constants;

import java.util.ArrayList;
import java.util.List;

import static com.svalero.game.screen.GameScreen.game;

public class LevelManager {

    public static final String[] LEVELS = {
            "levels/casa_felda.tmx",
            "levels/bosque.tmx",
            "levels/ladera_nevada.tmx",
            "levels/cima_montaña.tmx"

    };
    World world;
    public int currentLevelIndex = 0;
    public Array<Item> items;
    public TiledMap map;
    private List<CollisionObject> collisionObjects;
    public Array<Body> worldBodies;
    public OrthogonalTiledMapRenderer mapRenderer;
    public Batch batch;
    private SpriteManager spriteManager;
    private CameraManager cameraManager;

    public LevelManager(SpriteManager spriteManager, World world) {
        this.spriteManager = spriteManager;
        this.world = world;
        this.worldBodies = new Array<>();
        this.items = new Array<>();

    }


    private void playCurrentLevelMusic() {
        //Carga la música
        if (ConfigurationManager.isSoundEnabled()) {
            spriteManager.music = ResourceManager.getMusic(Constants.MUSIC + "zelda_music.mp3");
            spriteManager.music.setLooping(true);
            spriteManager.music.setVolume(.2f);
            spriteManager.music.play();
        }
    }

    public void loadCurrentLevel() {
        //Carga el nivel que toca
        String levelFile = LEVELS[currentLevelIndex];

        map = new TmxMapLoader().load(levelFile);

        mapRenderer = new OrthogonalTiledMapRenderer(map);

        batch = mapRenderer.getBatch();

        //Dependiendo del nivel pone el jugador en un inicio recuperando rupias y corazones
        if (currentLevelIndex == 0) {
            spriteManager.player = new Player(new Vector2(140, 180), 5, 0, world, spriteManager.sword = new Sword(world));
        } else if (currentLevelIndex == 1) {
            spriteManager.player = new Player(new Vector2(10, 120), spriteManager.player.currentHearts, spriteManager.player.rupias, world, spriteManager.sword = new Sword(world));
        } else if (currentLevelIndex == 2) {
            spriteManager.player = new Player(new Vector2(10, 70), spriteManager.player.currentHearts, spriteManager.player.rupias, world, spriteManager.sword = new Sword(world));
        } else if (currentLevelIndex == 3) {
            spriteManager.player = new Player(new Vector2(20, 50), spriteManager.player.currentHearts, spriteManager.player.rupias, world, spriteManager.sword = new Sword(world));
        }
        loadCollisionLayer();
        playCurrentLevelMusic();
        loadItems();
        loadEnemies();

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
                            enemy = new GreenEnemy(new Vector2(tile.getX(), tile.getY()), 2, spriteManager.player, world);
                            break;
                        case "gray":
                            enemy = new GrayEnemy(new Vector2(tile.getX(), tile.getY()), 2, spriteManager.player, world);
                            break;
                        case "blue":
                            enemy = new BlueEnemy(new Vector2(tile.getX(), tile.getY()), 5, spriteManager.player, world);
                            break;
                    }
                    spriteManager.enemies.add(enemy);
                }
            }
        }
    }

    private void loadItems() {
        Item item = null;
        // Carga los objetos "item" del TiledMap
        for (MapObject object : map.getLayers().get("objects").getObjects()) {
            if (object instanceof TiledMapTileMapObject) {
                TiledMapTileMapObject tile = (TiledMapTileMapObject) object;
                if (tile.getProperties().containsKey("item")) {
                    String itemType = (String) tile.getProperties().get("item");
                    switch (itemType) {
                        case "rupia":
                            item = new Rupia(tile.getX(), tile.getY(), 1);
                            break;
                        case "goal":
                            item = new Goal(tile.getX(), tile.getY());
                            break;
                    }
                    spriteManager.items.add(item);
                }
            }
        }
    }

    public void loadCollisionLayer() {
        // Carga los collision del TiledMap
        collisionObjects = new ArrayList<>();
        MapLayer objectLayer = map.getLayers().get("collision");
        for (MapObject object : objectLayer.getObjects()) {
            if (object instanceof RectangleMapObject) {
                RectangleMapObject rectangleObject = (RectangleMapObject) object;
                float x = rectangleObject.getRectangle().x;
                float y = rectangleObject.getRectangle().y;
                float width = rectangleObject.getRectangle().width;
                float height = rectangleObject.getRectangle().height;
                collisionObjects.add(new CollisionObject(x, y, width, height, world));
            }
        }
    }

    public void nextLevel() {
        //Cambia de nivel
        currentLevelIndex++;
        if (currentLevelIndex >= LEVELS.length) {
            currentLevelIndex = 0;
            ResourceManager.getSound(Constants.SOUND + "win.mp3").play();
            ((Game) Gdx.app.getApplicationListener()).setScreen(new WinScreen(game));
        } else {
            unloadCurrentLevel();
            loadCurrentLevel();
        }
    }

    public void unloadCurrentLevel() {
        //libera recursos del nivel anterior
        spriteManager.music.stop();
        cameraManager.init();
        spriteManager.enemies.clear();
        spriteManager.items.clear();
        collisionObjects.clear();
        world.getBodies(worldBodies);
        for (Body body : worldBodies) {
            world.destroyBody(body);
        }
        batch.dispose();
        map.dispose();

    }

    public void setCameraManager(CameraManager cameraManager) {
        this.cameraManager = cameraManager;
    }
}
