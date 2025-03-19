package io.github.illuminatijoe.spellsandmagicks.game.entities.systems;

import com.badlogic.ashley.core.*;
import com.badlogic.ashley.utils.ImmutableArray;
import io.github.illuminatijoe.spellsandmagicks.game.entities.components.EnemyComponent;
import io.github.illuminatijoe.spellsandmagicks.game.entities.components.ExplosionComponent;
import io.github.illuminatijoe.spellsandmagicks.game.entities.components.HealthComponent;
import io.github.illuminatijoe.spellsandmagicks.game.entities.components.PositionComponent;

public class ExplosionSystem extends EntitySystem {
    private ImmutableArray<Entity> entities;
    private final ComponentMapper<PositionComponent> positionMapper = ComponentMapper.getFor(PositionComponent.class);
    private final ComponentMapper<ExplosionComponent> explosionMapper = ComponentMapper.getFor(ExplosionComponent.class);
    private final ComponentMapper<HealthComponent> healthMapper = ComponentMapper.getFor(HealthComponent.class);

    @Override
    public void addedToEngine(Engine engine) {
        entities = engine.getEntitiesFor(Family.all(ExplosionComponent.class, PositionComponent.class).get());
    }

    @Override
    public void update(float deltaTime) {
        for (Entity explosionEntity : entities) {
            ExplosionComponent explosion = explosionMapper.get(explosionEntity);
            PositionComponent explosionPos = positionMapper.get(explosionEntity);

            if (!explosion.hasDamaged) {
                boolean hasDamagedAnyone = false;
                for (Entity enemy : getEngine().getEntitiesFor(Family.all(EnemyComponent.class, PositionComponent.class, HealthComponent.class).get())) {
                    PositionComponent enemyPos = positionMapper.get(enemy);
                    HealthComponent health = healthMapper.get(enemy);

                    if (isWithinRange(explosionPos, enemyPos, explosion.radius)) {
                        health.decreaseHealth(explosion.damage);
                        hasDamagedAnyone = true;
                    }
                }

                explosion.hasDamaged = hasDamagedAnyone;
            }

            if (explosion.lifetime <= 0f) {
                getEngine().removeEntity(explosionEntity);
            } else {
                explosion.lifetime -= deltaTime;
            }
        }
    }

    private boolean isWithinRange(PositionComponent a, PositionComponent b, float radius) {
        return a.getPosition().dst(b.getPosition()) <= radius;
    }
}

