package io.github.illuminatijoe.spellsandmagicks.game.entities.components;

import com.badlogic.ashley.core.Component;

public class LifetimeComponent implements Component {
    public float lifetime = 3f;

    public LifetimeComponent(float lifetime) {
        this.lifetime = lifetime;
    }
}
