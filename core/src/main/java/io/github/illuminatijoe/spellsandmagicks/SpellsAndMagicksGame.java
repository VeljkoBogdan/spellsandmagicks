package io.github.illuminatijoe.spellsandmagicks;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import io.github.illuminatijoe.spellsandmagicks.ui.MainMenuScreen;

public class SpellsAndMagicksGame extends Game {
    public SpriteBatch batch;
    public BitmapFont bitmapFont;

    @Override
    public void create() {
        batch = new SpriteBatch();
        bitmapFont = new BitmapFont();

        // Start with the SpellsAndMagicksGame Menu
        setScreen(new MainMenuScreen(this));
    }

    @Override
    public void dispose() {
        batch.dispose();
        bitmapFont.dispose();
        super.dispose();
    }
}
