package com.iuw.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
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
        camera.setToOrtho(false, 600, 800);

        img = new Texture("main_theme.jpg");
        stage = new Stage(new ScreenViewport());

        Slider slider_1 = new Slider(0, 100, 1, false, Process.gameSkin);
        Slider slider_2 = new Slider(0, 100, 1, false, Process.gameSkin);

        Button button_1 = new Button(Process.gameSkin);

        SelectBox selectbox_1 = new SelectBox<String>(Process.gameSkin);
        SelectBox selectbox_2 = new SelectBox<String>(Process.gameSkin);

        String[] text_1 = {"Green", "Blue"};
        String[] text_2 = {"12", "14", "16", "18", "20", "22"};
        selectbox_1.setItems(text_1);
        selectbox_2.setItems(text_2);

        Label label_1 = new Label("Music", Process.gameSkin);
        Label label_2 = new Label("Sound", Process.gameSkin);
        Label label_3 = new Label("Something", Process.gameSkin);
        Label label_4 = new Label("Chto-to", Process.gameSkin);
        label_1.setFontScale(2,2);
        label_2.setFontScale(2,2);
        label_3.setFontScale(2,2);
        label_4.setFontScale(2,2);

        slider_1.setSize(210, 100);
        slider_2.setSize(210, 100);

        button_1.setSize(250, 100);

        selectbox_1.setSize(210, 100);
        selectbox_2.setSize(210, 100);

        button_1.setPosition(170, 100);

        slider_1.setPosition(60, 300);
        slider_2.setPosition(330, 300);

        selectbox_1.setPosition(60, 500);
        selectbox_2.setPosition(330, 500);

        label_1.setPosition(130,600);
        label_2.setPosition(130,400);
        label_3.setPosition(380,600);
        label_4.setPosition(380,400);

        stage.addActor(slider_1);
        stage.addActor(slider_2);

        stage.addActor(label_1);
        stage.addActor(label_2);
        stage.addActor(label_3);
        stage.addActor(label_4);

        stage.addActor(selectbox_1);
        stage.addActor(selectbox_2);
        stage.addActor(button_1);


    }

    @Override
    public void show() {
        Gdx.input.setInputProcessor(stage);
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(1, 1, 1, 1);


        camera.update();
        game.batch.setProjectionMatrix(camera.combined);

        game.batch.begin();
        game.batch.draw(img, 0, 0);
        game.batch.end();
        stage.act();
        stage.draw();
        // game.button3.getClickListener().touchUp();
//        if (Gdx.input.isTouched()) {
//            game.setScreen(new ConfigScreen(game));
//            dispose();
//        }
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

    }

    @Override
    public void dispose() {
        stage.dispose();
    }
}
