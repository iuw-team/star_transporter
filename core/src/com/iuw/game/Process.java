package com.iuw.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.ui.*;
public class Process extends Game {
	public SpriteBatch batch;
	public Music SpaceMusic;
    public BitmapFont font;
	 public static Skin gameSkin = new Skin(Gdx.files.internal("temp_textures/buttons_pack.json"));
	 public static Integer WIDTH = 600, HEIGHT = 800;
	@Override
	public void create () {
		this.setScreen(new MainMenuScreen(this));
		batch = new SpriteBatch();
		font = new BitmapFont();
		//Theme music for beauty
//		SpaceMusic = Gdx.audio.newMusic(Gdx.files.internal("Space.mp3"));
//		SpaceMusic.setLooping(true);
//		SpaceMusic.play();
	}
	@Override
	public void render () {
		super.render();
	}
}
