package com.iuw.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Null;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.TimeUtils;

import java.util.Arrays;
import java.util.Iterator;
import java.util.concurrent.ThreadLocalRandom;

public class Process extends ApplicationAdapter {
	private SpriteBatch batch;
	Texture img;
	Texture but;
	Texture ship;
	private Sound dropSound;
	private Sound ship_sound_1, ship_sound_2, ship_sound_3;
	private Music SpaceMusic;
	private OrthographicCamera camera;
	private Rectangle bucket;
	private long ship_sound_time;
	
	@Override
	public void create () {
		batch = new SpriteBatch(); // thing for drawing image
		img = new Texture("main_theme.jpg");
		but = new Texture("button_ready.png"); //size 300x70
		ship = new Texture("ship.png"); // 150x150
		ship_sound_1= Gdx.audio.newSound(Gdx.files.internal("fly_1.wav"));
		ship_sound_2= Gdx.audio.newSound(Gdx.files.internal("fly_2.wav"));
		ship_sound_3= Gdx.audio.newSound(Gdx.files.internal("fly_3.wav"));
		camera = new OrthographicCamera();
		camera.setToOrtho(false, 600, 800);
		//Theme music for beauty
//		SpaceMusic = Gdx.audio.newMusic(Gdx.files.internal("Space.mp3"));
//		SpaceMusic.setLooping(true);
//		SpaceMusic.play();
		//
		bucket = new Rectangle();
		bucket.x = 600/2 - 150/2;
		bucket.y = 600;
		bucket.width = 150;
		bucket.height = 150;
	}

	@Override
	public void render () {
		ScreenUtils.clear(0, 0, 0.3f, 1);

        camera.update();
		batch.setProjectionMatrix(camera.combined);

		batch.begin();
		batch.draw(img, 0, 0);
		batch.draw(but, 50, 600);
		batch.draw(ship, bucket.x, bucket.y);
		batch.end();

		if(Gdx.input.isTouched()){
			Vector3 touchpos = new Vector3();
			float posX = Gdx.input.getX();
			float posY = Gdx.input.getY();
			touchpos.set(posX, posY, 0);
			camera.unproject(touchpos); // correct camera's system coordinates to touchpos
			bucket.x = touchpos.x - 150 / 2;
			bucket.y = touchpos.y - 150 / 2;
		}
		if(Gdx.input.isKeyPressed(Input.Keys.LEFT) || Gdx.input.isKeyPressed(Input.Keys.RIGHT)||
				Gdx.input.isKeyPressed(Input.Keys.DOWN) || Gdx.input.isKeyPressed(Input.Keys.UP)) {
			if(TimeUtils.nanoTime() - ship_sound_time > 5e9) {
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
			if (Gdx.input.isKeyPressed(Input.Keys.LEFT)) bucket.x -= 200 * Gdx.graphics.getDeltaTime();
			if (Gdx.input.isKeyPressed(Input.Keys.RIGHT)) bucket.x += 200 * Gdx.graphics.getDeltaTime();
			if (Gdx.input.isKeyPressed(Input.Keys.DOWN)) bucket.y -= 200 * Gdx.graphics.getDeltaTime();
			if (Gdx.input.isKeyPressed(Input.Keys.UP)) bucket.y += 200 * Gdx.graphics.getDeltaTime();
		}
		if(bucket.x < 0) bucket.x = 0;
		if(bucket.x > 600 - 150) bucket.x = 600 - 150;
		if(bucket.y > 800 - 150) bucket.y = 800 - 150;
		if(bucket.y < 0) bucket.y = 0;
	}

	@Override
	public void dispose () {
		batch.dispose();
		img.dispose();
		but.dispose();
		ship.dispose();
		ship_sound_1.dispose();
		ship_sound_2.dispose();
		ship_sound_3.dispose();
	}
}
