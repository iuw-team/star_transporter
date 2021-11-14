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

public class ConfigScreen extends ScreenAdapter {
    private final Process game;
    private final Stage stage;
    private final OrthographicCamera camera;
    private final Integer defMass = 10;
    private final Integer[][] boxVariables = new Integer[][]{
            new Integer[]{4, 5, 6, 7}, //list for planet number
            new Integer[]{1, 2, 3, 4}, //number of loads for delivery
            new Integer[]{10, 20, 30}, //speed of the ship
            new Integer[]{defMass, 2 * defMass, 3 * defMass,
                    defMass, 2 * defMass, 3 * defMass}, //mass of definite star
    };

    public ConfigScreen(@NotNull final Process game) {
        this.game = game;
        game.setCurrentScreen(1);
        camera = new OrthographicCamera();
        camera.setToOrtho(false, Process.SCREEN_WIDTH, Process.SCREEN_HEIGHT);
        stage = game.getStage();
        InitBoxes();
        InitButtons();
        InitLabels();
    }

    @Override
    public void show() {
        Gdx.input.setInputProcessor(stage);
    }

    @Override
    public void render(float delta) {
        camera.update();
        game.batch.setProjectionMatrix(camera.combined);
        stage.act();
        stage.draw();
        if (Gdx.input.isKeyPressed(Input.Keys.ENTER)) game.setScreen(game.GetScreenByIndex(3));

    }

    @Override
    public void hide() {
        dispose();
    }

    @Override
    public void dispose() {
        stage.dispose();
    }

    /**
     * Инициализация SelectBoxes
     */
    private void InitBoxes() {
        final float posX = (Process.SCREEN_WIDTH - Process.BOX_WIDTH) / 2f;
        float posY = 500f;
        Array<Array<String>> boxItems = new Array<>();
        boxItems.add(
                new Array<>(new String[]{"4", "5", "6", "7"}),
                new Array<>(new String[]{"1", "2", "3", "4"}),
                new Array<>(new String[]{"Slow", "Medium", "Fast"}),
                new Array<>(new String[]{"Ia", "Ib", "II", "III", "IV", "V"})

        );
        for (int i = 0; i < 4; i++, posY -= 100f) {
            final SelectBox<String> box = game.getSelectBox();
            box.setPosition(posX, posY);
            box.setSize(Process.BOX_WIDTH, Process.BOX_HEIGHT);
            box.setItems(boxItems.get(i));
            GameSettings.setSystemVariables(i, boxVariables[i][0]);
            final Integer index = i;
            box.addListener(new ChangeListener() {
                @Override
                public void changed(ChangeEvent event, Actor actor) {
                    GameSettings.setSystemVariables(index, boxVariables[index][box.getSelectedIndex()]);
                }
            });
            this.stage.addActor(box);
        }
    }

    private void InitButtons() {
        final String[] butName = new String[]{"X", "Play"};
        final float[][] butPos = new float[][]{
                {50f, 500f},
                {Process.SCREEN_WIDTH / 2f - Process.SMALL_BUTTON_WIDTH / 2f, 150f}};
        for (int i = 0; i < 2; i++) {
            final TextButton button = game.getTextButton(butName[i]);
            button.setPosition(butPos[i][0], butPos[i][1]);
            button.setSize(Process.SMALL_BUTTON_WIDTH, Process.SMALL_BUTTON_HEIGHT);

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

    private void InitLabels() {
        final String[] labelText = new String[]{
                "Number of planet",
                "Quantity of goods ",
                "Ship's velocity",
                "System star type",
        };
        final float posX = (Process.SCREEN_WIDTH / 2f - Process.BOX_WIDTH);
        float posY = 530f;
        for (int i = 0; i < 4; i++, posY -= 100f) {

            final Label label = game.getLabel(labelText[i]);
            label.setFontScale(Process.TYPE);
            label.setPosition(posX, posY);
            label.setSize(Process.BOX_WIDTH, Process.BOX_HEIGHT);
            stage.addActor(label);
        }
    }
}
