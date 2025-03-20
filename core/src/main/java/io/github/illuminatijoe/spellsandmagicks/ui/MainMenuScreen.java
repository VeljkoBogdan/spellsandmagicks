package io.github.illuminatijoe.spellsandmagicks.ui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import io.github.illuminatijoe.spellsandmagicks.SpellsAndMagicksGame;

public class MainMenuScreen implements Screen {
    private final SpellsAndMagicksGame game;
    private final Stage stage;
    private final SpriteBatch batch;
    private final OrthographicCamera camera;
    private final Texture background;
    private GameScreen gameScreen = null;
    private final Skin skin;

    public MainMenuScreen(SpellsAndMagicksGame game) {
        this.game = game;
        this.batch = new SpriteBatch();
        this.camera = new OrthographicCamera(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        this.stage = new Stage();
        this.skin = new Skin(Gdx.files.internal("textures/ui/pixthulhu-ui.json"));
        this.background = new Texture("textures/ui/menu_background.png");

        Gdx.input.setInputProcessor(stage);
        createUI();
    }

    private void createUI() {
        float x = Gdx.graphics.getWidth() / 2f - 200;
        TextButton playButton = createButton("Play", x, Gdx.graphics.getHeight() / 2f, new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                if (gameScreen == null) gameScreen = new GameScreen(game, MainMenuScreen.this);

                game.setScreen(gameScreen);
                Gdx.input.setInputProcessor(null);
            }
        });

        TextButton exitButton = createButton("Exit", x, Gdx.graphics.getHeight() / 2f - 180, new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                Gdx.app.exit();
            }
        });

        stage.addActor(exitButton);
        stage.addActor(playButton);
    }

    private TextButton createButton(String text, float x, float y, ClickListener clickListener) {
        TextButton playButton = new TextButton(text, skin);
        playButton.setSize(500, 120);
        playButton.setPosition(x - playButton.getLabel().getWidth() / 2f, y - playButton.getLabel().getHeight() / 2f);
        playButton.addListener(clickListener);
        return playButton;
    }

    @Override
    public void show() {
        Gdx.input.setInputProcessor(stage);
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        batch.setProjectionMatrix(camera.combined);
        batch.begin();
            batch.draw(background, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        batch.end();

        stage.act(delta);
        stage.draw();

        if (Gdx.input.isKeyJustPressed(Input.Keys.ESCAPE)) {
            Gdx.app.exit();
        }
    }

    @Override
    public void resize(int width, int height) {
        camera.setToOrtho(false, width, height);
        stage.getViewport().update(width, height, true);
    }

    @Override
    public void pause() {}

    @Override
    public void resume() {}

    @Override
    public void hide() {}

    @Override
    public void dispose() {
        stage.dispose();
        batch.dispose();
        gameScreen.dispose();
        background.dispose();
    }
}
