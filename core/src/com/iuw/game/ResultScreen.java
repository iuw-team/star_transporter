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

import javax.swing.*;

public class ResultScreen implements Screen {
    final Process game;
    private Stage stage;
    private OrthographicCamera camera;

    public ResultScreen(final Process game) {
        this.game = game;
        camera = new OrthographicCamera();
        camera.setToOrtho(false, Process.SCREEN_WIDTH, Process.SCREEN_HEIGHT);
        stage = new Stage(new ScreenViewport());

        final String[] ButtonName = new String[]{"Play again", "Settings", "MainMenu"};
        for (int i = -2; i < 1; i++) {
            final TextButton button = new TextButton(ButtonName[i + 2], Process.gameSkin);
            button.setPosition(200, 200 - 100 * i);
            button.setSize(Process.BUTTON_WIDTH, Process.BUTTON_HEIGHT);
            stage.addActor(button);
            final Integer index = i;
            button.addListener(new ClickListener() {
                public void clicked(InputEvent event, float x, float y) {
                    game.setScreen(game.getNextScreen(index));
                }
            });
        }
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

    }
}
