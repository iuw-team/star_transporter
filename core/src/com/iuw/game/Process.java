package com.iuw.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.ScaleByAction;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.utils.Scaling;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.ScalingViewport;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import org.lwjgl.system.CallbackI;

import static com.badlogic.gdx.Gdx.graphics;

/**
 * The main game class that stores the main global variables and does all the rendering
 *
 * @author IUW-team
 * @version 0.2
 */
public class Process extends Game {

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
    private OrthographicCamera camera;
    @Override
    public void create() {
        camera = new OrthographicCamera((float)GameSettings.SCREEN_WIDTH, (float)GameSettings.SCREEN_HEIGHT);
        gameSkin = new Skin(Gdx.files.internal("temp_textures/buttons_pack.json"));
        if (GameSettings.game == null) GameSettings.game = this;
        batch = GameSettings.game.getBatch();
        batch.setProjectionMatrix(camera.combined);
        font = new BitmapFont();
        this.setScreen(GetScreenByIndex(0));

    }

    /**
     * Drawing the gameplay
     */
    @Override
    public void render() {
        ScreenUtils.clear(0f, 0f, 0f, 1f);
        setCameraResolution(GameSettings.SCREEN_WIDTH, GameSettings.SCREEN_HEIGHT);
        camera.update();
        batch.setProjectionMatrix(camera.combined);
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
    public void setCameraResolution(float width, float height){
        camera.setToOrtho(false, width, height);
    }
    /**
     * Returns new Stage
     */
    public Stage getStage() {
        ScreenViewport view = new ScreenViewport(camera);
        return new Stage(view, batch);
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
     * Returns Texture by name:
     * ship - ship texture
     * star - star texture
     * planet - planet texture
     */
    public Texture getTextureByName(String name) {
        switch (name) {
            case "ship":
                return new Texture("ship_noflame.png");
            case "ship_flame":
                return new Texture("ship_flame.png");
            case "star":
                return new Texture("sun_n.png");
            case "planet":
                return new Texture("pixel_planet.png");
            case "planet1":
                return new Texture("planet1_n.png");
            case "planet2":
                return new Texture("planet2_n.png");
            case "planet3":
                return new Texture("planet3_n.png");
            case "planet4":
                return new Texture("planet4_n.png");
            case "planet5":
                return new Texture("planet5_n.png");
            case "planet6":
                return new Texture("planet6_n.png");
            case "planet7":
                return new Texture("planet7_n.png");
            case "planet8":
                return new Texture("planet8_n.png");
            case "asteroid":
                return new Texture("pix_asteroid.png");
            case "signFrom":
                return new Texture("here_2.png");
            case "signTo":
                return new Texture("here2.png");
            case "marker":
                return new Texture("marker.png");
            case "black_square":
                return new Texture("black_square.png");
            case "background":
                return new Texture("background.png");
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
