package com.iuw.game;

import com.badlogic.gdx.Game;
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

public class PlaySetScreen implements Screen {
    final Process game;
    private Stage stage;
    private Texture img;
    private OrthographicCamera camera;

    public PlaySetScreen(final Process game) {
        this.game = game;
        camera = new OrthographicCamera();
        camera.setToOrtho(false, Process.SCREEN_WIDTH, Process.SCREEN_HEIGHT);
        img = new Texture("main_theme.jpg");
        stage = new Stage(new ScreenViewport());

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
            final Slider volume = new Slider(0f, GameSettings.MAX_LEVEL, 0.001f, false, Process.gameSkin);
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

        TextButton t_but_exit = new TextButton("MainMenu", Process.gameSkin);
        t_but_exit.setSize(300, 70);
        t_but_exit.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                game.setScreen(game.GetNextScreen(0));
            }
        });
        t_but_exit.setPosition(225, 210);
        stage.addActor(t_but_exit);

        TextButton t_but_continue = new TextButton("Resume", Process.gameSkin);
        t_but_continue.setSize(340, 90);
        t_but_continue.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                game.setScreen(game.GetNextScreen(3));
            }
        });
        t_but_continue.setPosition(210, 90);
        stage.addActor(t_but_continue);
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
