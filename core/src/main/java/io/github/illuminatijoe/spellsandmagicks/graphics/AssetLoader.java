package io.github.illuminatijoe.spellsandmagicks.graphics;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.tools.texturepacker.TexturePacker;
import com.badlogic.gdx.utils.Disposable;

public class AssetLoader implements Disposable {
    public TextureAtlas entityAtlas;
    public TextureAtlas projectileAtlas;
    public final AssetManager assetManager;
    public static Animation<TextureRegion> playerAnimation;
    public static Animation<TextureRegion> slimeAnimation;
    public static Animation<TextureRegion> fireballAnimation;
    public static Animation<TextureRegion> toxipoolAnimation;
    public static Animation<TextureRegion> toxipoolPoolAnimation;
    public static Animation<TextureRegion> electricityAuraAnimation;

    public AssetLoader(AssetManager assetManager) {
        this.assetManager = assetManager;
    }

    public void load() {
        createAtlases();
        loadAnimations();

        System.out.println("Assets loaded!");
    }

    private void loadAnimations() {
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

        System.out.println("Loaded animations!");
    }

    public Animation<TextureRegion> getPlayerAnimation() {
        return playerAnimation;
    }

    private void createAtlases() {
        packTextures();

        entityAtlas = new TextureAtlas("textures/entities/entities.atlas");
        projectileAtlas = new TextureAtlas("textures/projectiles/projectiles.atlas");
        System.out.println("Atlases created!");
    }

    private void packTextures() {
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

    @Override
    public void dispose() {
        entityAtlas.dispose();
        projectileAtlas.dispose();
        assetManager.dispose();
    }

    public Animation<TextureRegion> getSlimeAnimation() {
        return slimeAnimation;
    }
}
