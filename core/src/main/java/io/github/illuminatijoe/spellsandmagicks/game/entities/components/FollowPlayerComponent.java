package io.github.illuminatijoe.spellsandmagicks.game.entities.components;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.math.Vector2;

public class FollowPlayerComponent implements Component {
    public Vector2 offset;

    public FollowPlayerComponent(float offsetX, float offsetY) {
        this.offset = new Vector2(offsetX, offsetY);
    }
}
