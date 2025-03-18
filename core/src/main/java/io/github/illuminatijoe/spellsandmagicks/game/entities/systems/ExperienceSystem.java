package io.github.illuminatijoe.spellsandmagicks.game.entities.systems;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import io.github.illuminatijoe.spellsandmagicks.game.entities.components.ExperienceComponent;
import io.github.illuminatijoe.spellsandmagicks.game.entities.components.PlayerComponent;

public class ExperienceSystem extends IteratingSystem {
    private final ComponentMapper<ExperienceComponent> experienceMapper = ComponentMapper.getFor(ExperienceComponent.class);

    public ExperienceSystem() {
        super(Family.all(PlayerComponent.class, ExperienceComponent.class).get());
    }

    @Override
    protected void processEntity(Entity entity, float v) {
        ExperienceComponent experienceComponent = experienceMapper.get(entity);

    }
}
