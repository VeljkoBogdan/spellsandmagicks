package io.github.illuminatijoe.spellsandmagicks.game.entities.projectiles;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;

public class ToxipoolPoolSystem extends IteratingSystem {
    private final ComponentMapper<ToxipoolPoolComponent> toxipoolMapper = ComponentMapper.getFor(ToxipoolPoolComponent.class);

    public ToxipoolPoolSystem() {
        super(Family.all(ToxipoolPoolComponent.class).get());
    }

    @Override
    protected void processEntity(Entity entity, float v) {
        ToxipoolPoolComponent toxipoolPoolComponent = toxipoolMapper.get(entity);

        if (toxipoolPoolComponent.lifetime <= 0f) {
            getEngine().removeEntity(entity);
        } else {
            toxipoolPoolComponent.lifetime -= v;
        }
    }
}
