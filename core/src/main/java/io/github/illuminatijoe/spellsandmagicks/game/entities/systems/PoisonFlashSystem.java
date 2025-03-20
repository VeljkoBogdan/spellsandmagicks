package io.github.illuminatijoe.spellsandmagicks.game.entities.systems;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.badlogic.gdx.graphics.Color;
import io.github.illuminatijoe.spellsandmagicks.game.entities.components.AnimationComponent;
import io.github.illuminatijoe.spellsandmagicks.game.entities.components.PoisonFlashComponent;

public class PoisonFlashSystem extends IteratingSystem {
    private final ComponentMapper<PoisonFlashComponent> flashMapper = ComponentMapper.getFor(PoisonFlashComponent.class);
    private final ComponentMapper<AnimationComponent> animationMapper = ComponentMapper.getFor(AnimationComponent.class);

    public PoisonFlashSystem() {
        super(Family.all(PoisonFlashComponent.class, AnimationComponent.class).get());
    }

    @Override
    protected void processEntity(Entity entity, float deltaTime) {
        PoisonFlashComponent flash = flashMapper.get(entity);
        AnimationComponent animation = animationMapper.get(entity);

        if (flash == null || animation == null) return;

        animation.tint = Color.GREEN;

        flash.duration -= deltaTime;

        if (flash.duration <= 0) {
            animation.tint = Color.WHITE;
            entity.remove(PoisonFlashComponent.class);
        }
    }
}
