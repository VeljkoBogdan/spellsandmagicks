package io.github.illuminatijoe.spellsandmagicks.game.entities.components;

import com.badlogic.ashley.core.Component;
import io.github.illuminatijoe.spellsandmagicks.util.Collision;

public class CollisionComponent implements Component {
    public Collision mask = Collision.FRIENDLY;

    public CollisionComponent(Collision mask) {
        this.mask = mask;
    }
}
