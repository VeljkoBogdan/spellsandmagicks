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
import io.github.illuminatijoe.spellsandmagicks.util.Target;
import io.github.illuminatijoe.spellsandmagicks.util.ZIndex;

public class FireballShootingSystem extends IteratingSystem {
    private final ComponentMapper<PositionComponent> positionMapper = ComponentMapper.getFor(PositionComponent.class);

    public float cooldown = 1f;
    public float cooldownTimer = 0f;
    public float damage = 50f;

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
        Entity nearestEnemy = Target.findNearestEnemy(getEngine(), positionComponent.getPosition().cpy());
        if (nearestEnemy == null) return;

        PositionComponent enemyPos = nearestEnemy.getComponent(PositionComponent.class);
        Vector2 direction = new Vector2(enemyPos.position).sub(positionComponent.getPosition().cpy()).nor();

        // Create fireball entity
        Entity fireball = new Entity();
        fireball.add(new CollisionComponent(Collision.FRIENDLY));
        fireball.add(new AnimationComponent(AssetLoader.fireballAnimation));
        fireball.add(new PositionComponent(positionComponent.getPosition().cpy()));
        fireball.add(new FireballComponent(direction, 500f));
        fireball.add(new AttackComponent(this.damage));
        fireball.add(new ProjectileComponent());
        fireball.add(new LifetimeComponent(3f));
        fireball.add(new ZIndexComponent(ZIndex.PROJECTILES));

        getEngine().addEntity(fireball);
        AssetLoader.getFireballShootSound().play(0.25f);
    }

    public void upgrade() {
        this.cooldown *= 0.85f;
        this.damage *= 1.3f;
    }
}
