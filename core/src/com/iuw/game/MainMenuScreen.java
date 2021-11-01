package com.iuw.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.ScreenViewport;

public class MainMenuScreen implements Screen {
    final Process game;
    private final Stage stage;
    private final Texture img;
    private final OrthographicCamera camera;

    public MainMenuScreen(final Process game) {
        this.game = game;
        camera = new OrthographicCamera();
        camera.setToOrtho(false, Process.SCREEN_WIDTH, Process.SCREEN_HEIGHT);

        final String[] ButtonName = new String[]{"Play", "Settings", "Exit"};
        img = new Texture("main_theme.jpg");
        stage = new Stage(new ScreenViewport());
        final float posX = 50f;
        float posY = 300f;
        for (int i = 0; i < 3; i++) {
            final TextButton button = new TextButton(ButtonName[i], Process.gameSkin);
            button.setPosition(posX, posY);
            button.setSize(Process.BUTTON_WIDTH, Process.BUTTON_HEIGHT);

            if (i < 2) {
                final Integer index = i + 1; //first screen is MainMenu, so others start with 1
                button.addListener(new ClickListener() {
                    @Override
                    public void clicked(InputEvent event, float x, float y) {
                        game.setScreen(game.GetNextScreen(index));
                    }
                });
            } else {
                button.addListener(new ClickListener() {
                    @Override
                    public void clicked(InputEvent event, float x, float y) {
                        game.batch.dispose();
                        game.font.dispose();
                        game.SpaceMusic.dispose();
                        Gdx.app.exit();
                    }
                });
            }
            posY -= 70f;
            stage.addActor(button);
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
        game.batch.draw(img, 0f, 0f);
        game.batch.end();
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
        img.dispose();
    }
}
