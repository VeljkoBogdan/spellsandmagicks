package io.github.illuminatijoe.spellsandmagicks.game.spells;

import com.badlogic.ashley.core.Component;
import com.badlogic.ashley.core.EntitySystem;

public interface Spell {
    EntitySystem getEntityMovingSystem();
    EntitySystem getEntityShootingSystem();
    Component getComponent();
    String getName();

    void upgrade();

    String getDescription();
}
