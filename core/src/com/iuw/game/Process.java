package com.iuw.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.ui.*;
public class Process extends Game {
	public SpriteBatch batch;
	public Music SpaceMusic;
    public BitmapFont font;
	 public static Skin gameSkin;
	 public static Integer SCREEN_WIDTH = 600, SCREEN_HEIGHT = 800;
	 public static Integer BUTTON_WIDTH = 300, BUTTON_HEIGHT = 70;
	 public static Integer BOX_WIDTH = 100, BOX_HEIGHT = 50;
	 public static Screen[] nextScreen = new Screen[4];
	@Override
	public void create () {
		gameSkin = new Skin(Gdx.files.internal("temp_textures/buttons_pack.json"));
		nextScreen[0] = new MainMenuScreen(this);
		nextScreen[1] = new ConfigScreen(this);
		nextScreen[2] = new SetScreen(this);
		this.setScreen(nextScreen[0]);
		batch = new SpriteBatch();
		font = new BitmapFont();
		//Theme music for beauty
//		SpaceMusic = Gdx.audio.newMusic(Gdx.files.internal("Space.mp3"));
//		SpaceMusic.setLooping(true);
//		SpaceMusic.play();
	}
	@Override
	public void render () {
		Gdx.gl.glClearColor(1f, 1f, 1f, 1f);
		super.render();
	}
}
