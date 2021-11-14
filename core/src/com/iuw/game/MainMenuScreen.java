package com.iuw.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import org.jetbrains.annotations.NotNull;

public class MainMenuScreen implements Screen {
    final Process game;
    private final Stage stage;
    private final OrthographicCamera camera;

    public MainMenuScreen(@NotNull final Process game) {
        this.game = game;
        game.setCurrentScreen(0);
        camera = new OrthographicCamera();
        camera.setToOrtho(false, Process.SCREEN_WIDTH, Process.SCREEN_HEIGHT);

        final String[] buttonName = new String[]{"Play", "Settings", "Exit"};

        stage = game.getStage();
        final float posX = 50f;
        float posY = 400f;
        for (int i = 0; i < 3; i++, posY -= 100f) {
            final TextButton button = game.getTextButton(buttonName[i]);
            button.setPosition(posX, posY);
            button.setSize(Process.BUTTON_WIDTH, Process.BUTTON_HEIGHT);

            if (i < 2) {
                final Integer index = i + 1; //first screen is MainMenu, so others start with 1
                button.addListener(new ClickListener() {
                    @Override
                    public void clicked(InputEvent event, float x, float y) {
                        game.setScreen(game.getNextScreen(index));
                    }
                });
            } else {
                button.addListener(new ClickListener() {
                    @Override
                    public void clicked(InputEvent event, float x, float y) {
                        game.exit();
                    }
                });
            }
            stage.addActor(button);
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
        stage.act();
        stage.draw();
    }

    @Override
    public void resize(int width, int height) {//don't use yet
    }

    @Override
    public void pause() {//don't use yet
    }

    @Override
    public void resume() {//don't use yet
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
