package io.github.illuminatijoe.spellsandmagicks.game.entities.projectiles;

import com.badlogic.ashley.core.Component;

public class ToxipoolPoolComponent implements Component {
    public float lifetime = 3f;
    public float cooldown = 0.5f;
    public float cooldownTimer = 0f;

    public ToxipoolPoolComponent(float lifetime) {
        this.lifetime = lifetime;
    }

    public boolean shouldDamage(float delta) {
        if (cooldownTimer <= 0f) {
            cooldownTimer = cooldown;
            return true;
        } else {
            cooldownTimer -= delta;
            return false;
        }
    }
}
