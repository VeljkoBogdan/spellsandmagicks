package io.github.illuminatijoe.spellsandmagicks.game.entities.projectiles;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import io.github.illuminatijoe.spellsandmagicks.game.entities.components.PositionComponent;

public class ToxipoolMovingSystem extends IteratingSystem {
    private final ComponentMapper<PositionComponent> positionMapper = ComponentMapper.getFor(PositionComponent.class);
    private final ComponentMapper<ToxipoolComponent> toxipoolMapper = ComponentMapper.getFor(ToxipoolComponent.class);

    public ToxipoolMovingSystem() {
        super(Family.all(ToxipoolComponent.class).get());
    }

    @Override
    protected void processEntity(Entity entity, float v) {
        PositionComponent positionComponent = positionMapper.get(entity);
        ToxipoolComponent toxipoolComponent = toxipoolMapper.get(entity);

        positionComponent.position.mulAdd(toxipoolComponent.direction, toxipoolComponent.speed * v);
    }
}
