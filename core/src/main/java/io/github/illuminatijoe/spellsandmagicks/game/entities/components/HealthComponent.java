package io.github.illuminatijoe.spellsandmagicks.game.entities.components;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.utils.Pool;

public class HealthComponent implements Component, Pool.Poolable {
    public float health = 100f;
    public boolean isDead = false;
    public Runnable onDeath = null;

    public boolean canBeHurt = true;
    public float iFramesDuration = 0.5f;
    public float iTimer = 0f;

    public HealthComponent(float health, float iFramesDuration, Runnable runnable) {
        this.health = health;
        this.onDeath = runnable;
        this.iFramesDuration = iFramesDuration;
    }

    public void decreaseHealth(float amount) {
        if (canBeHurt) {
            this.health -= amount;
            iTimer = iFramesDuration;

            if (health <= 0f) {
                this.isDead = true;
                if (onDeath != null) {
                    onDeath.run();
                }
            }
        }
    }

    public void updateIFrames(float delta) {
        iTimer -= delta;

        if (iTimer <= 0f) {
            canBeHurt = true;
        } else {
            canBeHurt = false;
        }
    }

    @Override
    public void reset() {
        health = 100f;
        isDead = false;
        onDeath = null;
        canBeHurt = true;
        iFramesDuration = 0.5f;
    }
}
