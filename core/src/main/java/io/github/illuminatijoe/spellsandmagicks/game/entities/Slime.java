package io.github.illuminatijoe.spellsandmagicks.game.entities;

import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.math.Vector2;
import io.github.illuminatijoe.spellsandmagicks.game.entities.components.*;
import io.github.illuminatijoe.spellsandmagicks.graphics.AssetLoader;
import io.github.illuminatijoe.spellsandmagicks.util.Collision;
import io.github.illuminatijoe.spellsandmagicks.util.ZIndex;

public class Slime extends Entity {
    public Slime(AssetLoader assetLoader, Player player, Vector2 pos) {
        this.add(new AnimationComponent(assetLoader.getSlimeAnimation()));
        this.add(new PositionComponent(pos));
        this.add(new VelocityComponent());
        this.add(new TargetComponent(player));
        this.add(new CollisionComponent(Collision.HOSTILE));
        this.add(new HealthComponent(100f, 0.1f, null));
        this.add(new AttackComponent(25f));
        this.add(new EnemyComponent());
        this.add(new ZIndexComponent(ZIndex.ENEMIES));
    }
}
