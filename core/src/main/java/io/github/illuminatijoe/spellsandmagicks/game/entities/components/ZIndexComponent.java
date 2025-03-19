package io.github.illuminatijoe.spellsandmagicks.game.entities.components;

import com.badlogic.ashley.core.Component;
import io.github.illuminatijoe.spellsandmagicks.util.ZIndex;

public class ZIndexComponent implements Component {
    public int zIndex;

    public ZIndexComponent(ZIndex zIndex) {
        this.zIndex = zIndex.ordinal();
    }
}

