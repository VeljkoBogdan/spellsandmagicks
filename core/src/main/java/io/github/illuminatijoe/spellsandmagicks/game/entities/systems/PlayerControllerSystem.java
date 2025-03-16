package io.github.illuminatijoe.spellsandmagicks.game.entities.systems;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import io.github.illuminatijoe.spellsandmagicks.game.entities.components.AnimationComponent;
import io.github.illuminatijoe.spellsandmagicks.game.entities.components.ControllableComponent;
import io.github.illuminatijoe.spellsandmagicks.game.entities.components.VelocityComponent;

public class PlayerControllerSystem extends IteratingSystem {
    private final ComponentMapper<VelocityComponent> vm = ComponentMapper.getFor(VelocityComponent.class);
    private final ComponentMapper<AnimationComponent> am = ComponentMapper.getFor(AnimationComponent.class);

    public PlayerControllerSystem() {
        super(Family.all(AnimationComponent.class, VelocityComponent.class, ControllableComponent.class).get());
    }

    @Override
    protected void processEntity(Entity entity, float deltaTime) {
        VelocityComponent velocityComponent = vm.get(entity);
        AnimationComponent animationComponent = am.get(entity);

        velocityComponent.velocity.set(0, 0); // Reset velocity before applying movement

        if (Gdx.input.isKeyPressed(Input.Keys.W)) {
            velocityComponent.velocity.y += 1;
        }
        if (Gdx.input.isKeyPressed(Input.Keys.A)) {
            velocityComponent.velocity.x -= 1;
            animationComponent.facingLeft = true;
        }
        if (Gdx.input.isKeyPressed(Input.Keys.S)) {
            velocityComponent.velocity.y -= 1;
        }
        if (Gdx.input.isKeyPressed(Input.Keys.D)) {
            velocityComponent.velocity.x += 1;
            animationComponent.facingLeft = false;
        }

        animationComponent.idle = velocityComponent.velocity.isZero();

        velocityComponent.velocity.nor().scl(300); // Normalize so diagonal movement isn't faster
    }
}
