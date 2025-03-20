package io.github.illuminatijoe.spellsandmagicks.game.entities.components;

import com.badlogic.ashley.core.Component;

public class PoisonedComponent implements Component {
    public float amount;
    public float cooldown;
    public float timer = 0f;
    public float duration;

    public PoisonedComponent(float amount, float cooldown, float duration) {
        this.amount = amount;
        this.cooldown = cooldown;
        this.duration = duration;
    }

    public boolean shouldDealDamage(float delta) {
        if (timer <= 0f) {
            timer = cooldown;
            return true;
        } else {
            timer -= delta;
            return false;
        }
    }

    public boolean isOver(float delta) {
        if (duration <= 0f) {
            return true;
        } else {
            duration -= delta;
            return false;
        }
    }
}
