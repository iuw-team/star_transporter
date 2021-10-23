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

        String[] text_1 = {"Green", "Blue"};
        String[] text_2 = {"12", "14", "16", "18", "20", "22"};

        final String[] LabelName = new String[]{"Music", "Sound", "Something"};
        for(int i=0; i<3; i++){
            final Label label = new Label(LabelName[i], Process.gameSkin);
            label.setFontScale(2,2);
            label.setPosition(250, 340+i*175);
            stage.addActor(label);
        }

        for(int i=0; i<2; i++){
            SelectBox selectbox = new SelectBox<String>(Process.gameSkin);
            if(i==0) {
                selectbox.setItems(text_1);
            }
            else{
                selectbox.setItems(text_2);
            }
            selectbox.setPosition(170, 400+i*170);
            selectbox.setSize(250, 100);
            stage.addActor(selectbox);
        }

        TextButton t_but_exit = new TextButton("MainMenu", Process.gameSkin);

        slider_1.setSize(250, 100);

        t_but_exit.setSize(250, 100);

        t_but_exit.addListener(new InputListener(){

            @Override
            public void touchUp (InputEvent event, float x, float y, int pointer, int button) {
                game.setScreen(new SetScreen(game));
                dispose();
            }
            @Override
            public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {
                return true;
            }
        });

        t_but_exit.setPosition(170, 100);

        slider_1.setPosition(170, 250);

        stage.addActor(slider_1);

        stage.addActor(t_but_exit);

        t_but_exit.addListener(new InputListener(){

            @Override
            public void touchUp (InputEvent event, float x, float y, int pointer, int button) {
                game.setScreen(new MainMenuScreen(game));
            }
            @Override
            public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {
                return true;
            }
        });

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
