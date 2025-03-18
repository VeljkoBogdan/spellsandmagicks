package io.github.illuminatijoe.spellsandmagicks.graphics;

import com.badlogic.ashley.core.*;
import com.badlogic.ashley.utils.ImmutableArray;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import io.github.illuminatijoe.spellsandmagicks.game.entities.components.AnimationComponent;
import io.github.illuminatijoe.spellsandmagicks.game.entities.components.PositionComponent;

public class RenderSystem extends EntitySystem implements RenderableSystem {
    private final ComponentMapper<PositionComponent> pm = ComponentMapper.getFor(PositionComponent.class);
    private final ComponentMapper<AnimationComponent> am = ComponentMapper.getFor(AnimationComponent.class);
    private ImmutableArray<Entity> entities;

    public RenderSystem(Engine engine) {
        entities = engine.getEntitiesFor(Family.all(AnimationComponent.class, PositionComponent.class).get());
    }

    @Override
    public void renderToFrameBuffer(float deltaTime, SpriteBatch batch, OrthographicCamera camera) {
        batch.setProjectionMatrix(camera.combined);
        batch.begin();
        for (Entity entity : entities) {
            PositionComponent position = pm.get(entity);
            AnimationComponent animation = am.get(entity);

            if (!animation.idle) {
                animation.stateTime += deltaTime;
            }

            TextureRegion currentFrame = animation.getKeyFrame();

            // Flip texture if needed:
            if (animation.facingLeft != currentFrame.isFlipX()) {
                currentFrame.flip(true, false);
            }

            batch.draw(currentFrame, position.getPosition().x, position.getPosition().y);
        }
        batch.end();
    }

    @Override
    public void addedToEngine(com.badlogic.ashley.core.Engine engine) {
        entities = engine.getEntitiesFor(Family.all(AnimationComponent.class, PositionComponent.class).get());
    }
}
