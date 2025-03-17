package io.github.illuminatijoe.spellsandmagicks.game.entities.systems;

import com.badlogic.ashley.core.*;
import com.badlogic.ashley.utils.ImmutableArray;
import io.github.illuminatijoe.spellsandmagicks.game.entities.components.HealthComponent;

public class HealthSystem extends EntitySystem {
    private ComponentMapper<HealthComponent> hm = ComponentMapper.getFor(HealthComponent.class);
    private Engine engine;
    private ImmutableArray<Entity> entities;

    @Override
    public void addedToEngine(Engine engine) {
        this.engine = engine;
        entities = engine.getEntitiesFor(Family.all(HealthComponent.class).get());
    }

    @Override
    public void update(float deltaTime) {
        for (Entity entity : entities) {
            HealthComponent healthComponent = hm.get(entity);
            healthComponent.updateIFrames(deltaTime);
            if (healthComponent.isDead) {
                engine.removeEntity(entity);
            }
        }
    }
}
