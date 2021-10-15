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
import com.badlogic.gdx.utils.viewport.ScreenViewport;

import java.util.HashMap;
import java.util.Map;

public class ConfigScreen implements Screen {
    final Process game;
    private Stage stage;
    private Texture img;
    private OrthographicCamera camera;
    final private Integer[] list_1 = {4, 5, 6, 7}; //list for planet number
    final private Integer[] list_2= {1, 2, 3, 4}; //number of loads for delivery
    final private String[] list_3 = {"Slow", "Medium", "Fast"}; //velocity of ship
    final private String[] list_4 = { "Ia", "Ib", "II", "III", "IV","V"}; //types of sun
    private Map<String, Integer> speed_map = new HashMap<String, Integer>();
    private Map<String, Integer> mass_map = new HashMap<String, Integer>();
    private Integer chosen_planet_num;
    private Integer chosen_load_num;
    private Integer chosen_speed;
    private Integer chosen_star;
    final Integer default_speed = 10;
    final Integer default_mass = 10;
   // private Integer chosen_planet_num;
    public ConfigScreen(final Process game) {
        this.game = game;
        camera = new OrthographicCamera();
        camera.setToOrtho(false, 600, 800);

        img = new Texture("Space.jfif");
        speed_map.put(list_3[0], default_speed);
        speed_map.put(list_3[1], default_speed*2);
        speed_map.put(list_3[2], default_speed*3);

        speed_map.put(list_4[0], default_mass);
        speed_map.put(list_4[1], default_mass*2);
        speed_map.put(list_4[2], default_mass*3);
        speed_map.put(list_4[3], default_mass);
        speed_map.put(list_4[4], default_mass*2);
        speed_map.put(list_4[5], default_mass*3);

        stage = new Stage(new ScreenViewport());
             final SelectBox planet_numbs = new SelectBox<Integer>(Process.gameSkin);
             final SelectBox load_numbs = new SelectBox<Integer>(Process.gameSkin);
             final SelectBox ship_speeds = new SelectBox<String>(Process.gameSkin);
             final SelectBox star_types = new SelectBox<String>(Process.gameSkin);
             final TextButton play_but = new TextButton("PLAY", Process.gameSkin);
             final TextButton exit_but = new TextButton("X", Process.gameSkin);
//             final SelectBox planet_num = new SelectBox<String>(game.gameSkin);

             planet_numbs.setPosition(100,700);
            load_numbs.setPosition(100,600);
            ship_speeds.setPosition(100,500);
          star_types.setPosition(100,400);

            play_but.setPosition(200,300);
           exit_but.setPosition(50,700);

             planet_numbs.setItems(list_1);
             load_numbs.setItems(list_2);
             ship_speeds.setItems(list_3);
           star_types.setItems(list_4);
              chosen_planet_num = (Integer)planet_numbs.getSelected();
              chosen_load_num = (Integer)load_numbs.getSelected();
              chosen_speed =speed_map.get(ship_speeds.getSelected());
              chosen_star = mass_map.get(star_types.getSelected());
        planet_numbs.addListener(new ChangeListener() { // ChangeListener uses especially for select box
                @Override
                public void changed(ChangeEvent event, Actor actor) {
                 chosen_planet_num = (Integer)planet_numbs.getSelected();
                }
            });
        load_numbs.addListener(new ChangeListener() {
            @Override()
            public void changed(ChangeEvent event, Actor actor) {
                chosen_load_num = (Integer)load_numbs.getSelected();
            }
        });
        ship_speeds.addListener(new ChangeListener() {
            @Override()
            public void changed(ChangeEvent event, Actor actor) {
                chosen_speed = speed_map.get(ship_speeds.getSelected());
            }
        });
        star_types.addListener(new ChangeListener() {
            @Override()
            public void changed(ChangeEvent event, Actor actor) {
                chosen_star = mass_map.get(star_types.getSelected());
            }
        });
        play_but.addListener(new ClickListener(){ // InputListener is uses for low-level input
            @Override
            public void clicked(InputEvent event, float x, float y) {
                System.out.println(chosen_load_num);
                System.out.println(chosen_planet_num);
                System.out.println(chosen_speed);
                game.setScreen(new MainPlayScreen(game, chosen_speed));
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
            stage.addActor(ship_speeds);
            stage.addActor(star_types);
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
