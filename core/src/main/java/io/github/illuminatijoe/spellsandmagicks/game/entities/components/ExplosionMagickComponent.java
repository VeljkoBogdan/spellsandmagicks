package io.github.illuminatijoe.spellsandmagicks.game.entities.components;

import com.badlogic.ashley.core.Component;

public class ExplosionMagickComponent implements Component {
    public float radius;
    public float damage;

    public ExplosionMagickComponent(float damage, float radius) {
        this.damage = damage;
        this.radius = radius;
    }

    public void upgrade() {
        this.damage *= 1.25f;
    }
}
