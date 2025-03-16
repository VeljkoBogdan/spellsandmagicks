package io.github.illuminatijoe.spellsandmagicks.game.entities.components;

import com.badlogic.ashley.core.Component;
import com.badlogic.ashley.core.Entity;

public class TargetComponent implements Component {
    public Entity target;

    public TargetComponent(Entity target) {
        this.target = target;
    }
}
