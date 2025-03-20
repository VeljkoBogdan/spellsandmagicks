package io.github.illuminatijoe.spellsandmagicks.game.spells;

import com.badlogic.ashley.core.Component;
import com.badlogic.ashley.core.EntitySystem;
import io.github.illuminatijoe.spellsandmagicks.game.entities.components.PoisonMagickComponent;
import io.github.illuminatijoe.spellsandmagicks.game.entities.systems.PoisonSystem;

public class PoisonMagick implements Spell{
    public String name = "Poison Magick";
    public String description = "All spells apply a poison effect that deals periodical damage over 5 seconds\n\n" +
        "Upgrades increase damage by 25% and duration by 25%";
    public PoisonSystem poisonSystem = new PoisonSystem();
    public PoisonMagickComponent poisonMagickComponent = new PoisonMagickComponent(18f, 5f, 0.4f);

    @Override
    public EntitySystem getEntityMovingSystem() {
        return poisonSystem;
    }

    @Override
    public EntitySystem getEntityShootingSystem() {
        return null;
    }

    @Override
    public Component getComponent() {
        return poisonMagickComponent;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public void upgrade() {
        poisonMagickComponent.upgrade();
    }

    @Override
    public String getDescription() {
        return description;
    }
}
