package io.github.illuminatijoe.spellsandmagicks.ui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import io.github.illuminatijoe.spellsandmagicks.SpellsAndMagicksGame;
import io.github.illuminatijoe.spellsandmagicks.game.Game;
import io.github.illuminatijoe.spellsandmagicks.graphics.AssetLoader;

public class GameScreen implements Screen {
    private final SpellsAndMagicksGame game;
    private final Game gameLogic;
    private final LevelUpMenu levelUpMenu;
    private final MainMenuScreen mainMenuScreen;

    public GameScreen(SpellsAndMagicksGame game, MainMenuScreen mainMenuScreen) {
        this.mainMenuScreen = mainMenuScreen;
        this.game = game;
        this.gameLogic = new Game(game, this);
        this.levelUpMenu = new LevelUpMenu(this, gameLogic.engine, gameLogic.player);
    }

    @Override
    public void show() {
    }

    public void onLevelUp() {
        gameLogic.paused = true;
        levelUpMenu.show();
        AssetLoader.getLevelUpSound().play(0.5f);
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        gameLogic.render();

        if (levelUpMenu.isVisible()) {
            levelUpMenu.render();
        }

        // TODO: debug; remove on release
        if (Gdx.input.isKeyJustPressed(Input.Keys.L)) {
            onLevelUp();
        }
        if (Gdx.input.isKeyJustPressed(Input.Keys.ESCAPE)) {
            game.setScreen(mainMenuScreen);
        }
    }

    @Override
    public void resize(int width, int height) {
    }

    @Override
    public void pause() {
    }

    @Override
    public void resume() {
    }

    @Override
    public void hide() {
    }

    @Override
    public void dispose() {
        gameLogic.dispose();
        levelUpMenu.dispose();
    }

    public void resumeGame() {
        gameLogic.paused = false;
    }
}
