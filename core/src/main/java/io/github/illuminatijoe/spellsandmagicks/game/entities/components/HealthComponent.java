package io.github.illuminatijoe.spellsandmagicks.game.entities.components;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.utils.Pool;

public class HealthComponent implements Component, Pool.Poolable {
    public float maxHealth = 100f;
    public float health = 100f;
    public boolean isDead = false;
    public Runnable onDeath = null;

    public boolean canBeHurt = true;
    public float iFramesDuration = 0.5f;
    public float iTimer = 0f;

    public HealthComponent(float maxHealth, float iFramesDuration, Runnable runnable) {
        this.maxHealth = maxHealth;
        this.health = maxHealth;
        this.onDeath = runnable;
        this.iFramesDuration = iFramesDuration;
    }

    public boolean decreaseHealth(float amount) {
        if (canBeHurt) {

            this.health -= amount;
            iTimer = iFramesDuration;

            if (health <= 0f) {
                this.isDead = true;
                if (onDeath != null) {
                    onDeath.run();
                }
            }

            return true;
        }
        return false;
    }

    public void updateIFrames(float delta) {
        iTimer -= delta;

        canBeHurt = iTimer <= 0f;
    }

    @Override
    public void reset() {
        maxHealth = 100f;
        health = 100f;
        isDead = false;
        onDeath = null;
        canBeHurt = true;
        iFramesDuration = 0.5f;
    }

    public void increaseHealth(float amount) {
        health += amount;

        if (this.health >= maxHealth) {
            health = maxHealth;
        }
    }
}
