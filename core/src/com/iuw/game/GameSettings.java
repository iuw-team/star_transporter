package com.iuw.game;

/**
 * Класс глобальных переменных и методов
 */
public class GameSettings {
    public final static float DEFAULT_CAMERA_SPEED = 500f;
    public final static float MAX_LEVEL = 1f;
    /**
     * Переменные конфигурации системы
     */
    private final static Integer[] SYSTEM_VARIABLES = new Integer[]{4, 0, 0, 0};
    public static Process game;
    public static float[] VOLUME_LEVELS = new float[]{
            MAX_LEVEL / 2,
            MAX_LEVEL / 2
    };
    private static String result;

    /**
     * Возвращает значение уровня звука по имени:
     * "sound" - звуки игры
     * "music" - фоновая музыка
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
     * Возвращает Заголовок ResultScreen
     */
    public static String getGameResult() {
        return result;
    }
    /**
     * Настроить Заголовок ResultScreen
     */
    public static void setGameResult(String result) {
        GameSettings.result = result;
    }
    /**
     * Возвращает переменную системы по имени
     * "planets" - число планет
     * "goods" - число грузов
     * "asteroids" - число астероидов
     * "star" - массу звезды
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
     * Задаёт значения системным переменным
     * @param
     * index - индекс системной конфигурации
     * @param
     * value - значение соответсвующего
     */
    public static void setSystemVariables(int index, int value) {
        SYSTEM_VARIABLES[index] = value;
    }
}
