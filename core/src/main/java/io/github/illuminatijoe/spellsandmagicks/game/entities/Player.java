package io.github.illuminatijoe.spellsandmagicks.game.entities;

import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.math.Vector2;
import io.github.illuminatijoe.spellsandmagicks.game.entities.components.*;
import io.github.illuminatijoe.spellsandmagicks.graphics.AssetLoader;

public class Player extends Entity {

    public Player(AssetLoader assetLoader) {
        this.add(new AnimationComponent(assetLoader.getPlayerAnimation()));
        this.add(new PositionComponent(new Vector2(100, 100)));
        this.add(new VelocityComponent());
        this.add(new ControllableComponent());
        this.add(new CameraFollowComponent());
        this.add(new CollisionComponent());
    }
}
