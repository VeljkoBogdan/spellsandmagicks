package io.github.illuminatijoe.spellsandmagicks.game.entities.systems;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import io.github.illuminatijoe.spellsandmagicks.game.entities.components.CameraFollowComponent;
import io.github.illuminatijoe.spellsandmagicks.game.entities.components.PositionComponent;

/// Handles the movement of the Camera
public class CameraSystem extends IteratingSystem {
    private final OrthographicCamera camera;
    private final ComponentMapper<PositionComponent> pm = ComponentMapper.getFor(PositionComponent.class);

    public CameraSystem(OrthographicCamera camera) {
        super(Family.all(PositionComponent.class, CameraFollowComponent.class).get());
        this.camera = camera;
    }

    @Override
    protected void processEntity(Entity entity, float deltaTime) {
        PositionComponent position = pm.get(entity);
        if (position == null) return;

        Vector2 targetPos = position.position;
        float lerpFactor = 1.0f - (float) Math.pow(0.001, deltaTime);
        camera.position.lerp(new Vector3(targetPos.x, targetPos.y, 0), lerpFactor);

        camera.update();
    }
}
