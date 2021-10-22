package com.iuw.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import org.jetbrains.annotations.NotNull;

public class Process extends Game {
	public SpriteBatch batch;
	public Music SpaceMusic;
	public BitmapFont font;
	public static Skin gameSkin;
	public static Integer SCREEN_WIDTH = 600, SCREEN_HEIGHT = 800;
	public static Integer BUTTON_WIDTH = 300, BUTTON_HEIGHT = 70;
	public static Integer BOX_WIDTH = 100, BOX_HEIGHT = 50;
	public static Integer SYSTEM_VARIABLES[] = new Integer[4];

	@Override
	public void create() {
		gameSkin = new Skin(Gdx.files.internal("temp_textures/buttons_pack.json"));
		this.setScreen(new MainMenuScreen(this));
		batch = new SpriteBatch();
		font = new BitmapFont();
		//Theme music for beauty
//		SpaceMusic = Gdx.audio.newMusic(Gdx.files.internal("Space.mp3"));
//		SpaceMusic.setLooping(true);
//		SpaceMusic.play();
	}

	@Override
	public void render() {
		Gdx.gl.glClearColor(1f, 1f, 1f, 1f);
		super.render();
	}

	public Screen GetNextScreen(@NotNull Integer index) {
		switch (index) {
			case 0:
				return new MainMenuScreen(this);
			case 1:
				return new ConfigScreen(this);
			case 2:
				return new SetScreen(this);
		}
		return new MainPlayScreen(this, SYSTEM_VARIABLES[2]);
	}
}
