package io.github.illuminatijoe.spellsandmagicks.game;

import com.badlogic.ashley.core.Engine;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Disposable;
import io.github.illuminatijoe.spellsandmagicks.game.entities.Player;
import io.github.illuminatijoe.spellsandmagicks.game.entities.systems.CameraSystem;
import io.github.illuminatijoe.spellsandmagicks.game.entities.systems.EnemyMovementSystem;
import io.github.illuminatijoe.spellsandmagicks.game.entities.Slime;
import io.github.illuminatijoe.spellsandmagicks.game.entities.components.*;
import io.github.illuminatijoe.spellsandmagicks.game.entities.systems.MovementSystem;
import io.github.illuminatijoe.spellsandmagicks.game.entities.systems.PlayerControllerSystem;
import io.github.illuminatijoe.spellsandmagicks.graphics.AssetLoader;
import io.github.illuminatijoe.spellsandmagicks.graphics.RenderSystem;

public class Game implements Disposable {
    public Engine engine;
    public AssetLoader assetLoader;
    public RenderSystem renderSystem;
    public Player player;
    public float deltaTime = 0f;
    public OrthographicCamera camera;

    public Game() {
        camera = new OrthographicCamera();
        camera.setToOrtho(false, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        camera.update();

        // Load assets
        assetLoader = new AssetLoader(new AssetManager());
        assetLoader.load();

        player = new Player();
        player.add(new AnimationComponent(assetLoader.getPlayerAnimation()));
        player.add(new PositionComponent(new Vector2(100, 100)));
        player.add(new VelocityComponent());
        player.add(new ControllableComponent());
        player.add(new CameraFollowComponent());

        Slime slime = new Slime();
        slime.add(new AnimationComponent(assetLoader.getSlimeAnimation()));
        slime.add(new PositionComponent(new Vector2(500, 500)));
        slime.add(new VelocityComponent());
        slime.add(new TargetComponent(player));

        // Init engine and systems
        engine = new Engine();
        engine.addSystem(new EnemyMovementSystem());
        engine.addSystem(new RenderSystem(camera));
        engine.addSystem(new MovementSystem());
        engine.addSystem(new PlayerControllerSystem());
        engine.addSystem(new CameraSystem(camera));

        engine.addEntity(player);
        engine.addEntity(slime);

    }

    @Override
    public void dispose() {
        assetLoader.dispose();
        renderSystem.dispose();
    }

    public void render() {
        deltaTime = Gdx.graphics.getDeltaTime();

        engine.update(deltaTime);
    }
}
