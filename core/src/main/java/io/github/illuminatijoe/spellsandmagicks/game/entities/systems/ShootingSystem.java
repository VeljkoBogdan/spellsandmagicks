package io.github.illuminatijoe.spellsandmagicks.game.entities.systems;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import io.github.illuminatijoe.spellsandmagicks.game.entities.components.ControllableComponent;
import io.github.illuminatijoe.spellsandmagicks.game.entities.components.PositionComponent;
import io.github.illuminatijoe.spellsandmagicks.game.entities.components.ProjectileComponent;

public class ShootingSystem extends IteratingSystem {
    private final ComponentMapper<ProjectileComponent> pm = ComponentMapper.getFor(ProjectileComponent.class);

    public ShootingSystem() {
        super(Family.all(PositionComponent.class, ControllableComponent.class).get());
    }

    @Override
    protected void processEntity(Entity entity, float v) {

    }
}
