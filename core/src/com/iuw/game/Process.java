package com.iuw.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.ScreenViewport;

/**
 * The main game class that stores the main global variables and does all the rendering
 *
 * @author IUW-team
 * @version 0.2
 */
public class Process extends Game {
    /**
     * Constant value fields, such as width and height of buttons, sliders
     */
    public final static Integer
            SCREEN_WIDTH = 800, SCREEN_HEIGHT = 600,
            BUTTON_WIDTH = 300, BUTTON_HEIGHT = 70,
            SMALL_BUTTON_WIDTH = 70, SMALL_BUTTON_HEIGHT = 50,
            BOX_WIDTH = 100, BOX_HEIGHT = 50,
            SLIDER_WIDTH = 250, SLIDER_HEIGHT = 50;
    public final static float
            TYPE = 1.5f;
    /**
     * A skin field that stores texture information for buttons and other GUI components
     */
    public static Skin gameSkin;
    /**
     * Index of the selected skin
     */
    public static Integer ChosenSkin = 0;
    public SpriteBatch batch;
    private BitmapFont font;
    /**
     * The conditional number of the selected screen used to move between them using the Esc key
     * 0 - Main menu
     * 1 - Settings menu and System Configuration menu
     * 2 - Game process
     */
    private Integer CURRENT_SCREEN = 0;
    /**
     * Field storing information on whether the Esc key is pressed
     */
    private boolean exitPressed = false;

    /**
     * Initialising the renderer and other components
     */
    @Override
    public void create() {
        if (GameSettings.game == null) GameSettings.game = this;
        gameSkin = new Skin(Gdx.files.internal("temp_textures/buttons_pack.json"));
        batch = GameSettings.game.getBatch();
        this.setScreen(GetScreenByIndex(0));
        font = new BitmapFont();
    }

    /**
     * Drawing the gameplay
     */
    @Override
    public void render() {
        ScreenUtils.clear(0f, 0f, 0f, 1f);
        super.render();
        if (Gdx.input.isKeyPressed(Input.Keys.ESCAPE) && !exitPressed) {
            exitPressed = true;
            if (CURRENT_SCREEN == 0) {
                GameSettings.game.exit();
            } else {
                CURRENT_SCREEN--;
                this.setScreen(GetScreenByIndex(CURRENT_SCREEN));

            }
        } else if (!Gdx.input.isKeyPressed(Input.Keys.ESCAPE) && exitPressed) {
            exitPressed = false;
        }
    }

    /**
     * Exiting the game
     */
    public void exit() {
        GameSettings.game.batch.dispose();
        this.font.dispose();
        this.dispose();
        Gdx.app.exit();
    }

    /**
     * Returns new Stage
     */
    public Stage getStage() {
        return new Stage(new ScreenViewport(), batch);
    }

    /**
     * Returns new Batch
     */
    public SpriteBatch getBatch() {
        return new SpriteBatch();
    }

    /**
     * Returns new SelectBox <String>
     */
    public SelectBox<String> getSelectBox() {
        return new SelectBox<>(gameSkin);
    }

    /**
     * Returns a new TextButton with text on it
     */
    public TextButton getTextButton(String text) {
        return new TextButton(text, gameSkin);
    }

    /**
     * Returns new Label with text caption
     */
    public Label getLabel(String text) {
        return new Label(text, gameSkin);
    }

    /**
     * Returns new CheckBox with text
     */
    public CheckBox getCheckBox(String text) {
        return new CheckBox(text, gameSkin);
    }

    /**
     * Returns new Slider
     */
    public Slider getSlider() {
        return new Slider(0f, GameSettings.MAX_LEVEL, 0.001f, false, gameSkin);
    }

    /**
     * Returns new ShapeRenderer
     */
    public ShapeRenderer getShapeRenderer() {
        return new ShapeRenderer();
    }

    /**
     * Returns Texture by name:
     * ship - ship texture
     * star - star texture
     * planet - planet texture
     */
    public Texture getTextureByName(String name) {
        switch (name) {
            case "ship":
                return new Texture("pixel_ship.png");
            case "star":
                return new Texture("star_0.png");
            case "planet":
                return new Texture("pixel_planet.png");
            case "planet1":
                return new Texture("planet1.png");
            case "planet2":
                return new Texture("planet2.png");
            case "planet3":
                return new Texture("planet3.png");
            case "planet4":
                return new Texture("planet4.png");
            case "planet5":
                return new Texture("planet5.png");
            case "planet6":
                return new Texture("planet6.png");
            case "planet7":
                return new Texture("planet7.png");
            case "planet8":
                return new Texture("planet8.png");
            case "asteroid":
                return new Texture("pix_asteroid.png");
            case "signFrom":
                return new Texture("here.png");
            case "signTo":
                return new Texture("lasthere.png");
            case "marker":
                return new Texture("marker.png");
            case "black_square":
                return new Texture("black_square.png");
            default:
                throw new IllegalArgumentException("Incorrect name of system's variable");
        }

    }

    /**
     * Returns Sound by name:
     * ship - sounds of ship traffic
     * collision - the sound of a collision
     */
    Sound getSound(String filename) {
        return Gdx.audio.newSound(Gdx.files.internal("Sounds/".concat(filename)));
    }

    public int getCurrentScreen() {
        return CURRENT_SCREEN;
    }

    /**
     * The method that determines the currently selected screen:
     * 0 - MainMenuScreen
     * 1 - ConfigScreen и SetScreen
     * 2 - MainPlayScreen
     */
    public void setCurrentScreen(int index) {
        CURRENT_SCREEN = index;
    }

    /**
     * Function to retrieve a screenshot by its ID:
     * 0 - MainMenuScreen
     * 1 - ConfigScreen
     * 2 - SetScreen
     * 3 - MainPlayScreen
     */
    public Screen GetScreenByIndex(int index) {

        switch (index) {
            case 0:
                return new MainMenuScreen(GameSettings.game);
            case 1:
                return new ConfigScreen(GameSettings.game);
            case 2:

                return new SetScreen(GameSettings.game);
            case 3:
                return new MainPlayScreen(GameSettings.game);
            case 4:
                return new ResultScreen(GameSettings.game);
            default:
                throw new IllegalArgumentException("Incorrect index of screen");

        }
    }
}
