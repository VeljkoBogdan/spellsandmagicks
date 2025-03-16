package io.github.illuminatijoe.spellsandmagicks.game.entities;

import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.math.Vector2;
import io.github.illuminatijoe.spellsandmagicks.game.entities.components.DirectionComponent;
import io.github.illuminatijoe.spellsandmagicks.game.entities.components.PositionComponent;

public class Player extends Entity {

    public void move(float delta) {
        PositionComponent pos = this.getComponent(PositionComponent.class);
        DirectionComponent dir = this.getComponent(DirectionComponent.class);

        if (pos != null && dir != null) {
            dir.direction.setZero();
            handleInput(pos.position, dir.direction);

            pos.position.add(dir.direction.scl(delta * 400));
        }
    }

    private void handleInput(Vector2 pos, Vector2 dir) {
        dir = Vector2.Zero;

        if (Gdx.input.isKeyPressed(Input.Keys.W)) {
            dir.y += 1;
        }
        if (Gdx.input.isKeyPressed(Input.Keys.A)) {
            dir.x -= 1;
        }
        if (Gdx.input.isKeyPressed(Input.Keys.S)) {
            dir.y -= 1;
        }
        if (Gdx.input.isKeyPressed(Input.Keys.D)) {
            dir.x += 1;
        }

        dir.nor();
    }
}
