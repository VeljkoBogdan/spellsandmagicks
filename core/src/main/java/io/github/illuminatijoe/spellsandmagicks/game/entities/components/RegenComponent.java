package io.github.illuminatijoe.spellsandmagicks.game.entities.components;

import com.badlogic.ashley.core.Component;

public class RegenComponent implements Component {
    public float healCooldown;
    public float healTimer;
    public float amount = 1f;

    public RegenComponent(float amount, float healCooldown) {
        this.amount = amount;
        this.healCooldown = healCooldown;
    }

    public boolean shouldHeal(float delta) {
        if (healTimer <= 0f) {
            healTimer = healCooldown;
            return true;
        } else {
            healTimer -= delta;
            return false;
        }
    }
}
