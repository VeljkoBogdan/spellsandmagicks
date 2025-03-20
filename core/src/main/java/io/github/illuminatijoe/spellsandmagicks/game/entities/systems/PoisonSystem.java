package io.github.illuminatijoe.spellsandmagicks.game.entities.systems;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import io.github.illuminatijoe.spellsandmagicks.game.entities.components.HealthComponent;
import io.github.illuminatijoe.spellsandmagicks.game.entities.components.PoisonFlashComponent;
import io.github.illuminatijoe.spellsandmagicks.game.entities.components.PoisonedComponent;

public class PoisonSystem extends IteratingSystem {
    private final ComponentMapper<PoisonedComponent> poisonedMapper = ComponentMapper.getFor(PoisonedComponent.class);
    private final ComponentMapper<HealthComponent> healthMapper = ComponentMapper.getFor(HealthComponent.class);

    public PoisonSystem() {
        super(Family.all(PoisonedComponent.class, HealthComponent.class).get());
    }

    @Override
    protected void processEntity(Entity entity, float v) {
        PoisonedComponent poisonedComponent = poisonedMapper.get(entity);
        HealthComponent healthComponent = healthMapper.get(entity);

        if (poisonedComponent.shouldDealDamage(v)) {
            healthComponent.decreaseHealth(poisonedComponent.amount);
            entity.add(new PoisonFlashComponent());
        }

        if (poisonedComponent.isOver(v)) entity.remove(PoisonedComponent.class);
    }
}
