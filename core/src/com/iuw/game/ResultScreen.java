package com.iuw.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

public class ResultScreen implements Screen {
    final Process game;
    private final Stage stage;
    private final OrthographicCamera camera;

    public ResultScreen(final Process game) {
        this.game = game;
        camera = new OrthographicCamera();
        camera.setToOrtho(false, Process.SCREEN_WIDTH, Process.SCREEN_HEIGHT);
        //  img = new Texture("main_theme.jpg");
        stage = game.getStage();

        final String[] ButtonName = new String[]{"Play again", "Settings", "MainMenu"};
        float posX, posY;
        posX = 200f;
        posY = 400f;
        for (int i = 0; i < 3; i++, posY -= 100f) {
            final TextButton button = game.getTextButton(ButtonName[i]);
            button.setPosition(posX, posY);
            button.setSize(Process.BUTTON_WIDTH, Process.BUTTON_HEIGHT);
            stage.addActor(button);
            final Integer index = i;
            button.addListener(new ClickListener() {
                public void clicked(InputEvent event, float x, float y) {
                    game.setScreen(game.GetScreenByIndex(index));

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
