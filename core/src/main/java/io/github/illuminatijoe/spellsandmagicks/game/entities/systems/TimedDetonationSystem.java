package io.github.illuminatijoe.spellsandmagicks.game.entities.systems;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import io.github.illuminatijoe.spellsandmagicks.game.entities.projectiles.TimedDetonationComponent;

public class TimedDetonationSystem extends IteratingSystem {
    private final ComponentMapper<TimedDetonationComponent> timedDetonationMapper = ComponentMapper.getFor(TimedDetonationComponent.class);

    public TimedDetonationSystem() {
        super(Family.all(TimedDetonationComponent.class).get());
    }

    @Override
    protected void processEntity(Entity entity, float v) {
        TimedDetonationComponent timedDetonationComponent = timedDetonationMapper.get(entity);

        if (timedDetonationComponent.cooldown <= 0f) {
            timedDetonationComponent.detonate();
        } else {
            timedDetonationComponent.cooldown -= v;
        }
    }
}
