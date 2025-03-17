package io.github.illuminatijoe.spellsandmagicks.game.entities.components;

import com.badlogic.ashley.core.Component;

public class ShootingComponent implements Component {
    public float shootFrequency = 2f;
    public float shootTimer = 0f;

    public ShootingComponent(float shootFrequency) {
        this.shootFrequency = shootFrequency;
    }
}
