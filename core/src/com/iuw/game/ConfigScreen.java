package com.iuw.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.viewport.ScreenViewport;

public class ConfigScreen implements Screen {
    final Process game;
    private Stage stage;
    private Texture img;
    private OrthographicCamera camera;
    private Integer[] list_1 = {4, 5, 6, 7}; //list for planet number
    private Integer[] list_2= {1, 2, 3, 4}; //list for planet number
    private String[] list_3 = {"1", "2", "3"}; //list for planet number
    private Integer chosen_planet_num;
    private Integer chosen_load_num;
   // private Integer chosen_planet_num;
    public ConfigScreen(final Process game) {
        this.game = game;
        camera = new OrthographicCamera();
        camera.setToOrtho(false, 600, 800);

        img = new Texture("Space.jfif");

        stage = new Stage(new ScreenViewport());
             final SelectBox planet_numbs = new SelectBox<Integer>(Process.gameSkin);
             final SelectBox load_numbs = new SelectBox<Integer>(Process.gameSkin);
             final TextButton play_but = new TextButton("PLAY", Process.gameSkin);
             final TextButton exit_but = new TextButton("X", Process.gameSkin);
//             final SelectBox planet_num = new SelectBox<String>(game.gameSkin);

             planet_numbs.setPosition(100,500);
            load_numbs.setPosition(100,300);
            play_but.setPosition(200,300);
           exit_but.setPosition(50,700);

             planet_numbs.setItems(list_1);
             load_numbs.setItems(list_2);
              chosen_planet_num = (Integer)planet_numbs.getSelected();
              chosen_load_num = (Integer)load_numbs.getSelected();
        planet_numbs.addListener(new ChangeListener() { // ChangeListener uses especially for select box
                @Override
                public void changed(ChangeEvent event, Actor actor) {
                 chosen_planet_num = (Integer)planet_numbs.getSelected();
                }
            });
        load_numbs.addListener(new ChangeListener() { // ChangeListener uses especially for select box
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                chosen_load_num = (Integer)load_numbs.getSelected();
            }
        });
        play_but.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                System.out.println(chosen_load_num);
                System.out.println(chosen_planet_num);
            }

        });
        exit_but.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                game.setScreen(new MainMenuScreen(game));
                dispose();
            }

        });


            stage.addActor(planet_numbs);
            stage.addActor(load_numbs);
            stage.addActor(play_but);
            stage.addActor(exit_but);
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
        img.dispose();
    }
}
