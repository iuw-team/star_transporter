package com.iuw.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.TimeUtils;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import org.jetbrains.annotations.NotNull;

public class MainPlayScreen implements Screen {
    final  Process game;
    private Stage stage;
    private OrthographicCamera camera;
    Texture ship;
    private Rectangle ship_box;
    private long ship_sound_time;
    final float ship_size = 50;
    private Integer ship_speed;
    private float angle;
    private final TextureRegion temp_t_region;
    final private Integer[] data_map;
    final private Sound[] sounds = new Sound[]{
            Gdx.audio.newSound(Gdx.files.internal("fly_1.wav")),
            Gdx.audio.newSound(Gdx.files.internal("fly_2.wav")),
            Gdx.audio.newSound(Gdx.files.internal("fly_3.wav"))
    };

    public MainPlayScreen(final Process game, @NotNull Integer[] data_map){
        this.game = game;
        game.setCurrentScreen(2);
        this.data_map = data_map;
        ship_speed = data_map[2];
        camera = new OrthographicCamera();
        camera.setToOrtho(false, Process.SCREEN_WIDTH, Process.SCREEN_HEIGHT);
        angle = 0;
        ship_box = new Rectangle();
        ship_box.x =  300f;
        ship_box.y = 400f;
        ship_box.width = ship_size;
        ship_box.height = ship_size;
        ship = new Texture("ship_50.png"); // 150x150
        temp_t_region = new TextureRegion(ship);

        stage = new Stage(new ScreenViewport());
        TextButton exit_but = new TextButton("X", Process.gameSkin);
        exit_but.setSize(Process.SMALL_BUTTON_WIDTH, Process.SMALL_BUTTON_HEIGHT);
        exit_but.setPosition(50, 750);
        exit_but.addListener(new ClickListener(){
            @Override
            public void clicked (InputEvent event, float x, float y) {
                game.setScreen(game.GetNextScreen(1));
            }
        });

        stage.addActor(exit_but);
    }


    @Override
    public void show() {
        Gdx.input.setInputProcessor(stage);
    }

    @Override
    public void render(float delta) {
        if(angle > 6.28f || angle < - 6.28f) {
            angle=0f;
        }
        game.batch.begin();
        game.batch.draw(temp_t_region, ship_box.x, ship_box.y, ship_size/2f, ship_size/2f, 50, 50,1 , 1, angle*180/3.14f);
        game.batch.end();
        stage.act();
        stage.draw();
        angle-=0.001f;
        ship_box.x-= Math.sin(angle)*Gdx.graphics.getDeltaTime();
        ship_box.y+= Math.cos(angle)*Gdx.graphics.getDeltaTime();
		if(Gdx.input.isKeyPressed(Input.Keys.LEFT) || Gdx.input.isKeyPressed(Input.Keys.RIGHT)|| Gdx.input.isKeyPressed(Input.Keys.DOWN) || Gdx.input.isKeyPressed(Input.Keys.UP)) {
			if(TimeUtils.nanoTime() - ship_sound_time > 7e9) {
                int i = MathUtils.random(0, 2);
                sounds[i].play(Process.VOLUME_LEVELS[0]);
				ship_sound_time = TimeUtils.nanoTime();
			}
			if (Gdx.input.isKeyPressed(Input.Keys.LEFT)){
                angle+=0.01f;

            }
			if (Gdx.input.isKeyPressed(Input.Keys.RIGHT)) {
                angle-=0.01f;
            }
			if (Gdx.input.isKeyPressed(Input.Keys.DOWN)) {
                ship_box.y -= ship_speed *Math.cos(angle)* Gdx.graphics.getDeltaTime();
                ship_box.x += ship_speed * Math.sin(angle)* Gdx.graphics.getDeltaTime();
            }
			if (Gdx.input.isKeyPressed(Input.Keys.UP)){
                ship_box.y += ship_speed *Math.cos(angle)* Gdx.graphics.getDeltaTime();
                ship_box.x -= ship_speed * Math.sin(angle)* Gdx.graphics.getDeltaTime();
            }
		}
		if(ship_box.x < 0) ship_box.x = 0f;
		if(ship_box.x > 600f - ship_size) ship_box.x = 600f - ship_size; //600 ширина экрана, 50 - ширина корабля
		if(ship_box.y > 800f - ship_size) ship_box.y = 800f - ship_size;
		if(ship_box.y < 0) ship_box.y = 0f;
    }

    @Override
    public void resize(int width, int height) {//don't use yet
        Process.SCREEN_WIDTH = width;
        Process.SCREEN_HEIGHT = height;
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
        sounds[0].dispose();
        sounds[1].dispose();
        sounds[2].dispose();
        ship.dispose();
        stage.dispose();
    }
}
