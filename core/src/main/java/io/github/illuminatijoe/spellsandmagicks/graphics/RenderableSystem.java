package io.github.illuminatijoe.spellsandmagicks.graphics;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public interface RenderableSystem {
     //// Render this system’s contents to the given batch using the provided camera.
    void renderToFrameBuffer(float deltaTime, SpriteBatch batch, OrthographicCamera camera);
}
