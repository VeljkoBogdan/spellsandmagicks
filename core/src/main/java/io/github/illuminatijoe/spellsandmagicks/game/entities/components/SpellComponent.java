package io.github.illuminatijoe.spellsandmagicks.game.entities.components;

import com.badlogic.ashley.core.Component;
import io.github.illuminatijoe.spellsandmagicks.game.spells.Spell;

import java.util.ArrayList;
import java.util.List;

public class SpellComponent implements Component {
    public List<Spell> spells = new ArrayList<>();

    public void addSpell(Spell spell) {
        if (spells.contains(spell)) {
            spell.upgrade();
        } else {
            spells.add(spell);
        }
    }
}
