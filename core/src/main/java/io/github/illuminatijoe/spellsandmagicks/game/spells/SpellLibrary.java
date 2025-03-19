package io.github.illuminatijoe.spellsandmagicks.game.spells;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class SpellLibrary {
    private static final List<Spell> spells = new ArrayList<>();

    static {
        spells.add(new FireballSpell());
        // TODO add more spells
    }

    public static List<Spell> getRandomSpells(int count) {
        List<Spell> randomized = new ArrayList<>(spells);
        Collections.shuffle(randomized);
        return randomized.subList(0, Math.min(randomized.size(), count));
    }
}
