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
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.viewport.ScreenViewport;

public class ConfigScreen implements Screen {
    final Process game;
    private final Stage stage;
   private final Texture img;
    private final OrthographicCamera camera;
    final Integer default_speed = 10;
    final Integer default_mass = 10;

    final Integer[] planet_num_list = new Integer[]{4, 5, 6, 7}; //list for planet number
    final Integer[] load_num_list= new Integer[]{1, 2, 3, 4}; //number of loads for delivery
    final Integer[] speed_list= new Integer[]{default_speed, 2*default_speed, 3*default_speed}; //speed of the ship
    final Integer[] mass_star_list = new Integer[]{default_mass, 2*default_mass, 3*default_mass,
                                                   default_mass, 2*default_mass, 3*default_mass}; //mass of definite star

    final Integer[][] index_array =
            new Integer[][]{planet_num_list, load_num_list, speed_list, mass_star_list};
    private Integer[] chosen_variables = new Integer[4];
    final float WIDTH_BOX = 100, HEIGHT_BOX = 50;

      public ConfigScreen(final Process game) {
        this.game = game;
        camera = new OrthographicCamera();
        camera.setToOrtho(false, Process.WIDTH, Process.HEIGHT);
        final String[] list_3 = new String[]{"Slow", "Medium", "Fast"}; //velocity of ship
        final String[] list_4 = new String[]{ "Ia", "Ib", "II", "III", "IV","V"}; //types of sun

        img = new Texture("Space.jfif");
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


             planet_numbs.setItems(new Array(planet_num_list));
             load_numbs.setItems(new Array(load_num_list));
             ship_speeds.setItems(new Array(list_3));
             star_types.setItems(new Array(list_4));

             chosen_variables[0] = (Integer)planet_numbs.getSelected();
             chosen_variables[1] = (Integer)load_numbs.getSelected();
             chosen_variables[2] = speed_list[ship_speeds.getSelectedIndex()];
             chosen_variables[3] = mass_star_list[star_types.getSelectedIndex()];

          ListenBox(planet_numbs, 0);
          ListenBox(load_numbs, 1);
          ListenBox(ship_speeds, 2);
          ListenBox(star_types, 3);


        play_but.addListener(new ClickListener(){ // InputListener is uses for low-level input
            @Override
            public void clicked(InputEvent event, float x, float y) {
                System.out.println(chosen_variables[0]);
                System.out.println(chosen_variables[1]);
                System.out.println(chosen_variables[2]);
                System.out.println(chosen_variables[3]);
                game.setScreen(new MainPlayScreen(game, chosen_variables[0]));
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

    public boolean ListenBox(final SelectBox box, final Integer index){
          if (index > 3 || index < 0) return false;
          else return box.addListener(new ChangeListener() {
            @Override()
            public void changed(ChangeEvent event, Actor actor) {
                chosen_variables[index] = index_array[index][box.getSelectedIndex()];
            }
        });
    }

}
