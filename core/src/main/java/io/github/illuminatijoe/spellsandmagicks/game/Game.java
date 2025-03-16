package io.github.illuminatijoe.spellsandmagicks.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Disposable;
import io.github.illuminatijoe.spellsandmagicks.game.entities.Player;
import io.github.illuminatijoe.spellsandmagicks.game.entities.components.AnimationComponent;
import io.github.illuminatijoe.spellsandmagicks.game.entities.components.DirectionComponent;
import io.github.illuminatijoe.spellsandmagicks.game.entities.components.PositionComponent;
import io.github.illuminatijoe.spellsandmagicks.graphics.AssetLoader;
import io.github.illuminatijoe.spellsandmagicks.graphics.RenderSystem;

public class Game implements Disposable {
    public AssetLoader assetLoader;
    public RenderSystem renderSystem;
    public Player player;
    public float deltaTime;

    public Game() {
        deltaTime = 0f;

        assetLoader = new AssetLoader(new AssetManager());
        assetLoader.load();

        renderSystem = new RenderSystem();

        player = new Player();
        player.add(new AnimationComponent(assetLoader.getPlayerAnimation()));
        player.add(new PositionComponent(new Vector2(100, 100)));
        player.add(new DirectionComponent(Vector2.Zero));

        RenderSystem.entities.add(player);
    }

    @Override
    public void dispose() {
        assetLoader.dispose();
        renderSystem.dispose();
    }

    public void render() {
        deltaTime = Gdx.graphics.getDeltaTime();

        player.move(deltaTime);

        renderSystem.render();
    }
}
