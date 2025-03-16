package io.github.illuminatijoe.spellsandmagicks;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.ScreenUtils;
import io.github.illuminatijoe.spellsandmagicks.game.Game;

public class Main extends ApplicationAdapter {
    public Game game;

    public SpriteBatch debugBatch;
    public BitmapFont bitmapFont;

    @Override
    public void create() {
        // Start of game
        game = new Game();
        bitmapFont = new BitmapFont();
        debugBatch = new SpriteBatch();
    }

    @Override
    public void render() {
        // Game update
        game.render();

        if (Gdx.input.isKeyJustPressed(Input.Keys.ESCAPE)) {
            Gdx.app.exit();
        }

        if (Gdx.input.isKeyJustPressed(Input.Keys.TAB)) {
            game.togglePause();
        }

        int textOffset = Gdx.graphics.getHeight() - 10;
        debugBatch.begin();
            bitmapFont.draw(debugBatch, String.valueOf(Gdx.graphics.getFramesPerSecond()) + " FPS", 10, textOffset);
            textOffset-=20;
            bitmapFont.draw(debugBatch, String.valueOf(Gdx.app.getJavaHeap() / 1000 / 1000) + " MB", 10, textOffset);
        debugBatch.end();
    }

    @Override
    public void dispose() {
        // Game disposal
        game.dispose();
        debugBatch.dispose();
        bitmapFont.dispose();
    }
}
