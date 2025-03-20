package io.github.illuminatijoe.spellsandmagicks.game.spells;

import com.badlogic.ashley.core.Component;
import com.badlogic.ashley.core.EntitySystem;
import io.github.illuminatijoe.spellsandmagicks.game.entities.components.ExplosionMagickComponent;
import io.github.illuminatijoe.spellsandmagicks.game.entities.systems.ExplosionSystem;

public class ExplosionMagick implements Spell {
    public String name = "Explosion Magick";
    public String description = "All your spells summon an explosion on hit that deals damage \n\n" +
        "Upgrades deal 25% more damage";
    public static ExplosionMagickComponent explosionMagickComponent =
        new ExplosionMagickComponent(30f, 110f);
    public static ExplosionSystem explosionSystem = new ExplosionSystem();

    @Override
    public EntitySystem getEntityMovingSystem() {
        return explosionSystem;
    }

    @Override
    public EntitySystem getEntityShootingSystem() {
        return null;
    }

    @Override
    public Component getComponent() {
        return explosionMagickComponent;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public void upgrade() {
        explosionMagickComponent.upgrade();
    }

    @Override
    public String getDescription() {
        return description;
    }
}
