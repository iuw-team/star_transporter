package com.iuw.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
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
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import org.jetbrains.annotations.NotNull;

public class SetScreen implements Screen {
    final Process game;
    private final Stage stage;
    private final OrthographicCamera camera;

    public SetScreen(@NotNull final Process game) {
        this.game = game;
        game.setCurrentScreen(1);
        camera = new OrthographicCamera();
        stage = new Stage(new ScreenViewport());

        float posX = 325f;
        float posY = 350f;
        final String[] LabelName = new String[]{"Music", "Sound", "Chosen interface"};
        for (int i = 0; i < 3; i++, posY +=100f) {
            final Label label = new Label(LabelName[i], Process.gameSkin);
            label.setFontScale(2f, 2f);
            label.setPosition(posX, posY);
            stage.addActor(label);
        }
        //Sliders
        posX = Process.SCREEN_WIDTH / 2f - Process.SLIDER_WIDTH / 2f;
        posY = 400f;
        for (int i = 0; i < 2; i++, posY -= 100f) {
            final Slider volume = new Slider(0f, Process.MAX_LEVEL, 0.001f, false, Process.gameSkin);
            volume.setValue(Process.VOLUME_LEVELS[i]);
            volume.setPosition(posX, posY);
            volume.setSize(Process.SLIDER_WIDTH, Process.SLIDER_HEIGHT);
            final Integer index = i;
            volume.addListener(new ChangeListener() {
                @Override
                public void changed(ChangeEvent event, Actor actor) {
                    Process.VOLUME_LEVELS[index] = volume.getValue();
                }
            });
            stage.addActor(volume);
        }
        final String[] words = new String[]{"GREEN", "BLUE"};
        final CheckBox[] checks = new CheckBox[2];
        boolean WhoChosen = true;
        posY = 500f;
        posX = 150f;
        for (int i = 0; i < 2; i++, posX += 300f) {
            checks[i] = new CheckBox(words[i], Process.gameSkin);
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
        TextButton t_but_exit = new TextButton("Save and exit", Process.gameSkin);
        t_but_exit.setSize(Process.BUTTON_WIDTH, Process.BUTTON_HEIGHT);
        t_but_exit.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                game.setScreen(game.GetNextScreen(0));
            }
        });
        t_but_exit.setPosition(170, 100);
        stage.addActor(t_but_exit);
    }

    @Override
    public void show() {
        Gdx.input.setInputProcessor(stage);
    }

    @Override
    public void render(float delta) {
        ScreenUtils.clear(0f, 0f, 0f, 0f);
        camera.update();
        game.batch.setProjectionMatrix(camera.combined);
        stage.act();
        stage.draw();
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
        dispose();
    }

    @Override
    public void dispose() {
        stage.dispose();
    }
}
