package io.github.illuminatijoe.spellsandmagicks.game.entities.projectiles;

import com.badlogic.ashley.core.Component;

public class TimedDetonationComponent implements Component {
    public float cooldown;
    public Runnable onTimeOut;

    public TimedDetonationComponent(float cooldown, Runnable onTimeOut) {
        this.cooldown = cooldown;
        this.onTimeOut = onTimeOut;
    }

    public void detonate() {
        this.onTimeOut.run();
    }
}
