package io.github.illuminatijoe.spellsandmagicks;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.ScreenUtils;
import io.github.illuminatijoe.spellsandmagicks.game.Game;

public class Main extends ApplicationAdapter {
    public Game game;

    @Override
    public void create() {
        // Start of game
        game = new Game();
    }

    @Override
    public void render() {
        // Game update
        game.render();
    }

    @Override
    public void dispose() {
        // Game disposal
        game.dispose();
    }
}
