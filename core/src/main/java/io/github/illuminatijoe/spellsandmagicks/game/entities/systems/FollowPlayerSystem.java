package io.github.illuminatijoe.spellsandmagicks.game.entities.systems;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.badlogic.ashley.core.ComponentMapper;

import io.github.illuminatijoe.spellsandmagicks.game.entities.Player;
import io.github.illuminatijoe.spellsandmagicks.game.entities.components.*;

public class FollowPlayerSystem extends IteratingSystem {
    private final ComponentMapper<PositionComponent> positionMapper = ComponentMapper.getFor(PositionComponent.class);
    private final ComponentMapper<FollowPlayerComponent> followMapper = ComponentMapper.getFor(FollowPlayerComponent.class);

    private final Player player;

    public FollowPlayerSystem(Player player) {
        super(Family.all(FollowPlayerComponent.class, PositionComponent.class).get());
        this.player = player;
    }

    @Override
    protected void processEntity(Entity entity, float deltaTime) {
        PositionComponent playerPos = player.getComponent(PositionComponent.class);
        PositionComponent auraPos = positionMapper.get(entity);
        FollowPlayerComponent follow = followMapper.get(entity);

        if (playerPos != null && auraPos != null) {
            auraPos.getPosition().set(playerPos.getPosition()).add(follow.offset);
        }
    }
}

