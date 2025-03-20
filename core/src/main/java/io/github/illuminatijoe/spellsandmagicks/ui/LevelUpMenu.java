package io.github.illuminatijoe.spellsandmagicks.ui;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.EntitySystem;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.Window;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import io.github.illuminatijoe.spellsandmagicks.game.entities.Player;
import io.github.illuminatijoe.spellsandmagicks.game.entities.components.*;
import io.github.illuminatijoe.spellsandmagicks.game.spells.*;
import io.github.illuminatijoe.spellsandmagicks.graphics.AssetLoader;
import io.github.illuminatijoe.spellsandmagicks.util.ZIndex;

import java.util.List;

public class LevelUpMenu {
    private final GameScreen gameScreen;
    private boolean visible = false;
    private final SpriteBatch batch;
    private final BitmapFont font;
    private final Stage stage;
    private Texture background;
    private Label tooltipLabel;
    private Window tooltipWindow;
    private Skin skin;

    // Spells
    private Engine engine;
    private List<Spell> spells;
    private Player player;
    private final ComponentMapper<SpellComponent> spellMapper = ComponentMapper.getFor(SpellComponent.class);

    public LevelUpMenu(GameScreen gameScreen, Engine engine, Player player) {
        this.engine = engine;
        this.player = player;
        this.gameScreen = gameScreen;
        this.batch = new SpriteBatch();
        this.font = new BitmapFont();
        this.stage = new Stage();

        Pixmap pixmap = new Pixmap(1, 1, Pixmap.Format.RGBA8888);
        pixmap.setColor(0, 0, 0.1f, 0.75f);
        pixmap.fill();
        background = new Texture(pixmap);
        pixmap.dispose();

        skin = new Skin(Gdx.files.internal("textures/ui/pixthulhu-ui.json"));

        tooltipLabel = new Label("", skin);
        tooltipWindow = new Window("", skin);
        tooltipWindow.add(tooltipLabel).pad(10);
        tooltipWindow.pack();
        tooltipWindow.toFront();
        tooltipWindow.setVisible(false);
    }

    public void show() {
        visible = true;
        this.spells = SpellLibrary.getRandomSpells(3);
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
            for (Spell spell : spells) {
                TextButton optionButton = new TextButton(spell.getName(), skin);
                optionButton.setSize(600, 100);
                optionButton.setPosition(Gdx.graphics.getWidth() / 2f - 300, Gdx.graphics.getHeight() / 2f + 200 - 200 * pos);

                optionButton.addListener(new ClickListener() {
                    @Override
                    public void clicked(InputEvent event, float x, float y) {
                        addSpellSystems(spell);
                        hide();
                    }
                });

                optionButton.addListener(new InputListener() {
                    @Override
                    public void enter(InputEvent event, float x, float y, int pointer, Actor fromActor) {
                        tooltipLabel.setText(spell.getDescription());
                        tooltipWindow.pack();
                        tooltipWindow.toFront();
                        tooltipWindow.setPosition(Gdx.graphics.getWidth() / 2f - tooltipWindow.getWidth() / 2f,
                            Gdx.graphics.getHeight() - Gdx.graphics.getHeight() * 0.2f);
                        tooltipWindow.setVisible(true);
                    }

                    @Override
                    public void exit(InputEvent event, float x, float y, int pointer, Actor toActor) {
                        tooltipWindow.setVisible(false);
                    }
                });

                stage.addActor(optionButton);
                pos++;
            }

            stage.addActor(tooltipWindow);
        batch.end();

        stage.act();
        stage.draw();
    }

    private void addSpellSystems(Spell spell) {
        SpellComponent spellComponent = spellMapper.get(player);

        if (spell instanceof ElectricityAuraSpell electricityAuraSpell) {
            ElectricityAuraComponent auraComponent = player.getComponent(ElectricityAuraComponent.class);
            if (auraComponent == null) {
                player.add(electricityAuraSpell.getElectricityAuraComponent());
                Entity aura = new Entity();
                aura.add(new PositionComponent(player.getComponent(PositionComponent.class).getPosition().cpy()));
                aura.add(new AnimationComponent(AssetLoader.electricityAuraAnimation));
                aura.add(new FollowPlayerComponent(-135, -135));
                engine.addEntity(aura);

                spellComponent.addSpell(electricityAuraSpell);
            } else {
                auraComponent.upgrade();
            }
        } else if (spell instanceof ExplosionMagick explosionMagick) {
            player.add(explosionMagick.getComponent());
            engine.addSystem(explosionMagick.getEntityMovingSystem());
            spellComponent.addSpell(explosionMagick);
        } else if (spell instanceof PoisonMagick poisonMagick) {
            player.add(poisonMagick.getComponent());
            engine.addSystem(poisonMagick.poisonSystem);
            spellComponent.addSpell(poisonMagick);
        } else {
            EntitySystem movingSystem = spell.getEntityMovingSystem();
            EntitySystem shootingSystem = spell.getEntityShootingSystem();

            if (movingSystem != null) engine.addSystem(movingSystem);
            if (shootingSystem != null) engine.addSystem(shootingSystem);

            spellComponent.addSpell(spell);
        }
    }

    public void dispose() {
        batch.dispose();
        font.dispose();
        background.dispose();
    }
}
