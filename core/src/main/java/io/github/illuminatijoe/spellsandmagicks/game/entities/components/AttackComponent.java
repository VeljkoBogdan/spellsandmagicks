package io.github.illuminatijoe.spellsandmagicks.game.entities.components;

import com.badlogic.ashley.core.Component;

public class AttackComponent implements Component {
    public float damage = 10f;

    public AttackComponent(float damage) {
        this.damage = damage;
    }
}
