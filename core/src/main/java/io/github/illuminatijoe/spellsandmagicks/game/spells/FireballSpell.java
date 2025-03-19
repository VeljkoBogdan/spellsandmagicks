package io.github.illuminatijoe.spellsandmagicks.game.spells;

import com.badlogic.ashley.core.EntitySystem;
import io.github.illuminatijoe.spellsandmagicks.game.entities.projectiles.FireballMovingSystem;
import io.github.illuminatijoe.spellsandmagicks.game.entities.projectiles.FireballShootingSystem;

public class FireballSpell implements Spell {
    public String name = "Fireball Spell";
    public FireballMovingSystem entityMovingSystem = new FireballMovingSystem();
    public FireballShootingSystem entityShootingSystem = new FireballShootingSystem();

    @Override
    public EntitySystem getEntityMovingSystem() {
        return entityMovingSystem;
    }

    @Override
    public EntitySystem getEntityShootingSystem() {
        return entityShootingSystem;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public void upgrade() {
        entityShootingSystem.upgrade();
    }
}
