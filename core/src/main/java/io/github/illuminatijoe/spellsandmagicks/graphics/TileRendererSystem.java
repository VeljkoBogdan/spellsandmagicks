package io.github.illuminatijoe.spellsandmagicks.graphics;

import com.badlogic.ashley.core.*;
import com.badlogic.ashley.utils.ImmutableArray;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Disposable;
import io.github.illuminatijoe.spellsandmagicks.game.entities.components.ControllableComponent;
import io.github.illuminatijoe.spellsandmagicks.game.entities.components.PositionComponent;

public class TileRendererSystem extends EntitySystem implements RenderableSystem, Disposable {
    private final Texture texture;
    private final int tileSize;
    private final int renderDistance;
    private final ComponentMapper<PositionComponent> pm = ComponentMapper.getFor(PositionComponent.class);
    private ImmutableArray<Entity> entities;

    public TileRendererSystem(Engine engine) {
        entities = engine.getEntitiesFor(Family.all(PositionComponent.class, ControllableComponent.class).get());
        this.tileSize = 512;
        this.renderDistance = 4;
        texture = new Texture("textures/tiles/grass.png");
    }

    @Override
    public void renderToFrameBuffer(float deltaTime, SpriteBatch batch, OrthographicCamera camera) {
        batch.setProjectionMatrix(camera.combined);
        batch.begin();

        for (Entity entity : entities) {
            PositionComponent posComp = pm.get(entity);
            Vector2 pos = posComp.getPosition();
            int playerTileX = (int)(pos.x / tileSize);
            int playerTileY = (int)(pos.y / tileSize);

            for (int x = -renderDistance; x <= renderDistance; x++) {
                for (int y = -renderDistance; y <= renderDistance; y++) {
                    int worldX = (playerTileX + x) * tileSize;
                    int worldY = (playerTileY + y) * tileSize;
                    batch.draw(texture, worldX, worldY, tileSize, tileSize);
                }
            }
        }
        batch.end();
    }

    @Override
    public void addedToEngine(com.badlogic.ashley.core.Engine engine) {
        entities = engine.getEntitiesFor(Family.all(PositionComponent.class, ControllableComponent.class).get());
    }

    @Override
    public void dispose() {
        texture.dispose();
    }
}
