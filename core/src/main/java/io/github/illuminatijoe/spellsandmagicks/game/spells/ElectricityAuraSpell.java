package io.github.illuminatijoe.spellsandmagicks.game.spells;

import com.badlogic.ashley.core.EntitySystem;
import io.github.illuminatijoe.spellsandmagicks.game.entities.components.ElectricityAuraComponent;

public class ElectricityAuraSpell implements Spell {
    public static String name = "Electricity Aura Spell";
    public static ElectricityAuraComponent electricityAuraComponent =
        new ElectricityAuraComponent(170, 10f, 0.2f);

    @Override
    public EntitySystem getEntityMovingSystem() {
        return null;
    }

    @Override
    public EntitySystem getEntityShootingSystem() {
        return null;
    }

    @Override
    public String getName() {
        return name;
    }

    public ElectricityAuraComponent getElectricityAuraComponent() {
        return electricityAuraComponent;
    }

    @Override
    public void upgrade() {
        electricityAuraComponent.upgrade();
    }
}
