package io.github.illuminatijoe.spellsandmagicks.game.entities.systems;

import com.badlogic.ashley.core.*;
import com.badlogic.ashley.utils.ImmutableArray;
import com.badlogic.gdx.math.Vector2;
import io.github.illuminatijoe.spellsandmagicks.game.core.SpatialGrid;
import io.github.illuminatijoe.spellsandmagicks.game.entities.Player;
import io.github.illuminatijoe.spellsandmagicks.game.entities.components.*;

import java.util.Set;

public class CollisionSystem extends EntitySystem {
    private final ComponentMapper<PositionComponent> pm = ComponentMapper.getFor(PositionComponent.class);
    private final ComponentMapper<PlayerComponent> playerMapper = ComponentMapper.getFor(PlayerComponent.class);
    private final ComponentMapper<EnemyComponent> em = ComponentMapper.getFor(EnemyComponent.class);
    private final ComponentMapper<HealthComponent> hm = ComponentMapper.getFor(HealthComponent.class);
    private final ComponentMapper<AttackComponent> am = ComponentMapper.getFor(AttackComponent.class);
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
                handleCollision(entity, other);
            }
        }
    }

    private void handleCollision(Entity entity, Entity other) {
        boolean isAPlayer = playerMapper.has(entity);
        boolean isBPlayer = playerMapper.has(other);
        boolean isAEnemy = em.has(entity);
        boolean isBEnemy = em.has(other);

        if (entity != other && aabbCollision(pm.get(entity), 22, 22, pm.get(other), 22, 22)) {
            Vector2 dir = pm.get(entity).position.cpy().sub(pm.get(other).position).nor();

            pm.get(entity).position.add(dir);
            pm.get(other).position.sub(dir);

            if (isAPlayer && isBEnemy) {
                AttackComponent attackComponent = am.get(other);
                damageEntity(entity, attackComponent);
            } else if (isBPlayer && isAEnemy) {
                AttackComponent attackComponent = am.get(entity);
                damageEntity(other, attackComponent);
            }
        }
    }

    private void damageEntity(Entity entity, AttackComponent attackComponent) {
        HealthComponent healthComponent = hm.get(entity);
        if (healthComponent != null) {
            healthComponent.decreaseHealth(attackComponent.damage);
        }
    }

    public boolean aabbCollision(PositionComponent a, float widthA, float heightA,
                                 PositionComponent b, float widthB, float heightB) {
        return (a.position.x < b.position.x + widthB && a.position.x + widthA > b.position.x &&
            a.position.y < b.position.y + heightB && a.position.y + heightA > b.position.y);
    }
}
