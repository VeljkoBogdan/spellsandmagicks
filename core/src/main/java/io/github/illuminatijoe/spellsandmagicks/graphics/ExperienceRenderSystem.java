package io.github.illuminatijoe.spellsandmagicks.graphics;

import com.badlogic.ashley.core.*;
import com.badlogic.ashley.utils.ImmutableArray;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.Disposable;
import io.github.illuminatijoe.spellsandmagicks.game.entities.components.ExperienceComponent;
import io.github.illuminatijoe.spellsandmagicks.game.entities.components.HealthComponent;
import io.github.illuminatijoe.spellsandmagicks.game.entities.components.PositionComponent;

public class ExperienceRenderSystem extends EntitySystem implements RenderableSystem, Disposable {
    private final ComponentMapper<ExperienceComponent> xpMapper = ComponentMapper.getFor(ExperienceComponent.class);
    private final ComponentMapper<PositionComponent> positionMapper = ComponentMapper.getFor(PositionComponent.class);
    private ImmutableArray<Entity> entities;
    private final Texture whiteTexture;

    public float barWidth = 50f;
    public float barHeight = 5f;
    public float xOffset = -barWidth / 5f;
    public float yOffset = 29f;

    public ExperienceRenderSystem(Engine engine) {
        entities = engine.getEntitiesFor(Family.all(ExperienceComponent.class, PositionComponent.class).get());

        Pixmap pixmap = new Pixmap(1, 1, Pixmap.Format.RGBA8888);
        pixmap.setColor(Color.WHITE);
        pixmap.fill();
        whiteTexture = new Texture(pixmap);
        pixmap.dispose();
    }

    @Override
    public void addedToEngine(Engine engine) {
        entities = engine.getEntitiesFor(Family.all(ExperienceComponent.class, PositionComponent.class).get());
    }

    @Override
    public void renderToFrameBuffer(float deltaTime, SpriteBatch batch, OrthographicCamera camera) {
        batch.setProjectionMatrix(camera.combined);
        batch.begin();

        for (Entity entity : entities) {
            ExperienceComponent experienceComponent = xpMapper.get(entity);
            PositionComponent positionComponent = positionMapper.get(entity);

            float x = positionComponent.position.x + xOffset;
            float y = positionComponent.position.y - yOffset;
            float hpPercentage = experienceComponent.experience / experienceComponent.expToNextLevel;

            batch.setColor(Color.BLACK);
            batch.draw(whiteTexture, x-4, y-4, barWidth + 8, barHeight + 8);

            batch.draw(whiteTexture, x, y, barWidth * hpPercentage, barHeight);

            batch.setColor(Color.BLUE);
            batch.draw(whiteTexture, x, y, barWidth * hpPercentage, barHeight);
        }

        batch.setColor(Color.WHITE);
        batch.end();
    }

    @Override
    public void dispose() {
        whiteTexture.dispose();
    }
}
