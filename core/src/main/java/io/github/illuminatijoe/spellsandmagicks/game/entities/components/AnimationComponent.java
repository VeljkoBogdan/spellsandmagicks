package io.github.illuminatijoe.spellsandmagicks.game.entities.components;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class AnimationComponent implements Component {
    public Animation<TextureRegion> animation;
    public float stateTime = 0f;
    public boolean facingLeft = false;
    public boolean idle = false;
    public Color tint = Color.WHITE;

    public AnimationComponent(Animation<TextureRegion> animation) {
        this.animation = animation;
    }

    public TextureRegion getKeyFrame() {
        return animation.getKeyFrame(stateTime);
    }
}
