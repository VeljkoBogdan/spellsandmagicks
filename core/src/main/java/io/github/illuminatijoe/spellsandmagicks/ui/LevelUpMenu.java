package io.github.illuminatijoe.spellsandmagicks.ui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

public class LevelUpMenu {
    private final GameScreen gameScreen;
    private boolean visible = false;
    private final SpriteBatch batch;
    private final BitmapFont font;
    private final Stage stage;
    private Texture background;

    private final String[] options = {
        "Increase Health",
        "Increase Damage",
        "Increase Speed"
    };

    public LevelUpMenu(GameScreen gameScreen) {
        this.gameScreen = gameScreen;
        this.batch = new SpriteBatch();
        this.font = new BitmapFont();
        this.stage = new Stage();

        Pixmap pixmap = new Pixmap(1, 1, Pixmap.Format.RGBA8888);
        pixmap.setColor(0, 0, 0.1f, 0.75f);
        pixmap.fill();
        background = new Texture(pixmap);
        pixmap.dispose();
    }

    public void show() {
        visible = true;
        Gdx.input.setInputProcessor(stage);
    }

    public void hide() {
        visible = false;
        gameScreen.resumeGame();
    }

    public boolean isVisible() {
        return visible;
    }

    public void render() {
        if (!visible) return;

        batch.begin();
            // opacity bg
            batch.draw(background, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());

            int pos = 0;
            for (String option : options) {
                Skin skin = new Skin(Gdx.files.internal("textures/ui/pixthulhu-ui.json"));

                TextButton optionButton = new TextButton(option, skin);
                optionButton.setSize(600, 100);
                optionButton.setPosition(Gdx.graphics.getWidth() / 2f - 300, Gdx.graphics.getHeight() / 2f + 200 - 200 * pos);

                optionButton.addListener(new ClickListener() {
                    @Override
                    public void clicked(InputEvent event, float x, float y) {
                        hide();
                    }
                });

                pos++;
                this.stage.addActor(optionButton);
            }

        batch.end();

        stage.act();
        stage.draw();
    }


    public void dispose() {
        batch.dispose();
        font.dispose();
        background.dispose();
    }
}
