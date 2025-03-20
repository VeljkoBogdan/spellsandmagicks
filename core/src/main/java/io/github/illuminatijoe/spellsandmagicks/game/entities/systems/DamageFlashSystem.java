package io.github.illuminatijoe.spellsandmagicks.game.entities.systems;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.badlogic.gdx.graphics.Color;
import io.github.illuminatijoe.spellsandmagicks.game.entities.components.AnimationComponent;
import io.github.illuminatijoe.spellsandmagicks.game.entities.components.DamageFlashComponent;

public class DamageFlashSystem extends IteratingSystem {
    private final ComponentMapper<DamageFlashComponent> flashMapper = ComponentMapper.getFor(DamageFlashComponent.class);
    private final ComponentMapper<AnimationComponent> animationMapper = ComponentMapper.getFor(AnimationComponent.class);

    public DamageFlashSystem() {
        super(Family.all(DamageFlashComponent.class, AnimationComponent.class).get());
    }

    @Override
    protected void processEntity(Entity entity, float deltaTime) {
        DamageFlashComponent flash = flashMapper.get(entity);
        AnimationComponent animation = animationMapper.get(entity);

        if (flash == null || animation == null) return;

        animation.tint = Color.RED;

        flash.duration -= deltaTime;

        if (flash.duration <= 0) {
            animation.tint = Color.WHITE;
            entity.remove(DamageFlashComponent.class);
        }
    }
}
