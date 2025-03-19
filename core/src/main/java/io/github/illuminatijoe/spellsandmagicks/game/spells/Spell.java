package io.github.illuminatijoe.spellsandmagicks.game.spells;

import com.badlogic.ashley.core.EntitySystem;

public interface Spell {
    EntitySystem getEntityMovingSystem();
    EntitySystem getEntityShootingSystem();
    String getName();

    void upgrade();
}
