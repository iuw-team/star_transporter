package com.iuw.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.TimeUtils;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
public class MainPlayScreen implements Screen {
    final  Process game;
    private Stage stage;
    private OrthographicCamera camera;
    private Sound ship_sound_1, ship_sound_2, ship_sound_3;
    Texture ship;
    private Rectangle ship_box;
    private long ship_sound_time;
    final Integer ship_size = 50;
    private Integer ship_speed, current_speed;
    private float angle;
    private long mass;
    private float g;
    private TextureRegion temp_t_region;

    private void PlaySoundMove(){

    }
    public MainPlayScreen(final Process game, Integer ship_speed){
        this.game = game;
        this.ship_speed = ship_speed;
        mass = 10;
        g= (float) (mass/Math.pow(100,2));
        current_speed = 0;
        camera = new OrthographicCamera();
        camera.setToOrtho(false, 600, 800);
        angle = 0;
        ship_box = new Rectangle();
        ship_box.x =  300;
        ship_box.y = 400;
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

        ScreenUtils.clear(1, 1, 1f, 1);
//
        Vector3 origin = new Vector3();
        if(angle > 6.28 || angle < - 6.28) angle=0;
       // angle+=1; if(angle > 360f) angle = 0f;
        camera.unproject(origin);
//        if(Gdx.input.isTouched()){
//			Vector3 touchpos = new Vector3();
//			float posX = Gdx.input.getX();
//			float posY = Gdx.input.getY();
//			touchpos.set(posX, posY, 0);
//			camera.unproject(touchpos); // correct camera's system coordinates to touchpos
//			bucket.x = touchpos.x - 150 / 2;
//			bucket.y = touchpos.y - 150 / 2;
//		}
        game.batch.begin();
     //   game.batch.draw(ship, ship_box.x, ship_box.y);
        game.batch.draw(temp_t_region, ship_box.x, ship_box.y, ship_size/2, ship_size/2, 50, 50,1 , 1, angle*180/3.14f);
        game.batch.end();
        angle-=0.001;
        ship_box.x-= mass*Math.sin(angle)*Gdx.graphics.getDeltaTime();
        ship_box.y+= mass*Math.cos(angle)*Gdx.graphics.getDeltaTime();
		if(Gdx.input.isKeyPressed(Input.Keys.LEFT) || Gdx.input.isKeyPressed(Input.Keys.RIGHT)|| Gdx.input.isKeyPressed(Input.Keys.DOWN) || Gdx.input.isKeyPressed(Input.Keys.UP)) {
			if(TimeUtils.nanoTime() - ship_sound_time > 7e9) {
				switch (MathUtils.random(1, 3)) {
					case 1:
						ship_sound_1.play();
						break;
					case 2:
						ship_sound_2.play();
						break;
					case 3:
						ship_sound_3.play();
						break;
				}
				ship_sound_time = TimeUtils.nanoTime();
			}
			if (Gdx.input.isKeyPressed(Input.Keys.LEFT)){
                angle+=0.01;

            }
			if (Gdx.input.isKeyPressed(Input.Keys.RIGHT)) {
                angle-=0.01;
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
		if(ship_box.x < 0) ship_box.x = 0;
		if(ship_box.x > 600 - ship_size) ship_box.x = 600 - ship_size; //600 ширина экрана, 50 - ширина корабля
		if(ship_box.y > 800 - ship_size) ship_box.y = 800 - ship_size;
		if(ship_box.y < 0) ship_box.y = 0;
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
    ship.dispose();
    }
}
