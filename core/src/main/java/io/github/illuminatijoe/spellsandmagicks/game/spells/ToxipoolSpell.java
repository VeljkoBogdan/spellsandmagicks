package io.github.illuminatijoe.spellsandmagicks.game.spells;

import com.badlogic.ashley.core.Component;
import com.badlogic.ashley.core.EntitySystem;
import io.github.illuminatijoe.spellsandmagicks.game.entities.projectiles.ToxipoolMovingSystem;
import io.github.illuminatijoe.spellsandmagicks.game.entities.projectiles.ToxipoolShootingSystem;

public class ToxipoolSpell implements Spell{
    public static String name = "Toxipool Spell";
    public static ToxipoolShootingSystem toxipoolShootingSystem = new ToxipoolShootingSystem();
    public static ToxipoolMovingSystem toxipoolMovingSystem = new ToxipoolMovingSystem();
    public String description = "Lobs a harmless projectile that turns into a toxic pool after a certain time, " +
        "which lingers and deals constant damage\n" +
        "Upgrades increase damage by 50% and reduce the cooldown by 10%";

    @Override
    public EntitySystem getEntityMovingSystem() {
        return toxipoolMovingSystem;
    }

    @Override
    public EntitySystem getEntityShootingSystem() {
        return toxipoolShootingSystem;
    }

    @Override
    public Component getComponent() {
        return null;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public void upgrade() {
        toxipoolShootingSystem.upgrade();
    }

    @Override
    public String getDescription() {
        return description;
    }
}
