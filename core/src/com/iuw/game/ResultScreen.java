package com.iuw.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

/**
 * Game completion screen
 */
public class ResultScreen extends ScreenAdapter {
    final Process game;
    private final Stage stage;

    /**
     * Creating a Standard Result Screen
     *
     * @param game - Process
     */
    public ResultScreen(final Process game) {
        this.game = game;
        game.setCurrentScreen(2);
        stage = game.getStage();
        final Label mainFrame = game.getLabel(GameSettings.getGameResult());
        final Label scorePoints = game.getLabel("Goods were delivered: "
                .concat(GameSettings.getDeliveredGoods().toString())
        );
        mainFrame.setPosition(GameSettings.SCREEN_WIDTH / 2f - mainFrame.getWidth() / 2f, GameSettings.SCREEN_HEIGHT / 2f);
        scorePoints.setPosition(GameSettings.SCREEN_WIDTH / 2f - scorePoints.getWidth() / 2f, GameSettings.SCREEN_HEIGHT / 2.5f);
        stage.addActor(mainFrame);
        stage.addActor(scorePoints);
        final TextButton retryButton = game.getTextButton("Play again");
        retryButton.setPosition(GameSettings.SCREEN_WIDTH / 2f - GameSettings.BUTTON_WIDTH / 2f, GameSettings.SCREEN_HEIGHT / 5f);
        retryButton.setSize(GameSettings.BUTTON_WIDTH, GameSettings.BUTTON_HEIGHT);
        stage.addActor(retryButton);
        retryButton.addListener(new ClickListener() {
            public void clicked(InputEvent event, float x, float y) {
                game.setScreen(game.GetScreenByIndex(1));
            }
        });

    }

    @Override
    public void show() {
        Gdx.input.setInputProcessor(stage);
    }

    @Override
    public void render(float delta) {
        stage.act();
        stage.draw();
        if (Gdx.input.isKeyPressed(Input.Keys.SPACE) || Gdx.input.isKeyPressed(Input.Keys.ENTER)) {
            game.nextKeyPressed = true;
            game.setScreen(game.GetScreenByIndex(1));
        }
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
