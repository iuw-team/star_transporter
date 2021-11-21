package com.iuw.game;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.CheckBox;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Slider;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
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
     * @param game - Process
     */
    public SetScreen(@NotNull final Process game) {
        this.game = game;
        game.setCurrentScreen(1);
        camera = new OrthographicCamera();
        stage = game.getStage();
        float posY = Process.SCREEN_HEIGHT/2.35f;
        final String[] labelName = new String[]{"Music", "Sound", "Choose music theme"};
        for (int i = 0; i < 3; i++, posY += Process.SCREEN_HEIGHT/5f) {
            final Label label = game.getLabel(labelName[i]);
            label.setFontScale(2f, 2f);
            label.setPosition(Process.SCREEN_WIDTH / 2f - label.getWidth(), posY);
            stage.addActor(label);
        }
        //Sliders
        float posX = Process.SCREEN_WIDTH / 2f - Process.SLIDER_WIDTH / 2f;
        posY = Process.SCREEN_HEIGHT/2f;
        for (int i = 0; i < 2; i++, posY -= Process.SCREEN_HEIGHT/6f) {
            final Slider volume = game.getSlider();
            volume.setValue(GameSettings.VOLUME_LEVELS[i]);
            volume.setPosition(posX, posY);
            volume.setSize(Process.SLIDER_WIDTH, Process.SLIDER_HEIGHT);
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
        final String[] words = new String[]{"Music", "Vacuum"};
        final CheckBox[] checks = new CheckBox[2];
        boolean WhoChosen = Process.ChosenSkin != 1;
        posY = Process.SCREEN_HEIGHT/1.5f;
        posX = Process.SCREEN_WIDTH / 3f;
        for (int i = 0; i < 2; i++, posX += Process.SCREEN_WIDTH / 5f) {
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
        t_but_exit.setSize(Process.BUTTON_WIDTH, Process.BUTTON_HEIGHT);
        t_but_exit.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                game.setScreen(game.GetScreenByIndex(game.getCurrentScreen() - 1));

            }
        });
        t_but_exit.setPosition(Process.SCREEN_WIDTH/2f - Process.BUTTON_WIDTH/2f, Process.SCREEN_HEIGHT/6f);
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
     * @param delta - time between neighboring frames
     */
    @Override
    public void render(float delta) {
        camera.update();
        game.batch.setProjectionMatrix(camera.combined);
        stage.act();
        stage.draw();
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
