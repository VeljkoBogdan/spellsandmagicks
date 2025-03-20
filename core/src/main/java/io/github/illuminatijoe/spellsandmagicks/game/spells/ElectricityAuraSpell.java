package io.github.illuminatijoe.spellsandmagicks.game.spells;

import com.badlogic.ashley.core.Component;
import com.badlogic.ashley.core.EntitySystem;
import io.github.illuminatijoe.spellsandmagicks.game.entities.components.ElectricityAuraComponent;

public class ElectricityAuraSpell implements Spell {
    public static String name = "Electricity Aura Spell";
    public static ElectricityAuraComponent electricityAuraComponent =
        new ElectricityAuraComponent(170, 40f, 0.75f);
    private static String description = "Summons an aura of electricity that deals constant damage to nearby enemies\n" +
        "Upgrades increase damage by 30% and tick rate by 10%";

    @Override
    public EntitySystem getEntityMovingSystem() {
        return null;
    }

    @Override
    public EntitySystem getEntityShootingSystem() {
        return null;
    }

    @Override
    public Component getComponent() {
        return electricityAuraComponent;
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

    @Override
    public String getDescription() {
        return description;
    }
}
