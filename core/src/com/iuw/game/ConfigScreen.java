package com.iuw.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.SelectBox;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Array;
import org.jetbrains.annotations.NotNull;

/**
 * System configuration screen
 * @author iuw-team
 */
public class ConfigScreen extends ScreenAdapter {
    private final Process game;
    private final Stage stage;
    /**
     * Creating a standard ConfigScreen class
     * @param game - Process
     */
    public ConfigScreen(@NotNull final Process game) {
        this.game = game;
        game.setCurrentScreen(1);
        stage = game.getStage();
        InitBoxes();
        InitButtons();
        InitLabels();
    }
    /**
     * Called when the screen appears
     */
    @Override
    public void show() {
        Gdx.input.setInputProcessor(stage);
    }
    /**
     * Drawing the user interface
     */
    @Override
    public void render(float delta) {
        stage.act();
        stage.draw();
        if (Gdx.input.isKeyPressed(Input.Keys.ENTER)) game.setScreen(game.GetScreenByIndex(3));

    }
    /**
     * Called when the selected screen is hidden
     */
    @Override
    public void hide() {
        dispose();
    }
    /**
     * Destroys all created objects
     */
    @Override
    public void dispose() {
        stage.dispose();
    }
    /**
     * Initialising SelectBox
     */
    private void InitBoxes() {
        final float posX = (GameSettings.SCREEN_WIDTH - GameSettings.BOX_WIDTH) / 2f;
        float posY = 500f;
        Array<Array<String>> boxItems = new Array<>();
        boxItems.add(
                new Array<>(new String[]{"4", "5", "6", "7"}),
                new Array<>(new String[]{"1", "2", "3", "4"}),
                new Array<>(new String[]{"1", "3", "5", "7", "10"}),
                new Array<>(new String[]{"Ia", "Ib", "II", "III", "IV", "V"})

        );
        for (int i = 0; i < 4; i++, posY -= 100f) {
            final SelectBox<String> box = game.getSelectBox();
            box.setPosition(posX, posY);
            box.setSize(GameSettings.BOX_WIDTH, GameSettings.BOX_HEIGHT);
            box.setItems(boxItems.get(i));
            box.setSelectedIndex(GameSettings.getIndexSystemVariable(i));
            final Integer index = i;
            box.addListener(new ChangeListener() {
                @Override
                public void changed(ChangeEvent event, Actor actor) {
                    GameSettings.setIndexSystemVariables(index, box.getSelectedIndex());
                }
            });
            this.stage.addActor(box);
        }
    }
    /**
     * Initialising Buttons
     */
    private void InitButtons() {
        final String[] butName = new String[]{"X", "Play"};
        final float[][] butPos = new float[][]{
                {50f, 500f},
                {GameSettings.SCREEN_WIDTH / 2f - GameSettings.SMALL_BUTTON_WIDTH / 2f, 150f}};
        for (int i = 0; i < 2; i++) {
            final TextButton button = game.getTextButton(butName[i]);
            button.setPosition(butPos[i][0], butPos[i][1]);
            button.setSize(GameSettings.SMALL_BUTTON_WIDTH, GameSettings.SMALL_BUTTON_HEIGHT);

            final int index = (i == 0) ? i : 3;
            button.addListener(new ClickListener() {
                @Override
                public void clicked(InputEvent event, float x, float y) {
                    game.setScreen(game.GetScreenByIndex(index));
                }
            });
            stage.addActor(button);
        }
    }
    /**
     * Initialising Labels
     */
    private void InitLabels() {
        final String[] labelText = new String[]{
                "Number of planet",
                "Quantity of goods ",
                "Number of asteroids",
                "System star type",
        };
        final float posX = (GameSettings.SCREEN_WIDTH / 2f);
        float posY = 530f;
        for (int i = 0; i < 4; i++, posY -= 100f) {
            final Label label = game.getLabel(labelText[i]);
            label.setFontScale(GameSettings.TYPE);
            label.setPosition(posX - label.getWidth()/1.3f, posY);
            label.setSize(GameSettings.BOX_WIDTH, GameSettings.BOX_HEIGHT);
            stage.addActor(label);
        }
    }
}
