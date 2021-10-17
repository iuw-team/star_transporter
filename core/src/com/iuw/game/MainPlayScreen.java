package com.iuw.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.TimeUtils;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
public class MainPlayScreen implements Screen {
    final  Process game;
    private Stage stage;
    private OrthographicCamera camera;
    private final Sound ship_sound_1, ship_sound_2, ship_sound_3;
    Texture ship;
    private Rectangle ship_box;
    private long ship_sound_time;
    final float ship_size = 50;
    private Integer ship_speed;
    private float angle;
    private final TextureRegion temp_t_region;

    public MainPlayScreen(final Process game, Integer ship_speed){
        this.game = game;
        this.ship_speed = ship_speed;
        camera = new OrthographicCamera();
        camera.setToOrtho(false, Process.WIDTH, Process.HEIGHT);
        angle = 0;
        ship_box = new Rectangle();
        ship_box.x =  300f;
        ship_box.y = 400f;
        ship_box.width = ship_size;
        ship_box.height = ship_size;
        ship = new Texture("ship_50.png"); // 150x150
        temp_t_region = new TextureRegion(ship);

        ship_sound_1= Gdx.audio.newSound(Gdx.files.internal("fly_1.wav"));
        ship_sound_2= Gdx.audio.newSound(Gdx.files.internal("fly_2.wav"));
        ship_sound_3= Gdx.audio.newSound(Gdx.files.internal("fly_3.wav"));
    }


    @Override
    public void show() {
        Gdx.input.setInputProcessor(stage);

    }

    @Override
    public void render(float delta) {

        ScreenUtils.clear(1f, 1f, 1f, 1f);
        if(angle > 6.28f || angle < - 6.28f) {
            angle=0f;
        }
        game.batch.begin();
        game.batch.draw(temp_t_region, ship_box.x, ship_box.y, ship_size/2f, ship_size/2f, 50, 50,1 , 1, angle*180/3.14f);
        game.batch.end();
        angle-=0.001f;
        ship_box.x-= Math.sin(angle)*Gdx.graphics.getDeltaTime();
        ship_box.y+= Math.cos(angle)*Gdx.graphics.getDeltaTime();
		if(Gdx.input.isKeyPressed(Input.Keys.LEFT) || Gdx.input.isKeyPressed(Input.Keys.RIGHT)|| Gdx.input.isKeyPressed(Input.Keys.DOWN) || Gdx.input.isKeyPressed(Input.Keys.UP)) {
			if(TimeUtils.nanoTime() - ship_sound_time > 7e9) {
				switch (MathUtils.random(1, 3)) {
					case 1:
						ship_sound_1.play();
						break;
					case 2:
						ship_sound_2.play();
						break;
                    default:
                        ship_sound_3.play();
                        break;
				}
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
    }

    @Override
    public void pause() {//don't use yet
    }

    @Override
    public void resume() {//don't use yet
    }

    @Override
    public void hide() {//don't use yet
    }

    @Override
    public void dispose() {
    ship.dispose();
    }
}
