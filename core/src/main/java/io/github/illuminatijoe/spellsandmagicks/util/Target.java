package io.github.illuminatijoe.spellsandmagicks.util;

import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.gdx.math.Vector2;
import io.github.illuminatijoe.spellsandmagicks.game.entities.components.EnemyComponent;
import io.github.illuminatijoe.spellsandmagicks.game.entities.components.PositionComponent;

public class Target {
    public static Entity findNearestEnemy(Engine engine, Vector2 pos) {
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
