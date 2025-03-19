package io.github.illuminatijoe.spellsandmagicks.game.entities.systems;

import com.badlogic.ashley.core.*;
import com.badlogic.ashley.utils.ImmutableArray;
import com.badlogic.gdx.math.Vector2;
import io.github.illuminatijoe.spellsandmagicks.game.entities.Player;
import io.github.illuminatijoe.spellsandmagicks.game.entities.projectiles.ToxipoolPoolComponent;
import io.github.illuminatijoe.spellsandmagicks.util.SpatialGrid;
import io.github.illuminatijoe.spellsandmagicks.game.entities.components.*;

import java.util.Set;

public class CollisionSystem extends EntitySystem {
    private final ComponentMapper<PositionComponent> pm = ComponentMapper.getFor(PositionComponent.class);
    private final ComponentMapper<PlayerComponent> playerMapper = ComponentMapper.getFor(PlayerComponent.class);
    private final ComponentMapper<EnemyComponent> enemyMapper = ComponentMapper.getFor(EnemyComponent.class);
    private final ComponentMapper<HealthComponent> hm = ComponentMapper.getFor(HealthComponent.class);
    private final ComponentMapper<AttackComponent> am = ComponentMapper.getFor(AttackComponent.class);
    private final ComponentMapper<ProjectileComponent> projectileMapper = ComponentMapper.getFor(ProjectileComponent.class);
    private final ComponentMapper<CollisionComponent> collisionMapper = ComponentMapper.getFor(CollisionComponent.class);
    private final ComponentMapper<ExperienceComponent> experienceMapper = ComponentMapper.getFor(ExperienceComponent.class);
    private final ComponentMapper<NonDestroyableProjectileComponent> ndpcMapper = ComponentMapper.getFor(NonDestroyableProjectileComponent.class);
    private final ComponentMapper<ToxipoolPoolComponent> toxipoolMapper = ComponentMapper.getFor(ToxipoolPoolComponent.class);
    private final SpatialGrid spatialGrid;
    private ImmutableArray<Entity> entities;
    private Player player;

    public CollisionSystem(int gridSize, Player player) {
        spatialGrid = new SpatialGrid(gridSize);
        this.player = player;
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

        if (player != null && player.getComponent(ElectricityAuraComponent.class) != null) {
            applyAuraDamage(deltaTime);
        }
    }

    private void handleCollision(Entity entity, Entity other) {
        CollisionComponent collisionComponentFirstEntity = collisionMapper.get(entity);
        CollisionComponent collisionComponentSecondEntity = collisionMapper.get(other);

        boolean isAProjectile = projectileMapper.has(entity);
        boolean isBProjectile = projectileMapper.has(other);
        boolean isAPlayer = playerMapper.has(entity);
        boolean isBPlayer = playerMapper.has(other);
        boolean isAEnemy = enemyMapper.has(entity);
        boolean isBEnemy = enemyMapper.has(other);

        if (collisionComponentFirstEntity.mask.equals(collisionComponentSecondEntity.mask)) return;

        if (entity != other && aabbCollision(pm.get(entity), 22, 22, pm.get(other), 22, 22)) {
            Vector2 dir = pm.get(entity).position.cpy().sub(pm.get(other).position).nor();

            if (isAPlayer && isBEnemy) {
                AttackComponent attackComponent = am.get(other);
                damageEntity(entity, attackComponent.damage);
            } else if (isBPlayer && isAEnemy) {
                AttackComponent attackComponent = am.get(entity);
                damageEntity(other, attackComponent.damage);
            } else if (isAProjectile && isBEnemy) {
                if (am.has(entity)) {
                    damageEntity(other, am.get(entity).damage);
                }
                if (!ndpcMapper.has(entity)) {
                    getEngine().removeEntity(entity); // remove proj
                }
            } else if (isBProjectile && isAEnemy) {
                if (am.has(other)) {
                    damageEntity(entity, am.get(other).damage);
                }
                if (!ndpcMapper.has(other)) {
                    getEngine().removeEntity(other); // remove proj
                }
            } else if (isBProjectile || isAProjectile) {
                pm.get(entity).position.add(dir);
                pm.get(other).position.sub(dir);
            }
        }

        boolean isAPool = entity.getComponent(ToxipoolPoolComponent.class) != null;
        boolean isBPool = other.getComponent(ToxipoolPoolComponent.class) != null;

        if (isAPool && isBEnemy) {
            if (entity != other && aabbCollision(pm.get(entity), 128, 128, pm.get(other), 22, 22)) {
                AttackComponent attackComponent = am.get(entity);
                damageEntity(other, attackComponent.damage);
            }
        } else if (isBPool && isAEnemy) {
            if (entity != other && aabbCollision(pm.get(entity), 22, 22, pm.get(other), 128, 128)) {
                AttackComponent attackComponent = am.get(other);
                damageEntity(entity, attackComponent.damage);
            }
        }
    }

    private void damageEntity(Entity entity, float damage) {
        HealthComponent healthComponent = hm.get(entity);
        if (healthComponent != null) {
            healthComponent.decreaseHealth(damage);

            if (healthComponent.isDead) {
                ExperienceComponent xpComponent = experienceMapper.get(player);
                xpComponent.addXp(25);
            }
        }
    }

    private void applyAuraDamage(float deltaTime) {
        ElectricityAuraComponent aura = player.getComponent(ElectricityAuraComponent.class);
        PositionComponent playerPos = pm.get(player);

        if (aura == null || playerPos == null) return;
        if (!aura.shouldDealDamage(deltaTime)) return;

        Set<Entity> nearbyEntities = spatialGrid.getNearbyEntities(playerPos.position);

        for (Entity entity : nearbyEntities) {
            if (enemyMapper.has(entity)) {
                PositionComponent enemyPos = pm.get(entity);

                if (enemyPos != null && playerPos.position.dst(enemyPos.position) <= aura.range) {
                    damageEntity(entity, aura.damage);
                }
            }
        }
    }

    public boolean aabbCollision(PositionComponent a, float widthA, float heightA,
                                 PositionComponent b, float widthB, float heightB) {
        return (a.position.x < b.position.x + widthB && a.position.x + widthA > b.position.x &&
            a.position.y < b.position.y + heightB && a.position.y + heightA > b.position.y);
    }
}
