package com.iuw.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.ScreenViewport;

/**
 * Главный игровой класс, хранящий основные глобальные переменные и осуществляющий всю отрисовку
 *
 * @author IUW-team
 * @version 0.2
 */
public class Process extends Game {
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
     * Поле скина, хранящего информацию о текстурах кнопок и иных компонентов графического интерфейса
     */
    public static Skin gameSkin;
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
     * Варианты скинов
     */
    final private Skin[] skins = new Skin[2];
    public SpriteBatch batch;
    /**
     * Поле основной фоновой мелодии
     */
    public Music SpaceMusic;
    private BitmapFont font;
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
     * Инициализация отрисовщика и остальных компонентов
     */
    @Override
    public void create() {
        skins[0] = new Skin(Gdx.files.internal("temp_textures/buttons_pack.json"));
        skins[1] = new Skin(Gdx.files.internal("temp_textures/buttons_pack.json"));
        GameSettings.game = this;
        gameSkin = skins[ChosenSkin];
        batch = getBatch();
        this.setScreen(new MainMenuScreen(this));
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
        ScreenUtils.clear(0f, 0f, 0f, 1f);
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
     * Возвращает new Stage
     */
    public Stage getStage(){
        return new Stage(new ScreenViewport(), batch);
    }
    /**
     * Возвращает new Batch
     */
    public SpriteBatch getBatch(){
        return new SpriteBatch();
    }
    /**
     * Возвращает new SelectBox <String>
     */
    public SelectBox<String> getSelectBox(){
        return new SelectBox(gameSkin);
    }
    /**
     * Возвращает new TextButton с надписью text
     */
    public TextButton getTextButton(String text){
        return new TextButton(text, gameSkin);
    }
    /**
     * Возвращает new Label с надписью text
     */
    public Label getLabel(String text){
        return new Label(text, gameSkin);
    }
    /**
     * Возвращает new CheckBox с надписью text
     */
    public CheckBox getCheckBox(String text){
        return new CheckBox(text, gameSkin);
    }
    /**
     * Возвращает new Slider
     */
    public Slider getSlider(){
        return new Slider(0f, Process.MAX_LEVEL, 0.001f, false, gameSkin);
    }
    /**
     * Метод, определяющий ныне выбранный скрин
     * 0 - MainMenuScreen
     * 1 - ConfigScreen и SetScreen
     * 2 - MainPlayScreen
     */
    public void setCurrentScreen(int index) {
        CURRENT_SCREEN = index;
    }
    /**
     * Функция получения скрина в соответсвии с его идентификационным номером
     * 0 - MainMenuScreen
     * 1 - ConfigScreen
     * 2 - SetScreen
     * 3 - MainPlayScreen
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
