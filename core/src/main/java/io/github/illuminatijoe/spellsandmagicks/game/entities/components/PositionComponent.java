package io.github.illuminatijoe.spellsandmagicks.game.entities.components;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.math.Vector2;

public class PositionComponent implements Component {
    public Vector2 position;

    public PositionComponent(Vector2 position) {
        this.position = position;
    }

    public Vector2 getPosition() {
        return this.position;
    }
}
