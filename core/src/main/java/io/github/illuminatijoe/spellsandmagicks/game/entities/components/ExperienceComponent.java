package io.github.illuminatijoe.spellsandmagicks.game.entities.components;

import com.badlogic.ashley.core.Component;

public class ExperienceComponent implements Component {
    public float experience = 0f;
    public int level = 1;
    public float expToNextLevel = 100f;

    public void addXp(float amount) {
        experience += amount;

        checkProgress();
    }

    public void checkProgress() {
        if (experience >= expToNextLevel) {
            levelUp();
        }
    }

    public void levelUp() {
        experience = 0f;
        level++;
        calculateExpToNextLevel();
    }

    public void calculateExpToNextLevel() {
        expToNextLevel = (expToNextLevel * 1.5f);
    }
}
