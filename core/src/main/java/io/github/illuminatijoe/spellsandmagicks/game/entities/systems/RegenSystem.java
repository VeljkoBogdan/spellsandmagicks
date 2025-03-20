package io.github.illuminatijoe.spellsandmagicks.game.entities.systems;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import io.github.illuminatijoe.spellsandmagicks.game.entities.components.HealthComponent;
import io.github.illuminatijoe.spellsandmagicks.game.entities.components.RegenComponent;

public class RegenSystem extends IteratingSystem {
    private final ComponentMapper<RegenComponent> regenMapper = ComponentMapper.getFor(RegenComponent.class);
    private final ComponentMapper<HealthComponent> healthMapper = ComponentMapper.getFor(HealthComponent.class);

    public RegenSystem() {
        super(Family.all(RegenComponent.class, HealthComponent.class).get());
    }

    @Override
    protected void processEntity(Entity entity, float v) {
        RegenComponent regenComponent = regenMapper.get(entity);
        HealthComponent healthComponent = healthMapper.get(entity);

        if (regenComponent.shouldHeal(v)) healthComponent.increaseHealth(regenComponent.amount);
    }
}
