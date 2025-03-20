package io.github.illuminatijoe.spellsandmagicks.game;

import com.badlogic.ashley.core.Engine;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.utils.Disposable;
import io.github.illuminatijoe.spellsandmagicks.SpellsAndMagicksGame;
import io.github.illuminatijoe.spellsandmagicks.game.entities.Player;
import io.github.illuminatijoe.spellsandmagicks.game.entities.projectiles.FireballMovingSystem;
import io.github.illuminatijoe.spellsandmagicks.game.entities.projectiles.FireballShootingSystem;
import io.github.illuminatijoe.spellsandmagicks.game.entities.projectiles.ToxipoolPoolSystem;
import io.github.illuminatijoe.spellsandmagicks.game.entities.systems.*;
import io.github.illuminatijoe.spellsandmagicks.graphics.*;
import io.github.illuminatijoe.spellsandmagicks.ui.GameScreen;

import java.util.Arrays;
import java.util.List;

public class Game implements Disposable {
    private final SpellsAndMagicksGame spellsAndMagicksGame;
    private final GameScreen gameScreen;
    public boolean paused = false;
    public Engine engine;
    public Player player;
    public float deltaTime = 0f;
    public OrthographicCamera camera;

    public RenderSystem renderSystem;
    public TileRendererSystem tileRendererSystem;
    public MasterRenderSystem masterRenderSystem;
    public HpRenderSystem hpRenderSystem;
    public ExperienceRenderSystem experienceRenderSystem;

    public EntitySpawnerSystem entitySpawnerSystem;

    public Game(SpellsAndMagicksGame spellsAndMagicksGame, GameScreen gameScreen) {
        this.spellsAndMagicksGame = spellsAndMagicksGame;
        this.gameScreen = gameScreen;
        camera = new OrthographicCamera();
        camera.setToOrtho(false, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        camera.zoom *= 1.0f;
        camera.update();

        // Load assets
        AssetLoader.load();

        player = new Player(this);

        // Init engine and systems
        engine = new Engine();
        // rendering
        tileRendererSystem = new TileRendererSystem(engine);
        renderSystem = new RenderSystem(engine);
        hpRenderSystem = new HpRenderSystem(engine);
        experienceRenderSystem = new ExperienceRenderSystem(engine);
        List<RenderableSystem> renderableSystems = Arrays.asList(tileRendererSystem, renderSystem, hpRenderSystem, experienceRenderSystem);
        masterRenderSystem = new MasterRenderSystem(camera, renderableSystems);
        engine.addSystem(masterRenderSystem);

        engine.addSystem(new EnemyMovementSystem());
        engine.addSystem(new MovementSystem());
        engine.addSystem(new PlayerControllerSystem());
        engine.addSystem(new CameraSystem(camera));

        entitySpawnerSystem = new EntitySpawnerSystem(camera, player);
        engine.addSystem(entitySpawnerSystem);

        engine.addSystem(new CollisionSystem(256, player));
        engine.addSystem(new HealthSystem());
        engine.addSystem(new TimedDetonationSystem());
        engine.addSystem(new ToxipoolPoolSystem());
        engine.addSystem(new FollowPlayerSystem(player));
        engine.addSystem(new FireballMovingSystem());
        engine.addSystem(new FireballShootingSystem());
        engine.addSystem(new RegenSystem());
        engine.addSystem(new DamageFlashSystem());
        engine.addSystem(new PoisonFlashSystem());

        engine.addEntity(player);
    }

    public void render() {
        deltaTime = paused ? 0f : Gdx.graphics.getDeltaTime();

        engine.update(deltaTime);
    }

    public void playerKilled() {
        engine.removeAllEntities();
        Gdx.app.exit();
    }

    @Override
    public void dispose() {
        AssetLoader.disposeStatic();
        tileRendererSystem.dispose();
        hpRenderSystem.dispose();
        masterRenderSystem.dispose();
    }

    public void onLevelUp() {
        gameScreen.onLevelUp();
        entitySpawnerSystem.increaseDifficulty();
    }
}
