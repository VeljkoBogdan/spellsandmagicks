package io.github.illuminatijoe.spellsandmagicks.game.entities.projectiles;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import io.github.illuminatijoe.spellsandmagicks.game.entities.components.LifetimeComponent;
import io.github.illuminatijoe.spellsandmagicks.game.entities.components.PositionComponent;

public class FireballMovingSystem extends IteratingSystem {
    private final ComponentMapper<PositionComponent> positionMapper = ComponentMapper.getFor(PositionComponent.class);
    private final ComponentMapper<FireballComponent> fireballMapper = ComponentMapper.getFor(FireballComponent.class);
    private final ComponentMapper<LifetimeComponent> lifetimeMapper = ComponentMapper.getFor(LifetimeComponent.class);

    public FireballMovingSystem() {
        super(Family.all(FireballComponent.class, PositionComponent.class).get());
    }

    @Override
    protected void processEntity(Entity entity, float v) {
        PositionComponent positionComponent = positionMapper.get(entity);
        FireballComponent fireballComponent = fireballMapper.get(entity);
        LifetimeComponent lifetimeComponent = lifetimeMapper.get(entity);

        positionComponent.position.mulAdd(fireballComponent.direction, fireballComponent.speed * v);

        if (lifetimeComponent.lifetime <= 0f) {
            getEngine().removeEntity(entity);
        } else {
            lifetimeComponent.lifetime -= v;
        }
    }
}
