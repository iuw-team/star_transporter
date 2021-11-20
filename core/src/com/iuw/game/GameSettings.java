package com.iuw.game;

/**
 * Class of global variables and methods
 */
public class GameSettings {
    public final static float DEFAULT_CAMERA_SPEED = 500f;
    public final static float MAX_LEVEL = 1f;
    /**
     * System configuration variables
     */
    private final static Integer[] SYSTEM_VARIABLES = new Integer[]{4, 0, 0, 0};
    public static Process game;
    public static float[] VOLUME_LEVELS = new float[]{
            MAX_LEVEL / 2,
            MAX_LEVEL / 2
    };
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
                return SYSTEM_VARIABLES[0];
            case "goods":
                return SYSTEM_VARIABLES[1];
            case "asteroids":
                return SYSTEM_VARIABLES[2];
            case "star":
                return SYSTEM_VARIABLES[3];
            default:
                throw new IllegalArgumentException("Incorrect name of system's variable");
        }

    }
    /**
     * Sets values for system variables
     * @param
     * index - system configuration index
     * @param
     * value - the value of the relevant
     */
    public static void setSystemVariables(int index, int value) {
        SYSTEM_VARIABLES[index] = value;
    }
}
