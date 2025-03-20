package io.github.illuminatijoe.spellsandmagicks.game.entities.components;

import com.badlogic.ashley.core.Component;

public class PoisonMagickComponent implements Component {
    public float cooldown;
    public float damage;
    public float duration;

    public PoisonMagickComponent(float damage, float duration, float cooldown) {
        this.damage = damage;
        this.duration = duration;
        this.cooldown = cooldown;
    }

    public void upgrade() {
        this.damage *= 1.25f;
        this.duration *= 1.25f;
    }
}
