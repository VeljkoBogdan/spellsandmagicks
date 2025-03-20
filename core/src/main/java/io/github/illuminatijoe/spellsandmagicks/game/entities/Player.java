package io.github.illuminatijoe.spellsandmagicks.game.entities;

import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.math.Vector2;
import io.github.illuminatijoe.spellsandmagicks.game.Game;
import io.github.illuminatijoe.spellsandmagicks.game.entities.components.*;
import io.github.illuminatijoe.spellsandmagicks.graphics.AssetLoader;
import io.github.illuminatijoe.spellsandmagicks.util.Collision;
import io.github.illuminatijoe.spellsandmagicks.util.ZIndex;

public class Player extends Entity {

    public Player(Game game) {
        this.add(new AnimationComponent(AssetLoader.playerAnimation));
        this.add(new PositionComponent(new Vector2(0, 0)));
        this.add(new VelocityComponent());
        this.add(new ControllableComponent());
        this.add(new CameraFollowComponent());
        this.add(new CollisionComponent(Collision.FRIENDLY));
        this.add(new HealthComponent(200f, 0.75f, game::playerKilled));
        this.add(new PlayerComponent());
        this.add(new ExperienceComponent(game));
        this.add(new SpellComponent());
        this.add(new ZIndexComponent(ZIndex.PLAYER));
        this.add(new RegenComponent(4f, 2f));
    }
}
