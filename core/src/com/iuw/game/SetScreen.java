package com.iuw.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.ScreenViewport;

public class SetScreen implements Screen {
    final Process game;
    private Stage stage;
    private Texture img;
    private OrthographicCamera camera;

    public SetScreen(final Process game) {
        this.game = game;
        camera = new OrthographicCamera();
        camera.setToOrtho(false, Process.SCREEN_WIDTH, Process.SCREEN_HEIGHT);
        img = new Texture("main_theme.jpg");
        stage = new Stage(new ScreenViewport());
        String[] text_1 = {"Green", "Blue"};

        final String[] LabelName = new String[]{"Music", "Sound"};
        for (int i = 0; i < 2; i++) {
            final Label label = new Label(LabelName[i], Process.gameSkin);
            label.setFontScale(2f, 2f);
            label.setPosition(340, 340 + i * 110);
            stage.addActor(label);
        }
        //Sliders
        float posX = 250;
        float posY = 400;
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
        //
        //CheckBoxes
        final String[] words = new String[]{"first", "second"};
        final CheckBox[] checks = new CheckBox[2];
        Boolean WhoChosen = true;
        posY = 500f;
        posX = 150f;
        for (int i = 0; i < 2; i++, posX += 355f) {
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
        t_but_exit.setSize(300, 70);
        t_but_exit.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                game.setScreen(game.GetNextScreen(0));
            }
        });
        t_but_exit.setPosition(225, 100);
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
        game.batch.begin();
        //game.batch.draw(img, 0, 0);
        game.batch.end();
        stage.act();
        stage.draw();
    }

    @Override
    public void resize(int width, int height) {
        Process.SCREEN_WIDTH = width;
        Process.SCREEN_HEIGHT = height;
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

        img.dispose();
    }
}
