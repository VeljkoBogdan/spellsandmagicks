package io.github.illuminatijoe.spellsandmagicks.game.entities.projectiles;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.badlogic.gdx.math.Vector2;
import io.github.illuminatijoe.spellsandmagicks.game.entities.components.*;
import io.github.illuminatijoe.spellsandmagicks.graphics.AssetLoader;
import io.github.illuminatijoe.spellsandmagicks.util.Collision;
import io.github.illuminatijoe.spellsandmagicks.util.Target;
import io.github.illuminatijoe.spellsandmagicks.util.ZIndex;

public class ToxipoolShootingSystem extends IteratingSystem {
    private final ComponentMapper<PositionComponent> positionMapper = ComponentMapper.getFor(PositionComponent.class);

    public float cooldown = 2.5f;
    public float cooldownTimer = 0f;
    public float damage = 50f;

    public ToxipoolShootingSystem() {
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
        Entity nearestEnemy = Target.findNearestEnemy(getEngine(), positionComponent.getPosition().cpy());
        if (nearestEnemy == null) return;

        PositionComponent enemyPos = nearestEnemy.getComponent(PositionComponent.class);
        Vector2 direction = new Vector2(enemyPos.position).sub(positionComponent.getPosition().cpy()).nor();

        Entity toxipoolProjectile = new Entity();
        toxipoolProjectile.add(new CollisionComponent(Collision.FRIENDLY));
        toxipoolProjectile.add(new AnimationComponent(AssetLoader.toxipoolAnimation));
        toxipoolProjectile.add(new PositionComponent(positionComponent.getPosition().cpy()));
        PositionComponent projectilePosition = positionMapper.get(toxipoolProjectile);
        toxipoolProjectile.add(new ToxipoolComponent(direction, 500f));
        toxipoolProjectile.add(new ProjectileComponent());
        toxipoolProjectile.add(new NonDestroyableProjectileComponent());
        toxipoolProjectile.add(new ZIndexComponent(ZIndex.PROJECTILES));
        toxipoolProjectile.add(new TimedDetonationComponent(0.35f, () -> {
            Entity pool = new Entity();
            pool.add(new CollisionComponent(Collision.FRIENDLY));
            pool.add(new AnimationComponent(AssetLoader.toxipoolPoolAnimation));
            pool.add(new ToxipoolPoolComponent(3f));
            pool.add(new PositionComponent(
                projectilePosition.getPosition().cpy().sub(64, 64)
            ));
            pool.add(new ZIndexComponent(ZIndex.FURTHEST));
            pool.add(new ProjectileComponent());
            pool.add(new AttackComponent(this.damage));
            pool.add(new NonDestroyableProjectileComponent());

            getEngine().removeEntity(toxipoolProjectile);
            getEngine().addEntity(pool);
            AssetLoader.getToxipoolSpawnSound().play();
        }));

        getEngine().addEntity(toxipoolProjectile);
        AssetLoader.getToxipoolShootSound().play();
    }

    public void upgrade() {
        this.cooldown *= 0.9f;
        this.damage *= 1.5f;
    }
}
