package com.svalero.game.managers;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.Array;
import com.svalero.game.utils.Constants;

import java.io.File;

public class ResourceManager {

    private static AssetManager assets = new AssetManager();

    public static boolean update() {
        return assets.update();
    }
    public static TextureRegion getRegion(String name) {
        return assets.get(Constants.TEXTURE_ATLAS, TextureAtlas.class).findRegion(name);
    }
    public static Array<TextureAtlas.AtlasRegion>getRegions(String name) {
        return assets.get(Constants.TEXTURE_ATLAS, TextureAtlas.class).findRegions(name);
    }
    public static void loadAllResources() {
        assets.load(Constants.TEXTURE_ATLAS, TextureAtlas.class);

        loadSounds();
        loadMusics();
    }
    public static void loadSounds() {

        assets.load(Constants.SOUND + "sword.mp3", Sound.class);
        assets.load(Constants.SOUND + "shot.mp3", Sound.class);
        assets.load(Constants.SOUND + "hurt_bubble.mp3", Sound.class);
        assets.load(Constants.SOUND + "die_bubble.mp3", Sound.class);
        assets.load(Constants.SOUND + "collect_heart.mp3", Sound.class);
        assets.load(Constants.SOUND + "collect_rupia.mp3", Sound.class);
        assets.load(Constants.SOUND + "hurt.mp3", Sound.class);
        assets.load(Constants.SOUND + "die.mp3", Sound.class);
        assets.load(Constants.SOUND + "lose.mp3", Sound.class);

    }
    public static void loadMusics() {
        assets.load(Constants.MUSIC +"zelda_music.mp3", Music.class);

    }
    public static Sound getSound(String name) {
        return assets.get(name, Sound.class);
    }
    public static Music getMusic(String name) {
        return assets.get(name, Music.class);
    }

}
