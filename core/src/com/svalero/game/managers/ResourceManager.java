package com.svalero.game.managers;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.Array;
import com.svalero.game.utils.Constants;

public class ResourceManager {

    private static AssetManager assetManager = new AssetManager();

    public static boolean update() {
        return assetManager.update();
    }
    public static TextureRegion getRegion(String name) {
        return assetManager.get(Constants.TEXTURE_ATLAS, TextureAtlas.class).findRegion(name);
    }
    public static Array<TextureAtlas.AtlasRegion>getRegions(String name) {
        return assetManager.get(Constants.TEXTURE_ATLAS, TextureAtlas.class).findRegions(name);
    }
    public static void loadAllResources() {
        assetManager.load(Constants.TEXTURE_ATLAS, TextureAtlas.class);
    }
}
