package io.github.illuminatijoe.spellsandmagicks.game;

import com.badlogic.ashley.core.Engine;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.utils.Disposable;
import io.github.illuminatijoe.spellsandmagicks.Main;
import io.github.illuminatijoe.spellsandmagicks.game.entities.Player;
import io.github.illuminatijoe.spellsandmagicks.game.entities.projectiles.FireballMovingSystem;
import io.github.illuminatijoe.spellsandmagicks.game.entities.projectiles.FireballShootingSystem;
import io.github.illuminatijoe.spellsandmagicks.game.entities.systems.*;
import io.github.illuminatijoe.spellsandmagicks.graphics.*;

import java.util.Arrays;
import java.util.List;

public class Game implements Disposable {
    public boolean paused = false;
    public Engine engine;
    public AssetLoader assetLoader;
    public Player player;
    public float deltaTime = 0f;
    public OrthographicCamera camera;
    private final Main main;

    public RenderSystem renderSystem;
    public TileRendererSystem tileRendererSystem;
    public MasterRenderSystem masterRenderSystem;
    public HpRenderSystem hpRenderSystem;
    public ExperienceRenderSystem experienceRenderSystem;

    public Game(Main main) {
        this.main = main;
        camera = new OrthographicCamera();
        camera.setToOrtho(false, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        camera.zoom *= 1.0f;
        camera.update();

        // Load assets
        assetLoader = new AssetLoader(new AssetManager());
        assetLoader.load();

        player = new Player(assetLoader, this);

        // Init engine and systems
        engine = new Engine();
        // rendering
        tileRendererSystem = new TileRendererSystem(engine);
        renderSystem = new RenderSystem(engine);
        hpRenderSystem = new HpRenderSystem(engine);
        experienceRenderSystem = new ExperienceRenderSystem(engine);
//        engine.addSystem(tileRendererSystem);
//        engine.addSystem(renderSystem);
        List<RenderableSystem> renderableSystems = Arrays.asList(tileRendererSystem, renderSystem, hpRenderSystem, experienceRenderSystem);
        masterRenderSystem = new MasterRenderSystem(camera, renderableSystems);
        engine.addSystem(masterRenderSystem);

        engine.addSystem(new EnemyMovementSystem());
        engine.addSystem(new MovementSystem());
        engine.addSystem(new PlayerControllerSystem());
        engine.addSystem(new CameraSystem(camera));
        engine.addSystem(new EntitySpawnerSystem(camera, assetLoader, player));
        engine.addSystem(new CollisionSystem(32, player));
        engine.addSystem(new HealthSystem());
        engine.addSystem(new FireballMovingSystem());
        engine.addSystem(new FireballShootingSystem());

        engine.addEntity(player);
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

    @Override
    public void dispose() {
        assetLoader.dispose();
        tileRendererSystem.dispose();
        hpRenderSystem.dispose();
        masterRenderSystem.dispose();
    }
}
