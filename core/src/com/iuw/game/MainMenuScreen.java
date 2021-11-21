package com.iuw.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import org.jetbrains.annotations.NotNull;

public class MainMenuScreen extends ScreenAdapter {
    final Process game;
    private final Stage stage;
    // private final Texture img;

    public MainMenuScreen(@NotNull final Process game) {
        this.game = game;
        game.setCurrentScreen(0);
        final String[] buttonName = new String[]{"Play", "Settings", "Exit"};
//      img = new Texture("main-theme.png");

        stage = game.getStage();
        final float posX = GameSettings.SCREEN_WIDTH / 20f;
        float posY = GameSettings.SCREEN_HEIGHT * 5f / 8f;
        for (int i = 0; i < 3; i++, posY -= GameSettings.SCREEN_HEIGHT / 5f) {
            final TextButton button = game.getTextButton(buttonName[i]);
            button.setPosition(posX, posY);
            button.setSize(GameSettings.BUTTON_WIDTH, GameSettings.BUTTON_HEIGHT);

            if (i < 2) {
                final Integer index = i + 1; //first screen is MainMenu, so others start with 1
                button.addListener(new ClickListener() {
                    @Override
                    public void clicked(InputEvent event, float x, float y) {
                        //  GameSettings.game = game;
                        game.setScreen(game.GetScreenByIndex(index));
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
        stage.act();
        stage.draw();
        if (Gdx.input.isKeyPressed(Input.Keys.NUM_1)) game.setScreen(game.GetScreenByIndex(1));
        if (Gdx.input.isKeyPressed(Input.Keys.NUM_2)) game.setScreen(game.GetScreenByIndex(2));
        if (Gdx.input.isKeyPressed(Input.Keys.NUM_3)) game.exit();

    }

    @Override
    public void hide() {
        dispose();
    }

    @Override
    public void dispose() {
        stage.dispose();
    }
    @Override
    public void resize (int width, int height) {
        stage.getViewport().update(width, height);
    }
}
