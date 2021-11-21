package com.iuw.game;

/**
 * Class of global variables and methods
 */
public class GameSettings {
    private GameSettings(){}
    public static final float DEFAULT_CAMERA_SPEED = 500f;
    public static final float MAX_LEVEL = 1f;
    /**
     * Constant value fields, such as width and height of buttons, sliders
     */
    public static int SCREEN_WIDTH = 800, SCREEN_HEIGHT = 600;
    public static float
            BUTTON_WIDTH = SCREEN_WIDTH/2.5f, BUTTON_HEIGHT = SCREEN_HEIGHT/8f,
            SMALL_BUTTON_WIDTH = SCREEN_WIDTH/10f, SMALL_BUTTON_HEIGHT = SCREEN_HEIGHT/14f,
            BOX_WIDTH = SCREEN_WIDTH/8f, BOX_HEIGHT = SCREEN_HEIGHT/12f,
            SLIDER_WIDTH = SCREEN_WIDTH/3.5f, SLIDER_HEIGHT = SCREEN_HEIGHT/12f,
            TYPE = 1.5f;

    /**
     * System configuration variables
     */
    private static final Integer[] STANDART_WIDTH = new Integer[]{800, 1280, 1600, 1920};
    private static final Integer[] STANDART_HEIGTH = new Integer[]{600, 1024, 1200, 1200};
    private static final Integer[] INDEX_SYSTEM_VARIABLE = new Integer[]{2, 1, 2, 1};
    private static final Integer[][] SYSTEM_VARIABLES = new Integer[][]{
            new Integer[]{4, 5, 6, 7}, //list for planet number
            new Integer[]{1, 2, 3, 4}, //number of loads for delivery
            new Integer[]{1, 3, 5, 7, 10}, //quantity of asteroids
            new Integer[]{1, 2, 3, 4, 5, 6}, //system speedFactor or may be exactly star mass
    };
    public static Process game;
    private static void updateUISizes(){
                BUTTON_WIDTH = SCREEN_WIDTH/2.5f; BUTTON_HEIGHT = SCREEN_HEIGHT/8.5f;
                SMALL_BUTTON_WIDTH = SCREEN_WIDTH/10f; SMALL_BUTTON_HEIGHT = SCREEN_HEIGHT/16f;
                BOX_WIDTH = SCREEN_WIDTH/8f; BOX_HEIGHT = SCREEN_HEIGHT/12f;
                SLIDER_WIDTH = SCREEN_WIDTH/3.5f; SLIDER_HEIGHT = SCREEN_HEIGHT/12f;
    }
    public static float[] VOLUME_LEVELS = new float[]{
            MAX_LEVEL / 2,
            MAX_LEVEL / 2
    };
    private static int CURRENT_RESOLUTION = 0;
    private static int goodsDelivered = 0;
    private static String result;

    /**
     * Returns the sound level value by name:
     * "sound" - the sounds of the game
     * "music" - background music
     */
    public static float getVolumeLevelByName(String name) {
        switch (name) {
            case "sound":
                return VOLUME_LEVELS[0];
            case "music":
                return VOLUME_LEVELS[1];
            default:
                throw new IllegalArgumentException("Incorrect name of sound/music");
        }
    }

    /**
     * Returns the ResultScreen Header
     */
    public static String getGameResult() {
        return result;
    }

    /**
     * Set ResultScreen Header
     */
    public static void setGameResult(String result) {
        GameSettings.result = result;
    }

    /**
     * Returns a system variable by name
     * "planets" - the number of planets
     * "goods" - number of goods
     * "asteroids" - the number of asteroids
     * "star" - the mass of the star
     */
    public static Integer getSystemVariableByName(String name) {
        switch (name) {
            case "planets":
                return SYSTEM_VARIABLES[0][INDEX_SYSTEM_VARIABLE[0]];
            case "goods":
                return SYSTEM_VARIABLES[1][INDEX_SYSTEM_VARIABLE[1]];
            case "asteroids":
                return SYSTEM_VARIABLES[2][INDEX_SYSTEM_VARIABLE[2]];
            case "star":
                return SYSTEM_VARIABLES[3][INDEX_SYSTEM_VARIABLE[3]];
            default:
                throw new IllegalArgumentException("Incorrect name of system's variable");
        }
    }

    public static Integer getIndexSystemVariable(int index) {
        return INDEX_SYSTEM_VARIABLE[index];
    }

    public static Integer getDeliveredGoods() {
        return goodsDelivered;
    }

    public static void setDeliveredGoods(int num) {
        goodsDelivered = num;
    }

    public static int getCurrentResolution() {
        return CURRENT_RESOLUTION;
    }

    public static void setResolution(int index) {
        CURRENT_RESOLUTION = index;
        SCREEN_WIDTH = STANDART_WIDTH[index];
        SCREEN_HEIGHT = STANDART_HEIGTH[index];
        updateUISizes();
    }

    /**
     * Sets values for system variables
     *
     * @param index - system configuration index
     * @param value - the value of the relevant
     */
    public static void setIndexSystemVariables(int index, int value) {
        INDEX_SYSTEM_VARIABLE[index] = value;
    }
}
