package io.github.illuminatijoe.spellsandmagicks.graphics;

import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Disposable;
import com.badlogic.gdx.utils.ScreenUtils;
import io.github.illuminatijoe.spellsandmagicks.game.entities.components.AnimationComponent;
import io.github.illuminatijoe.spellsandmagicks.game.entities.components.PositionComponent;

import java.util.ArrayDeque;

public class RenderSystem implements Disposable {
    private final SpriteBatch batch;

    public static ArrayDeque<Entity> entities = new ArrayDeque<>();

    public RenderSystem() {
        this.batch = new SpriteBatch();
    }

    public void render() {
        ScreenUtils.clear(Color.BLACK);
        batch.begin();
            renderEntities(batch);
        batch.end();
    }

    private void renderEntities(SpriteBatch batch) {
        for (Entity entity : entities) {
            PositionComponent positionComponent = entity.getComponent(PositionComponent.class);
            AnimationComponent animation = entity.getComponent(AnimationComponent.class);

            float deltaTime = Gdx.graphics.getDeltaTime();
            if (animation != null && positionComponent != null) {
                animation.stateTime += deltaTime;
                TextureRegion currentFrame = animation.getKeyFrame();
                Vector2 pos = positionComponent.getPosition();

                batch.draw(currentFrame, pos.x, pos.y);
            }
        }
    }

    @Override
    public void dispose() {
        batch.dispose();
    }
}
