package io.github.illuminatijoe.spellsandmagicks.game.entities.projectiles;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.badlogic.gdx.math.Vector2;
import io.github.illuminatijoe.spellsandmagicks.game.entities.components.*;
import io.github.illuminatijoe.spellsandmagicks.graphics.AssetLoader;
import io.github.illuminatijoe.spellsandmagicks.util.Collision;

public class FireballShootingSystem extends IteratingSystem {
    public static float cooldown = 1f;
    public float cooldownTimer = 0f;
    private final ComponentMapper<PositionComponent> positionMapper = ComponentMapper.getFor(PositionComponent.class);

    public FireballShootingSystem() {
        super(Family.all(ControllableComponent.class, PositionComponent.class).get());
    }

    @Override
    protected void processEntity(Entity entity, float v) {
        if (cooldownTimer <= 0f) {
            cooldownTimer = cooldown;
            spawnFireball(entity, v);
        } else {
            cooldownTimer -= v;
        }
    }

    private void spawnFireball(Entity entity, float v) {
        PositionComponent positionComponent = positionMapper.get(entity);

        // Find nearest enemy
        Entity nearestEnemy = findNearestEnemy(getEngine(), positionComponent.getPosition().cpy());
        if (nearestEnemy == null) return;

        PositionComponent enemyPos = nearestEnemy.getComponent(PositionComponent.class);
        Vector2 direction = new Vector2(enemyPos.position).sub(positionComponent.getPosition().cpy()).nor();

        // Create fireball entity
        Entity fireball = new Entity();
        fireball.add(new CollisionComponent(Collision.FRIENDLY));
        fireball.add(new AnimationComponent(AssetLoader.fireballAnimation));
        fireball.add(new PositionComponent(positionComponent.getPosition().cpy()));
        fireball.add(new FireballComponent(direction, 500f));
        fireball.add(new AttackComponent(50f));
        fireball.add(new ProjectileComponent());
        fireball.add(new LifetimeComponent(3f));

        getEngine().addEntity(fireball);
    }

    private static Entity findNearestEnemy(Engine engine, Vector2 pos) {
        float minDist = Float.MAX_VALUE;
        Entity nearest = null;

        for (Entity enemy : engine.getEntitiesFor(Family.all(EnemyComponent.class, PositionComponent.class).get())) {
            PositionComponent enemyPos = enemy.getComponent(PositionComponent.class);
            float dist = pos.dst2(enemyPos.position);
            if (dist < minDist) {
                minDist = dist;
                nearest = enemy;
            }
        }

        return nearest;
    }
}
