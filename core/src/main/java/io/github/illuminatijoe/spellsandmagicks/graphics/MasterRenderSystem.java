package io.github.illuminatijoe.spellsandmagicks.graphics;

import com.badlogic.ashley.core.EntitySystem;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.FrameBuffer;
import com.badlogic.gdx.graphics.glutils.ShaderProgram;
import com.badlogic.gdx.utils.Disposable;
import java.util.List;

public class MasterRenderSystem extends EntitySystem implements Disposable {
    private final FrameBuffer frameBuffer;
    private final SpriteBatch batch;
    private final OrthographicCamera camera;
    private ShaderProgram shader;
    private final List<RenderableSystem> renderSystems;

    public MasterRenderSystem(OrthographicCamera camera, List<RenderableSystem> renderSystems) {
        this.camera = camera;
        this.renderSystems = renderSystems;
        this.batch = new SpriteBatch();

        frameBuffer = new FrameBuffer(Pixmap.Format.RGBA8888, Gdx.graphics.getWidth(), Gdx.graphics.getHeight(), false);
        initShader();
    }

    private void initShader() {
        ShaderProgram.pedantic = false;
        shader = new ShaderProgram(
            Gdx.files.internal("shaders/smooth.vert"),
            Gdx.files.internal("shaders/smooth.frag")
        );
        if (!shader.isCompiled()) {
            System.err.println("Shader compilation failed: " + shader.getLog());
        }
    }

    @Override
    public void update(float deltaTime) {
        frameBuffer.begin();
            Gdx.gl.glClearColor(0, 0, 0, 1);
            Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

            for (RenderableSystem system : renderSystems) {
                system.renderToFrameBuffer(deltaTime, batch, camera);
            }
        frameBuffer.end();

        Texture fbTexture = frameBuffer.getColorBufferTexture();
        fbTexture.setFilter(Texture.TextureFilter.Nearest, Texture.TextureFilter.Linear);
        fbTexture.bind(0);
        shader.setUniformi("u_texture", 0);
        shader.setUniformMatrix("u_projTrans", batch.getProjectionMatrix());

        // shaders
        batch.setShader(shader);
        shader.bind();
        shader.setUniformf("u_texelSize", 1.0f / fbTexture.getWidth(), 1.0f / fbTexture.getHeight());

        // idk what this does, but it fixed the texture moving around while walking
        batch.setProjectionMatrix(camera.projection);
        batch.begin();
            // Draw final batch
            batch.draw(fbTexture, -Gdx.graphics.getWidth() / 2.0f, Gdx.graphics.getHeight() / 2.0f, Gdx.graphics.getWidth(), -Gdx.graphics.getHeight());
        batch.end();

        // reset shader
        batch.setShader(null);
    }

    @Override
    public void dispose() {
        frameBuffer.dispose();
        batch.dispose();
        shader.dispose();
    }
}
