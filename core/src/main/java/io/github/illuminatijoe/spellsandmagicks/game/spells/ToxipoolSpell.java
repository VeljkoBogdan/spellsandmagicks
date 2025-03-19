package io.github.illuminatijoe.spellsandmagicks.game.spells;

import com.badlogic.ashley.core.EntitySystem;
import io.github.illuminatijoe.spellsandmagicks.game.entities.projectiles.ToxipoolMovingSystem;
import io.github.illuminatijoe.spellsandmagicks.game.entities.projectiles.ToxipoolShootingSystem;

public class ToxipoolSpell implements Spell{
    public static String name = "Toxipool Spell";
    public static ToxipoolShootingSystem toxipoolShootingSystem = new ToxipoolShootingSystem();
    public static ToxipoolMovingSystem toxipoolMovingSystem = new ToxipoolMovingSystem();

    @Override
    public EntitySystem getEntityMovingSystem() {
        return toxipoolMovingSystem;
    }

    @Override
    public EntitySystem getEntityShootingSystem() {
        return toxipoolShootingSystem;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public void upgrade() {
        toxipoolShootingSystem.upgrade();
    }
}
