package io.github.illuminatijoe.spellsandmagicks.graphics;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.tools.texturepacker.TexturePacker;
import com.badlogic.gdx.utils.Disposable;

public class AssetLoader {
    private static final AssetManager assetManager = new AssetManager();
    private static TextureAtlas entityAtlas;
    private static TextureAtlas projectileAtlas;

    public static Animation<TextureRegion> playerAnimation;
    public static Animation<TextureRegion> slimeAnimation;
    public static Animation<TextureRegion> fireballAnimation;
    public static Animation<TextureRegion> toxipoolAnimation;
    public static Animation<TextureRegion> toxipoolPoolAnimation;
    public static Animation<TextureRegion> electricityAuraAnimation;
    public static Animation<TextureRegion> explosionAnimation;

    private AssetLoader() {
        // Private constructor to prevent instantiation
    }

    public static void load() {
        createAtlases();
        loadAnimations();
        loadSounds();

        System.out.println("Assets loaded!");
    }

    private static void loadSounds() {
        assetManager.load("sounds/explosion.wav", Sound.class);
        assetManager.load("sounds/fireball_hit.wav", Sound.class);
        assetManager.load("sounds/fireball_shoot.wav", Sound.class);
        assetManager.load("sounds/hit.wav", Sound.class);
        assetManager.load("sounds/level_up.wav", Sound.class);
        assetManager.load("sounds/player_hit.wav", Sound.class);
        assetManager.load("sounds/toxipool_shoot.wav", Sound.class);
        assetManager.load("sounds/toxipool_spawn_pool.wav", Sound.class);
        assetManager.load("sounds/aura_hit.wav", Sound.class);
        assetManager.finishLoading(); // Ensure all assets are loaded
    }

    private static void loadAnimations() {
        playerAnimation = new Animation<>(
            0.1f,
            entityAtlas.findRegions("wizard_walk"),
            Animation.PlayMode.LOOP
        );
        slimeAnimation = new Animation<>(
            0.1f,
            entityAtlas.findRegions("slime_walk"),
            Animation.PlayMode.LOOP
        );
        fireballAnimation = new Animation<>(
            0.1f,
            projectileAtlas.findRegions("fireball"),
            Animation.PlayMode.LOOP
        );
        toxipoolAnimation = new Animation<>(
            0.2f,
            projectileAtlas.findRegions("toxipool"),
            Animation.PlayMode.LOOP
        );
        toxipoolPoolAnimation = new Animation<>(
            0.5f,
            projectileAtlas.findRegions("toxipool_pool"),
            Animation.PlayMode.LOOP
        );
        electricityAuraAnimation = new Animation<>(
            0.25f,
            projectileAtlas.findRegions("aura"),
            Animation.PlayMode.LOOP
        );
        explosionAnimation = new Animation<>(
            0.04f,
            projectileAtlas.findRegions("explosion"),
            Animation.PlayMode.NORMAL
        );

        System.out.println("Loaded animations!");
    }

    private static void createAtlases() {
        packTextures();
        entityAtlas = new TextureAtlas("textures/entities/entities.atlas");
        projectileAtlas = new TextureAtlas("textures/projectiles/projectiles.atlas");
        System.out.println("Atlases created!");
    }

    private static void packTextures() {
        TexturePacker.process(
            "textures/entities",
            "textures/entities",
            "entities"
        );

        TexturePacker.process(
            "textures/projectiles",
            "textures/projectiles",
            "projectiles"
        );

        System.out.println("Textures packed!");
    }

    public static void disposeStatic() {
        entityAtlas.dispose();
        projectileAtlas.dispose();
        assetManager.dispose();
    }

    // Static Sound Accessors
    public static Sound getHitSound() {
        return assetManager.get("sounds/hit.wav", Sound.class);
    }

    public static Sound getExplosionSound() {
        return assetManager.get("sounds/explosion.wav", Sound.class);
    }

    public static Sound getFireballHitSound() {
        return assetManager.get("sounds/fireball_hit.wav", Sound.class);
    }

    public static Sound getFireballShootSound() {
        return assetManager.get("sounds/fireball_shoot.wav", Sound.class);
    }

    public static Sound getLevelUpSound() {
        return assetManager.get("sounds/level_up.wav", Sound.class);
    }

    public static Sound getPlayerHitSound() {
        return assetManager.get("sounds/player_hit.wav", Sound.class);
    }

    public static Sound getToxipoolShootSound() {
        return assetManager.get("sounds/toxipool_shoot.wav", Sound.class);
    }

    public static Sound getToxipoolSpawnSound() {
        return assetManager.get("sounds/toxipool_spawn_pool.wav", Sound.class);
    }

    public static Sound getAuraHitSound() {
        return assetManager.get("sounds/aura_hit.wav", Sound.class);
    }
}
