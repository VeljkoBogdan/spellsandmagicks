package io.github.illuminatijoe.spellsandmagicks.game.entities.projectiles;

import com.badlogic.ashley.core.Component;

public class ToxipoolPoolComponent implements Component {
    public float lifetime = 3f;

    public ToxipoolPoolComponent(float lifetime) {
        this.lifetime = lifetime;
    }
}
