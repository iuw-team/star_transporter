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

public class ConfigScreen implements Screen {
    final Process game;
    private final Stage stage;
    private final Texture img;
    private final OrthographicCamera camera;
    final Integer default_speed = 10;
    final Integer default_mass = 10;

    final Integer[] planets = new Integer[]{4, 5, 6, 7}; //list for planet number
    final Integer[] goods= new Integer[]{1, 2, 3, 4}; //number of loads for delivery
    final Integer[] velocities= new Integer[]{default_speed, 2*default_speed, 3*default_speed}; //speed of the ship
    final Integer[] stars = new Integer[]{default_mass, 2*default_mass, 3*default_mass,
                                                   default_mass, 2*default_mass, 3*default_mass}; //mass of definite star

    final Integer[][] box_variables =
            new Integer[][]{planets, goods, velocities, stars};
    final float WIDTH_BOX = 100, HEIGHT_BOX = 50;

    final String[] list_1 = new String[]{"4", "5", "6", "7"}; //quantity of planets
    final String[] list_2 = new String[]{"1", "2", "3", "4"}; //quantity of goods
    final String[] list_3 = new String[]{"Slow", "Medium", "Fast"}; //velocities of ship
    final String[] list_4 = new String[]{ "Ia", "Ib", "II", "III", "IV","V"}; //types of stars
    private Integer[] chosen_variable = new Integer[4];
    final String[][] box_items = new String[][]{list_1, list_2, list_3, list_4};
      public ConfigScreen(final Process game) {
        this.game = game;
        camera = new OrthographicCamera();
        camera.setToOrtho(false, Process.SCREEN_WIDTH, Process.SCREEN_HEIGHT);



        img = new Texture("Space.jfif");
             stage = new Stage(new ScreenViewport());
             final TextTooltip planet_info_tooltip = new TextTooltip("Average number of planet", Process.gameSkin);
             planet_info_tooltip.setInstant(true);
             final Label planet_info = new Label("Chose number of planet", Process.gameSkin);
             planet_info.setFontScale(1.5f);

             final float posX = (Process.SCREEN_WIDTH - Process.BOX_WIDTH)/2f;
             float posY = 600f;
             for(int i =0; i<4; i++) {
                 final SelectBox<String> box = new SelectBox<>(Process.gameSkin);
                 box.setPosition(posX,posY);
                 box.setSize(Process.BOX_WIDTH, Process.BOX_HEIGHT);
                 box.setItems(box_items[i]);
                 chosen_variable[i] = 1;

                 final Integer index = i;
                  box.addListener(new ChangeListener() {
                    @Override
                     public void changed(ChangeEvent event, Actor actor) {
                         chosen_variable[index] = box_variables[index][box.getSelectedIndex()];
                        }
                  });

                 stage.addActor(box);
                 posY -=100f;
             }

             final String[] ButtonName = new String[]{"X", "Play"};
             final float[][] ButtonPos = new float[][]{{50f,265f},{700f, 250f}};
             for(int i = 0; i<2;i++) {
                 final TextButton button = new TextButton(ButtonName[i], Process.gameSkin);
                 button.setPosition(ButtonPos[i][0], ButtonPos[i][1]);
                 button.setSize(Process.BUTTON_WIDTH, Process.BUTTON_HEIGHT);

                 final Integer index = (i == 0) ? i : 3;
                 if(index == 3) Process.nextScreen[index] = new MainPlayScreen(game, chosen_variable[0]);
                 button.addListener(new ClickListener() {
                     @Override
                     public void clicked(InputEvent event, float x, float y) {
                         game.setScreen(Process.nextScreen[index]);
                     }
                 });
                 stage.addActor(button);
             }
                 planet_info.setPosition(posX - 40f, 630f);
                 planet_info.setSize(WIDTH_BOX, HEIGHT_BOX);
                 stage.addActor(planet_info);

      }

    @Override
    public void show() {
        Gdx.input.setInputProcessor(stage);
    }

    @Override
    public void render(float delta){

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
