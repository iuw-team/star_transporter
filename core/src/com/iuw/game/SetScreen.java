package com.iuw.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import org.jetbrains.annotations.NotNull;

/**
 * Settings UI class
 */
public class SetScreen extends ScreenAdapter {
    final Process game;
    private final Stage stage;
    private final OrthographicCamera camera;

    /**
     * Creating default SetScreen
     *
     * @param game - Process
     */
    public SetScreen(@NotNull final Process game) {
        this.game = game;
        game.setCurrentScreen(1);
        camera = new OrthographicCamera();
        stage = game.getStage();
        float posY = GameSettings.SCREEN_HEIGHT / 2.35f;
        final String[] labelName = new String[]{"Music", "Sound", "Choose music theme"};
        for (int i = 0; i < 3; i++, posY += GameSettings.SCREEN_HEIGHT / 5f) {
            final Label label = game.getLabel(labelName[i]);
            label.setFontScale(2f, 2f);
            label.setPosition(GameSettings.SCREEN_WIDTH / 2f - label.getWidth(), posY);
            stage.addActor(label);
        }
        //Sliders
        float posX = GameSettings.SCREEN_WIDTH / 2f - GameSettings.SLIDER_WIDTH / 2f;
        posY = GameSettings.SCREEN_HEIGHT / 2f;
        for (int i = 0; i < 2; i++, posY -= GameSettings.SCREEN_HEIGHT / 6f) {
            final Slider volume = game.getSlider();
            volume.setValue(GameSettings.VOLUME_LEVELS[i]);
            volume.setPosition(posX, posY);
            volume.setSize(GameSettings.SLIDER_WIDTH, GameSettings.SLIDER_HEIGHT);
            final Integer index = i;
            volume.addListener(new ChangeListener() {
                @Override
                public void changed(ChangeEvent event, Actor actor) {
                    GameSettings.VOLUME_LEVELS[index] = volume.getValue();
                }
            });
            stage.addActor(volume);
        }
        //
        //CheckBoxes
        final String[] words = new String[]{"Classic", "Improver"};
        final CheckBox[] checks = new CheckBox[2];
        boolean WhoChosen = Process.ChosenSkin != 1;
        posY = GameSettings.SCREEN_HEIGHT / 1.5f;
        posX = GameSettings.SCREEN_WIDTH / 3f;
        for (int i = 0; i < 2; i++, posX += GameSettings.SCREEN_WIDTH / 5f) {
            checks[i] = game.getCheckBox(words[i]);
            checks[i].setPosition(posX, posY);
            checks[i].setSize(100f, 100f);
            checks[i].setChecked(WhoChosen);
            WhoChosen = !WhoChosen;
            final int index = i;
            checks[i].addListener(new ChangeListener() {
                @Override
                public void changed(ChangeEvent event, Actor actor) {
                    if (Process.ChosenSkin != index) {
                        checks[Process.ChosenSkin].setChecked(false);
                        checks[index].setChecked(true);
                        Process.ChosenSkin = index;
                    }
                }
            });
            stage.addActor(checks[i]);
        }
        TextButton t_but_exit = game.getTextButton("Save and Exit");
        t_but_exit.setSize(GameSettings.BUTTON_WIDTH, GameSettings.BUTTON_HEIGHT);
        t_but_exit.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                game.setScreen(game.GetScreenByIndex(game.getCurrentScreen() - 1));

            }
        });
        t_but_exit.setPosition(GameSettings.SCREEN_WIDTH / 2f - GameSettings.BUTTON_WIDTH / 2f, GameSettings.SCREEN_HEIGHT / 6f);

        SelectBox<String> scaleChanger = game.getSelectBox();
        scaleChanger.setPosition(GameSettings.SCREEN_WIDTH * 0.8f, GameSettings.SCREEN_HEIGHT * 0.8f);
        scaleChanger.setSize(1.5f * GameSettings.BOX_WIDTH, GameSettings.BOX_HEIGHT);
        scaleChanger.setItems(
                "800x600");//, "1280x1024", "1600x1200", "1920x1200");
        scaleChanger.setSelectedIndex(GameSettings.getCurrentResolution());
        scaleChanger.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                GameSettings.setResolution(scaleChanger.getSelectedIndex());
                game.setCameraResolution(GameSettings.SCREEN_WIDTH, GameSettings.SCREEN_HEIGHT);
                game.setScreen(game.GetScreenByIndex(2));
                hide();
            }
        });
        //stage.addActor(scaleChanger);
        stage.addActor(t_but_exit);
    }

    /**
     * Avoid when this Screen is appear
     */
    @Override
    public void show() {
        Gdx.input.setInputProcessor(stage);
    }

    /**
     * Rendering all UI components
     *
     * @param delta - time between neighboring frames
     */
    @Override
    public void render(float delta) {
        camera.update();
        game.batch.setProjectionMatrix(camera.combined);
        stage.act();
        stage.draw();
        if (Gdx.input.isKeyPressed(Input.Keys.ENTER) && !game.nextKeyPressed) {
            game.setScreen(game.GetScreenByIndex(0));
        } else if (!Gdx.input.isKeyPressed(Input.Keys.ENTER) && !Gdx.input.isKeyPressed(Input.Keys.SPACE) && game.nextKeyPressed) {
            game.nextKeyPressed = false;
        }
    }

    /**
     * Avoid when this Screen is hidden
     */
    @Override
    public void hide() {
        dispose();
    }

    /**
     * Destroy all Objects (components) of UI
     */
    @Override
    public void dispose() {
        stage.dispose();
    }
}
