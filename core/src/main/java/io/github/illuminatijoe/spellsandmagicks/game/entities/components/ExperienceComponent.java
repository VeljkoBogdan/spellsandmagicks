package io.github.illuminatijoe.spellsandmagicks.game.entities.components;

import com.badlogic.ashley.core.Component;
import io.github.illuminatijoe.spellsandmagicks.game.Game;

public class ExperienceComponent implements Component {
    public float experience = 0f;
    public int level = 1;
    public float expToNextLevel = 100f;
    public Game game;

    public ExperienceComponent(Game game) {
        this.game = game;
    }

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

        game.onLevelUp();
    }

    public void calculateExpToNextLevel() {
        expToNextLevel = (expToNextLevel * 1.35f);
    }
}
