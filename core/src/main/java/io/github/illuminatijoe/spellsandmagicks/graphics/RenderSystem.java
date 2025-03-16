package io.github.illuminatijoe.spellsandmagicks.graphics;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.Disposable;
import com.badlogic.gdx.utils.ScreenUtils;
import io.github.illuminatijoe.spellsandmagicks.game.entities.components.AnimationComponent;
import io.github.illuminatijoe.spellsandmagicks.game.entities.components.PositionComponent;

public class RenderSystem extends IteratingSystem implements Disposable {
    private final SpriteBatch batch;
    private OrthographicCamera camera;
    private final ComponentMapper<PositionComponent> pm = ComponentMapper.getFor(PositionComponent.class);
    private final ComponentMapper<AnimationComponent> am = ComponentMapper.getFor(AnimationComponent.class);

    public RenderSystem(OrthographicCamera camera) {
        super(Family.all(AnimationComponent.class, PositionComponent.class).get());
        this.batch = new SpriteBatch();
        this.camera = camera;
    }

    @Override
    protected void processEntity(Entity entity, float deltaTime) {
        PositionComponent position = pm.get(entity);
        AnimationComponent animation = am.get(entity);

        if (!animation.idle) {
            animation.stateTime += deltaTime;
        }

        TextureRegion currentFrame = animation.getKeyFrame();

        // Flip texture if needed
        if (animation.facingLeft != currentFrame.isFlipX()) {
            currentFrame.flip(true, false);
        }

        batch.draw(currentFrame, position.getPosition().x, position.getPosition().y);
    }

    @Override
    public void update(float deltaTime) {
        ScreenUtils.clear(Color.BLACK);
        batch.begin();
        batch.setProjectionMatrix(camera.combined);
            super.update(deltaTime); // Calls processEntity for each entity
        batch.end();
    }

    @Override
    public void dispose() {
        batch.dispose();
    }
}
