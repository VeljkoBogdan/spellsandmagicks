package io.github.illuminatijoe.spellsandmagicks.game.entities.systems;

import com.badlogic.ashley.core.*;
import com.badlogic.ashley.systems.IteratingSystem;
import com.badlogic.ashley.utils.ImmutableArray;
import com.badlogic.gdx.math.Vector2;
import io.github.illuminatijoe.spellsandmagicks.game.core.SpatialGrid;
import io.github.illuminatijoe.spellsandmagicks.game.entities.components.CollisionComponent;
import io.github.illuminatijoe.spellsandmagicks.game.entities.components.PositionComponent;

import java.util.Set;

public class CollisionSystem extends EntitySystem {
    private ComponentMapper<PositionComponent> pm = ComponentMapper.getFor(PositionComponent.class);
    private ComponentMapper<CollisionComponent> cm = ComponentMapper.getFor(CollisionComponent.class);
    private final SpatialGrid spatialGrid;
    private ImmutableArray<Entity> entities;

    public CollisionSystem(int gridSize) {
        spatialGrid = new SpatialGrid(gridSize);
    }

    @Override
    public void addedToEngine(Engine engine) {
        entities = engine.getEntitiesFor(Family.all(PositionComponent.class, CollisionComponent.class).get());
    }

    @Override
    public void update(float deltaTime) {
        spatialGrid.clear();

        // Populate spatial grid
        for (Entity entity : entities) {
            PositionComponent pos = pm.get(entity);
            spatialGrid.addEntity(entity, pos.position);
        }

        // Process collisions
        for (Entity entity : entities) {
            PositionComponent pos = pm.get(entity);
            Set<Entity> nearby = spatialGrid.getNearbyEntities(pos.position);

            for (Entity other : nearby) {
                // Broad col
                if (entity != other && aabbCollision(pm.get(entity), 32, 32, pm.get(other), 32, 32)) {
                    // Narrow col
                    if (circleCollision(pm.get(entity), 16, pm.get(other), 16)) {
                        Vector2 dir = new Vector2(pm.get(entity).position).sub(pm.get(other).position).nor();
                        float pushAmount = 1f;

                        pm.get(entity).position.add(dir.scl(pushAmount));
                        pm.get(other).position.sub(dir.scl(pushAmount));
                    }
                }
            }
        }
    }

    public boolean aabbCollision(PositionComponent a, float widthA, float heightA,
                                 PositionComponent b, float widthB, float heightB) {
        return (a.position.x < b.position.x + widthB && a.position.x + widthA > b.position.x &&
            a.position.y < b.position.y + heightB && a.position.y + heightA > b.position.y);
    }

    public boolean circleCollision(PositionComponent a, float radiusA,
                                   PositionComponent b, float radiusB) {
        float distance = a.position.dst2(b.position);
        float radiusSum = radiusA + radiusB;

        return distance <= radiusSum * radiusSum;
    }
}
