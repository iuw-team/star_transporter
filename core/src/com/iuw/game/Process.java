package com.iuw.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Null;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.TimeUtils;
import com.badlogic.gdx.utils.viewport.ScreenViewport;

import java.util.Arrays;
import java.util.Iterator;
import java.util.concurrent.ThreadLocalRandom;
public class Process extends Game {
	public SpriteBatch batch;
	Texture but;
	Texture ship;
	private Sound dropSound;
	private Sound ship_sound_1, ship_sound_2, ship_sound_3;
	private Music SpaceMusic;
	private OrthographicCamera camera;
	private Rectangle bucket;
	private long ship_sound_time;
	private Stage stage;
	private Label outputLabel;
    public BitmapFont font;
	public Button button3;
	public Button button2;
	static public Skin gameSkin;
	@Override
	public void create () {

		gameSkin = new Skin(Gdx.files.internal("temp_textures/buttons_pack.json"));

		this.setScreen(new MainMenuScreen(this));
		batch = new SpriteBatch(); // thing for drawing image
		font = new BitmapFont();

		but = new Texture("button_ready.png"); //size 300x70
		ship = new Texture("ship.png"); // 150x150
		ship_sound_1= Gdx.audio.newSound(Gdx.files.internal("fly_1.wav"));
		ship_sound_2= Gdx.audio.newSound(Gdx.files.internal("fly_2.wav"));
		ship_sound_3= Gdx.audio.newSound(Gdx.files.internal("fly_3.wav"));

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



/////////////Buttons

//		button2.setSize(300,70);
////		button3.getStyle().imageUp = new TextureRegionDrawable(new TextureRegion(new Texture(Gdx.files.internal("button_off.png"))));
////		button3.getStyle().imageDown = new TextureRegionDrawable(new TextureRegion(new Texture(Gdx.files.internal("button_on.png"))));
//		button2.setPosition(50,600);
//		button2.addListener(new InputListener(){
//			@Override
//			public void touchUp (InputEvent event, float x, float y, int pointer, int button) {
//				System.out.print(button);
//				img = new Texture("Space.jfif");
//			}
//			@Override
//			public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {
//				System.out.print("ye!");
//				return true;
//			}
//		});
 	//		stage.addActor(button2);

	}

	@Override
	public void render () {

		//batch.draw(but, 50, 600);
		//batch.draw(ship, bucket.x, bucket.y);

		super.render();
//		stage.act();
//		stage.draw();

//		if(Gdx.input.isTouched()){
//			Vector3 touchpos = new Vector3();
//			float posX = Gdx.input.getX();
//			float posY = Gdx.input.getY();
//			touchpos.set(posX, posY, 0);
//			camera.unproject(touchpos); // correct camera's system coordinates to touchpos
//			bucket.x = touchpos.x - 150 / 2;
//			bucket.y = touchpos.y - 150 / 2;
//		}
//		if(Gdx.input.isKeyPressed(Input.Keys.LEFT) || Gdx.input.isKeyPressed(Input.Keys.RIGHT)||
//				Gdx.input.isKeyPressed(Input.Keys.DOWN) || Gdx.input.isKeyPressed(Input.Keys.UP)) {
//			if(TimeUtils.nanoTime() - ship_sound_time > 5e9) {
//				switch (MathUtils.random(1, 3)) {
//					case 1:
//						ship_sound_1.play();
//						break;
//					case 2:
//						ship_sound_2.play();
//						break;
//					case 3:
//						ship_sound_3.play();
//						break;
//				}
//				ship_sound_time = TimeUtils.nanoTime();
//			}
//			if (Gdx.input.isKeyPressed(Input.Keys.LEFT)) bucket.x -= 200 * Gdx.graphics.getDeltaTime();
//			if (Gdx.input.isKeyPressed(Input.Keys.RIGHT)) bucket.x += 200 * Gdx.graphics.getDeltaTime();
//			if (Gdx.input.isKeyPressed(Input.Keys.DOWN)) bucket.y -= 200 * Gdx.graphics.getDeltaTime();
//			if (Gdx.input.isKeyPressed(Input.Keys.UP)) bucket.y += 200 * Gdx.graphics.getDeltaTime();
//		}
//		if(bucket.x < 0) bucket.x = 0;
//		if(bucket.x > 600 - 150) bucket.x = 600 - 150;
//		if(bucket.y > 800 - 150) bucket.y = 800 - 150;
//		if(bucket.y < 0) bucket.y = 0;
//	}

//	@Override
//	public void dispose() {
//		batch.dispose();
//		font.dispose();
//		img.dispose();
//		but.dispose();
//		ship.dispose();
//		ship_sound_1.dispose();
//		ship_sound_2.dispose();
//		ship_sound_3.dispose();
//	}
	}
}
