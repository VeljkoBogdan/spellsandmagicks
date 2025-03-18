package io.github.illuminatijoe.spellsandmagicks.ui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import io.github.illuminatijoe.spellsandmagicks.SpellsAndMagicksGame;
import io.github.illuminatijoe.spellsandmagicks.game.Game;

public class GameScreen implements Screen {
    private final SpellsAndMagicksGame game;
    private final Game gameLogic;
    private final LevelUpMenu levelUpMenu;

    public GameScreen(SpellsAndMagicksGame game) {
        this.game = game;
        this.gameLogic = new Game(game, this);
        this.levelUpMenu = new LevelUpMenu(this);
    }

    @Override
    public void show() {
    }

    public void onLevelUp() {
        gameLogic.paused = true;
        levelUpMenu.show();
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        gameLogic.render();


        if (levelUpMenu.isVisible()) {
            levelUpMenu.render();
        }

        if (Gdx.input.isKeyJustPressed(Input.Keys.L)) {
            onLevelUp();
        }
        if (Gdx.input.isKeyJustPressed(Input.Keys.ESCAPE)) {
            game.setScreen(new MainMenuScreen(game));
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
    }

    public void resumeGame() {
        gameLogic.paused = false;
    }
}
