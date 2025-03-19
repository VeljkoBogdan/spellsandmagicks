package io.github.illuminatijoe.spellsandmagicks.game.entities.projectiles;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.math.Vector2;

public class ToxipoolComponent implements Component {
    public Vector2 direction = new Vector2();
    public float speed;

    public ToxipoolComponent(Vector2 direction, float speed) {
        this.direction.set(direction).nor();
        this.speed = speed;
    }
}
