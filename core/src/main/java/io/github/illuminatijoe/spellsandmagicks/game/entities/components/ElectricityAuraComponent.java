package io.github.illuminatijoe.spellsandmagicks.game.entities.components;

import com.badlogic.ashley.core.Component;

import com.badlogic.ashley.core.Component;

public class ElectricityAuraComponent implements Component {
    public float range;
    public float damage;
    public float tickRate;
    public float tickTimer;
    private int level = 1; // Track upgrades

    public ElectricityAuraComponent(float range, float damage, float tickRate) {
        this.range = range;
        this.damage = damage;
        this.tickRate = tickRate;
        tickTimer = 0f;
    }

    public void upgrade() {
        level++;
        this.damage *= 1.25f;
        this.tickRate *= 0.8f;
    }

    public int getLevel() {
        return level;
    }

    public boolean shouldDealDamage(float deltaTime) {
        tickTimer += deltaTime;
        if (tickTimer >= tickRate) {
            tickTimer = 0;
            return true;
        }
        return false;
    }
}

