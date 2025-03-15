package io.github.illuminatijoe.spellsandmagicks.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.Disposable;
import com.badlogic.gdx.utils.ScreenUtils;
import io.github.illuminatijoe.spellsandmagicks.game.entities.Player;
import io.github.illuminatijoe.spellsandmagicks.game.entities.components.AnimationComponent;
import io.github.illuminatijoe.spellsandmagicks.graphics.AssetLoader;

public class Game implements Disposable {
    public AssetLoader assetLoader;
    public Player player;

    // temp
    public SpriteBatch spriteBatch;

    public Game() {
        assetLoader = new AssetLoader(new AssetManager());
        assetLoader.load();

        player = new Player();
        player.add(new AnimationComponent(assetLoader.getPlayerAnimation()));

        // temp
        spriteBatch = new SpriteBatch();
    }

    @Override
    public void dispose() {
        assetLoader.dispose();
        //temp
        spriteBatch.dispose();
    }

    public void render() {
        ScreenUtils.clear(Color.BLACK);
        spriteBatch.begin();

        // temp
            AnimationComponent animation = player.getComponent(AnimationComponent.class);

            float deltaTime = Gdx.graphics.getDeltaTime();
            if (animation != null) {
                animation.stateTime += deltaTime;
                TextureRegion currentFrame = animation.getKeyFrame();

                spriteBatch.draw(currentFrame, 100, 100);
            }
        spriteBatch.end();
    }
}
