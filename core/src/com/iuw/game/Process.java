package com.iuw.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.utils.ScreenUtils;
/**
 * Главный игровой класс, хранящий основные глобальные переменные и осуществляющий всю отрисовку
 *
 * @author IUW-team
 * @version 0.2
 */
public class Process extends Game {
    public SpriteBatch batch;
    public Music SpaceMusic;
    public BitmapFont font;
    /**
     * Поле основной фоновой мелодии
     */
    //public Music SpaceMusic;
    /**
     * Поле скина, хранящего информацию о текстурах кнопок и иных компонентов графического интерфейса
     */
    public static Skin gameSkin;
    /**
     * Поля константных значений, таких как ширина и высота кнопок, слайдеров
     */
    public final static Integer
            SCREEN_WIDTH = 800, SCREEN_HEIGHT = 600,
            BUTTON_WIDTH = 300, BUTTON_HEIGHT = 70,
            SMALL_BUTTON_WIDTH = 70, SMALL_BUTTON_HEIGHT = 50,
            BOX_WIDTH = 100, BOX_HEIGHT = 50,
            SLIDER_WIDTH = 250, SLIDER_HEIGHT = 50;

    public final static float
            TYPE = 1.5f,
            MAX_LEVEL = 1f;
    /**
     * Поле, хранящее основную информацию о конфигурации системы
     */
    public static Integer[] SYSTEM_VARIABLES = new Integer[4];   /* Planets, goods, velocities, star types*/
    /**
     * Индекс выбранного скина
     */
    public static Integer ChosenSkin = 0;
    /**
     * Громкость фоновой музыки и игровых звуков
     */
    public static float[] VOLUME_LEVELS = new float[]{
            MAX_LEVEL / 2,  /* SOUND_LEVEL*/
            MAX_LEVEL / 2 /* MUSIC_LEVEL*/
    };
    /**
     * Условный номер выбранного скрина, используемый для перемещения между ними с помощью клавиши Esc
     * 0 - Главное меню
     * 1 - Меню настроек и Меню конфигурации системы
     * 2 - Игровой процесс
     */
    private Integer CURRENT_SCREEN = 0;
    /**
     * Поле, хранящее информацию, нажата ли клавиша Esc
     */
    private boolean exitPressed = false;
    /**
     * Варианты скинов
     */
    final private Skin[] skins = new Skin[2];

    /**
     * Инициализация игры
     */
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
        SpaceMusic.play();
    }

    /**
     * Отрисовка всего игрового процесса
     * Настройка звука фоновой мелодии
     * Обработка нажатия клавиши Ecs
     */
    @Override
    public void render() {
        ScreenUtils.clear(0f, 0f, 0f, 0f);
        super.render();
        SpaceMusic.setVolume(VOLUME_LEVELS[1]);
        gameSkin = skins[ChosenSkin];
        if (Gdx.input.isKeyPressed(Input.Keys.ESCAPE) && !exitPressed) {
            exitPressed = true;
            if (CURRENT_SCREEN == 0) {
                this.exit();
            } else {
                CURRENT_SCREEN--;
                this.setScreen(GetNextScreen(CURRENT_SCREEN));
            }
        } else if (!Gdx.input.isKeyPressed(Input.Keys.ESCAPE) && exitPressed) {
            exitPressed = false;
        }
    }

    /**
     * Метод выхода из игры
     */
    public void exit() {
        this.batch.dispose();
        this.font.dispose();
        this.SpaceMusic.dispose();
        this.dispose();
        Gdx.app.exit();
    }

    /**
     * Метод, определяющий ныне выбранный скрин
     */
    public void setCurrentScreen(int index) {
        CURRENT_SCREEN = index;
    }

    /**
     * Функция получения скрина в соответсвии с его идентификационным номером
     * 0 - Главное игровое меню
     * 1 - Меню конфигурации системы
     * 2 - Меню настроек
     * 3 - Игровой процесс
     */

    public Screen GetNextScreen(int index) {
        switch (index) {
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
