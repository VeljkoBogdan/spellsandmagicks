package io.github.illuminatijoe.spellsandmagicks.game.entities.components;

import com.badlogic.ashley.core.Component;

public class ExplosionComponent implements Component {
    public float damage;
    public float radius;
    public float lifetime = 0.12f;
    public boolean hasDamaged = false;

    public ExplosionComponent(float damage, float radius) {
        this.damage = damage;
        this.radius = radius;
    }
}
