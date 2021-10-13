package com.iuw.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.SelectBox;
import com.badlogic.gdx.scenes.scene2d.ui.Slider;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.badlogic.gdx.scenes.scene2d.ui.*;




public class SetScreen implements Screen {
    final Process game;
    private String [] maxarray = {"Green theme", "Blue theme"};
    private Stage stage;
    private OrthographicCamera camera;
    private Texture img;
    public SetScreen(final Process game){
        this.game = game;
        camera = new OrthographicCamera();
        camera.setToOrtho(false, 600, 800);

        img = new Texture("main_theme.jpg");
        stage = new Stage(new ScreenViewport());
        Slider maxim_slider = new Slider(0f, 100f, 1f, false, Process.gameSkin);
        SelectBox maxim_box = new SelectBox<String>(Process.gameSkin);
        Slider maxim_slider2 = new Slider(0f, 100f, 1f, false, Process.gameSkin);
        SelectBox maxim_box2 = new SelectBox<String>(Process.gameSkin);
        Label max_label = new Label("label", Process.gameSkin);


        maxim_slider.setSize(200, 150);
        maxim_box.setSize(300, 150);
        maxim_slider2.setSize(200, 150);
        max_label.setSize(200, 120);
        maxim_box2.setSize(300, 150);



        maxim_slider.setPosition(370, 200);
        maxim_slider2.setPosition(370, 500);
        maxim_box.setPosition(20, 200);
        maxim_box2.setPosition(20, 200);
        max_label.setPosition(23, 323);


        stage.addActor(maxim_slider);
        stage.addActor(maxim_box);
        stage.addActor(maxim_slider2);
        stage.addActor(max_label);
        stage.addActor(maxim_box2);

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

        //game.batch.begin();
        //game.batch.draw(img, 0, 0);
        //game.batch.end();
        //stage.act();
        //stage.draw();
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

    }

    @Override
    public void dispose() {

    }
}
