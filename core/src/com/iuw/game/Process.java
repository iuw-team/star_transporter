package com.iuw.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.utils.ScreenUtils;
import org.jetbrains.annotations.NotNull;

public class Process extends Game {
    public SpriteBatch batch;
    public Music SpaceMusic;
    public BitmapFont font;
    public static Skin gameSkin;

    public static Integer SCREEN_WIDTH = 600, SCREEN_HEIGHT = 800;
    public static Integer BUTTON_WIDTH = 400, BUTTON_HEIGHT = 80;
    public static Integer SMALL_BUTTON_WIDTH = 70, SMALL_BUTTON_HEIGHT = 50;
    public static Integer BOX_WIDTH = 100, BOX_HEIGHT = 50;
    public static Integer SLIDER_WIDTH = 250, SLIDER_HEIGHT = 50;
    public static Integer SYSTEM_VARIABLES[] = new Integer[4];   /* Planets, goods, velocities, star types*/
    final public static float MAX_LEVEL = 1f;
    public static Integer ChosenSkin = 0;
    public static float[] VOLUME_LEVELS = new float[]{
            MAX_LEVEL / 2,  /* SOUND_LEVEL*/
            MAX_LEVEL / 2 /* MUSIC_LEVEL*/
    };
    final private Skin[] skins = new Skin[2];

    @Override
    public void create() {
        skins[0] = new Skin(Gdx.files.internal("temp_textures/buttons_pack.json"));
        skins[1] = new Skin(Gdx.files.internal("temp_textures/buttons_pack.json"));
        gameSkin = skins[0];
        this.setScreen(new MainMenuScreen(this));
        batch = new SpriteBatch();
        font = new BitmapFont();
        SpaceMusic = Gdx.audio.newMusic(Gdx.files.internal("Space.mp3"));
        SpaceMusic.setLooping(true);
        //SpaceMusic.play();
    }

    public Screen GetNextScreen(@NotNull Integer index) {
        switch (index) {
            case -2:
                return new MainPlayScreen(this);
            case -1:
                return new SetScreen(this);
            case 0:
                return new MainMenuScreen(this);
            case 1:
                return new ConfigScreen(this);
            case 2:
                return new SetScreen(this);
            default:
                return new MainPlayScreen(this);
        }

    }
}
