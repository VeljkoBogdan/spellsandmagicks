package io.github.illuminatijoe.spellsandmagicks.game.entities.systems;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import io.github.illuminatijoe.spellsandmagicks.game.entities.Player;
import io.github.illuminatijoe.spellsandmagicks.game.entities.Slime;
import io.github.illuminatijoe.spellsandmagicks.game.entities.components.ControllableComponent;
import io.github.illuminatijoe.spellsandmagicks.game.entities.components.HealthComponent;
import io.github.illuminatijoe.spellsandmagicks.game.entities.components.PositionComponent;
import io.github.illuminatijoe.spellsandmagicks.graphics.AssetLoader;

public class EntitySpawnerSystem extends IteratingSystem {
    private final OrthographicCamera camera;
    private final AssetLoader assetLoader;
    private final Player player;
    private final ComponentMapper<PositionComponent> pm = ComponentMapper.getFor(PositionComponent.class);
    private final ComponentMapper<HealthComponent> healthMapper = ComponentMapper.getFor(HealthComponent.class);

    public float spawnRate = 1f;
    public float difficultyFactor = 1f;
    public float currentTime = 0f;

    public EntitySpawnerSystem(OrthographicCamera camera, AssetLoader assetLoader, Player player) {
        super(Family.all(PositionComponent.class, ControllableComponent.class).get());
        this.camera = camera;
        this.assetLoader = assetLoader;
        this.player = player;
    }

    @Override
    protected void processEntity(Entity entity, float v) {
        if (currentTime >= spawnRate) {
            spawnEntity(entity, v);
            currentTime -= spawnRate;
        } else {
            currentTime += v;
        }
    }

    public void increaseDifficulty() {
        spawnRate *= 0.9f;
        difficultyFactor *= 1.1f;
    }

    public void spawnEntity(Entity entity, float v) {
        PositionComponent positionComponent = pm.get(entity);
        Vector2 pos = positionComponent.getPosition();

        float xOffset = (camera.viewportWidth * camera.zoom) / 2;
        float yOffset = (camera.viewportHeight * camera.zoom) / 2;

        float leftX = pos.x - xOffset;
        float rightX = pos.x + xOffset;
        float upY = pos.y + yOffset;
        float downY = pos.y - yOffset;

        float margin = 50f;
        Vector2 offScreenPos = getRandomOffScreenPosition(leftX, rightX, downY, upY, margin);

        Slime slime = new Slime(assetLoader, player, offScreenPos);
        healthMapper.get(slime).maxHealth *= difficultyFactor;
        healthMapper.get(slime).health = healthMapper.get(slime).maxHealth;

        getEngine().addEntity(slime);
    }

    public Vector2 getRandomOffScreenPosition(float leftX, float rightX, float downY, float upY, float margin) {
        int side = MathUtils.random(3); // 0 = left, 1 = right, 2 = top, 3 = bottom
        float x, y;

        y = switch (side) {
            case 0 -> { // left
                x = leftX - margin;
                yield MathUtils.random(downY, upY);
            }
            case 1 -> { // right
                x = rightX + margin;
                yield MathUtils.random(downY, upY);
            }
            case 2 -> { // top
                x = MathUtils.random(leftX, rightX);
                yield upY + margin;
            }
            case 3 -> { // bottom
                x = MathUtils.random(leftX, rightX);
                yield downY - margin;
            }
            default -> throw new IllegalStateException("Unexpected value: " + side);
        };

        return new Vector2(x, y);
    }
}
