package io.github.illuminatijoe.spellsandmagicks.game.entities.systems;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Disposable;
import com.badlogic.gdx.utils.ScreenUtils;
import io.github.illuminatijoe.spellsandmagicks.game.entities.components.ControllableComponent;
import io.github.illuminatijoe.spellsandmagicks.game.entities.components.PositionComponent;

public class TileRendererSystem extends IteratingSystem implements Disposable {
    private final SpriteBatch batch;
    private final Texture texture;
    private final int tileSize;
    private final int renderDistance;
    private final OrthographicCamera camera;
    private ComponentMapper<PositionComponent> pm = ComponentMapper.getFor(PositionComponent.class);

    public TileRendererSystem(OrthographicCamera camera) {
        super(Family.all(PositionComponent.class, ControllableComponent.class).get());

        this.tileSize = 512;
        this.renderDistance = 3;
        batch = new SpriteBatch();
        texture = new Texture("textures/tiles/grass.png");

        this.camera = camera;
    }

    @Override
    protected void processEntity(Entity entity, float v) {
        PositionComponent positionComponent = pm.get(entity);
        Vector2 pos = positionComponent.getPosition();

        int playerTileX = (int) (pos.x/tileSize);
        int playerTileY = (int) (pos.y/tileSize);

        ScreenUtils.clear(Color.BLACK);
        batch.begin();
        batch.setProjectionMatrix(camera.combined);
        for (int x = -renderDistance; x <= renderDistance; x++) {
            for (int y = -renderDistance; y <= renderDistance; y++) {
                int worldX = (playerTileX + x) * tileSize;
                int worldY = (playerTileY + y) * tileSize;
                batch.draw(texture, worldX, worldY, tileSize, tileSize);
            }
        }
        batch.end();
    }

    @Override
    public void dispose() {
        batch.dispose();
        texture.dispose();
    }
}
