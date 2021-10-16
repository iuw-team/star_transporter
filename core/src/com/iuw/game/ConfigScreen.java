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
    private final Stage stage;
    private final Texture img;
    private final OrthographicCamera camera;
    private final Map<String, Integer> speed_map = new HashMap<>();
    private final Map<String, Integer> mass_map = new HashMap<>();
    private Integer chosen_planet_num;
    private Integer chosen_load_num;
    private Integer chosen_speed;
    private Integer chosen_star;
    final Integer default_speed = 10;
    final Integer default_mass = 10;
    final float WIDTH_BOX = 100, HEIGHT_BOX = 50;
      public ConfigScreen(final Process game) {
        this.game = game;
        camera = new OrthographicCamera();
        camera.setToOrtho(false, 600, 800);

        final Integer[] list_1 = new Integer[]{4, 5, 6, 7}; //list for planet number
        final Integer[] list_2= new Integer[]{1, 2, 3, 4}; //number of loads for delivery
        final String[] list_3 = new String[]{"Slow", "Medium", "Fast"}; //velocity of ship
        final String[] list_4 = new String[]{ "Ia", "Ib", "II", "III", "IV","V"}; //types of sun

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
             final TextTooltip planet_info_tooltip = new TextTooltip("Average number of planet", Process.gameSkin);
             planet_info_tooltip.setInstant(true);
             final Label planet_info = new Label("Chose number of planet", Process.gameSkin);
             planet_info.setFontScale(1.5f);

             final SelectBox planet_numbs = new SelectBox<Integer>(Process.gameSkin);
             final SelectBox load_numbs = new SelectBox<Integer>(Process.gameSkin);
             final SelectBox ship_speeds = new SelectBox<String>(Process.gameSkin);
             final SelectBox star_types = new SelectBox<String>(Process.gameSkin);

             final TextButton play_but = new TextButton("PLAY", Process.gameSkin);
             final TextButton exit_but = new TextButton("X", Process.gameSkin);

             planet_info.setPosition((Process.WIDTH - WIDTH_BOX)/2f -40 ,630f);  planet_info.setSize(WIDTH_BOX, HEIGHT_BOX);
             planet_numbs.setPosition((Process.WIDTH - WIDTH_BOX)/2f,600f); planet_numbs.setSize(WIDTH_BOX, HEIGHT_BOX);
             load_numbs.setPosition((Process.WIDTH - WIDTH_BOX)/2f,500f);   load_numbs.setSize(WIDTH_BOX, HEIGHT_BOX);
             ship_speeds.setPosition((Process.WIDTH - WIDTH_BOX)/2f,400f);  ship_speeds.setSize(WIDTH_BOX, HEIGHT_BOX);
             star_types.setPosition((Process.WIDTH - WIDTH_BOX)/2f,300f);   star_types.setSize(WIDTH_BOX, HEIGHT_BOX);

             play_but.setPosition(265f,250f);
             exit_but.setPosition(50f,700f);

             planet_numbs.setItems(list_1);
             load_numbs.setItems(list_2);
             ship_speeds.setItems(list_3);
             star_types.setItems(list_4);
              chosen_planet_num = (Integer)planet_numbs.getSelected();
              chosen_load_num = (Integer)load_numbs.getSelected();
              chosen_speed =speed_map.get(ship_speeds.getSelected());
              chosen_star = mass_map.get(star_types.getSelected());

          planet_info.addListener(planet_info_tooltip);
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
            }

        });

            stage.addActor(planet_info);
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
        Gdx.gl.glClearColor(1f, 1f, 1f, 1f);

        camera.update();
        game.batch.setProjectionMatrix(camera.combined);
        game.batch.begin();
        game.batch.draw(img, 0f, 0f);
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
        img.dispose();
    }
}
