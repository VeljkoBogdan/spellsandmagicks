package io.github.illuminatijoe.spellsandmagicks.game;

import com.badlogic.ashley.core.Engine;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.utils.Disposable;
import io.github.illuminatijoe.spellsandmagicks.Main;
import io.github.illuminatijoe.spellsandmagicks.game.entities.Player;
import io.github.illuminatijoe.spellsandmagicks.game.entities.systems.*;
import io.github.illuminatijoe.spellsandmagicks.graphics.AssetLoader;
import io.github.illuminatijoe.spellsandmagicks.graphics.RenderSystem;

public class Game implements Disposable {
    public boolean paused = false;
    public Engine engine;
    public AssetLoader assetLoader;
    public RenderSystem renderSystem;
    public Player player;
    public float deltaTime = 0f;
    public OrthographicCamera camera;
    public TileRendererSystem tileRendererSystem;
    private Main main;

    public Game(Main main) {
        this.main = main;
        camera = new OrthographicCamera();
        camera.setToOrtho(false, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        camera.zoom *= 1.5f;
        camera.update();

        // Load assets
        assetLoader = new AssetLoader(new AssetManager());
        assetLoader.load();

        player = new Player(assetLoader, this);

        // Init engine and systems
        engine = new Engine();
        tileRendererSystem = new TileRendererSystem(camera);
        engine.addSystem(tileRendererSystem);
        engine.addSystem(new EnemyMovementSystem());
        renderSystem = new RenderSystem(camera);
        engine.addSystem(renderSystem);
        engine.addSystem(new MovementSystem());
        engine.addSystem(new PlayerControllerSystem());
        engine.addSystem(new CameraSystem(camera));
        engine.addSystem(new EntitySpawnerSystem(camera, assetLoader, player));
        engine.addSystem(new CollisionSystem(32));
        engine.addSystem(new HealthSystem());

        engine.addEntity(player);
    }

    @Override
    public void dispose() {
        assetLoader.dispose();
        renderSystem.dispose();
        tileRendererSystem.dispose();
    }

    public void render() {
        if (paused) return;

        deltaTime = Gdx.graphics.getDeltaTime();

        engine.update(deltaTime);
    }

    public int getEntityAmount() {
        return engine.getEntities().size();
    }

    public void pause() {
        this.paused = true;
    }

    public void unpause() {
        this.paused = false;
    }

    public void togglePause() {
        paused = !paused;
    }

    public void playerKilled() {
        engine.removeAllEntities();
        this.main.showEndScreen();
    }
}
