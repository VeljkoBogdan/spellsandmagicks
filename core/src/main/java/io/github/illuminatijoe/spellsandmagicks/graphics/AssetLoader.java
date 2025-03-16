package io.github.illuminatijoe.spellsandmagicks.graphics;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.tools.texturepacker.TexturePacker;
import com.badlogic.gdx.utils.Disposable;

public class AssetLoader implements Disposable {
    public TextureAtlas textureAtlas;
    public final AssetManager assetManager;
    public static Animation<TextureRegion> playerAnimation;
    public static Animation<TextureRegion> slimeAnimation;

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
            textureAtlas.findRegions("wizard_walk"),
            Animation.PlayMode.LOOP
        );
        slimeAnimation = new Animation<>(
            0.1f,
            textureAtlas.findRegions("slime_walk"),
            Animation.PlayMode.LOOP
        );

        System.out.println("Loaded animations!");
    }

    public Animation<TextureRegion> getPlayerAnimation() {
        return playerAnimation;
    }

    private void createAtlases() {
        packTextures();

        textureAtlas = new TextureAtlas("textures/entities/entities.atlas");
        System.out.println("Atlases created!");
    }

    private void packTextures() {
        TexturePacker.process(
            "textures/entities",
            "textures/entities",
            "entities"
        );

        System.out.println("Textures packed!");
    }

    @Override
    public void dispose() {
        textureAtlas.dispose();
        assetManager.dispose();
    }

    public Animation<TextureRegion> getSlimeAnimation() {
        return slimeAnimation;
    }
}
