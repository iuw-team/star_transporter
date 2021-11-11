package com.iuw.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.utils.Array;

public class GameSettings {
    private GameSettings(){}
    public final static float DEFAULT_CAMERA_SPEED = 500f;
    /**
     * Переменные конфигурации системы
     */
    private final static Integer[] SYSTEM_VARIABLES = new Integer[]{0, 0, 0, 0};
    public final static float MAX_LEVEL = 1f;
    public static Process game;
    //public static boolean soundIsPlaying = false;
    public static float elapsedTime = 0f;
    public static boolean soundIsPlaying = false;
    public final static float playTime = 1f;
    public static float[] VOLUME_LEVELS = new float[]{
            MAX_LEVEL / 2,
            MAX_LEVEL / 2
    };
    /**
     * Возвращает значение уровня звука по имени:
     * "sound" - звуки игры
     * "music" - фоновая музыка
     */
    public static float getVolumeLevelByName(String name){
        switch(name){
            case "sound": return VOLUME_LEVELS[0];
            case "music": return VOLUME_LEVELS[1];
            default:
                throw new IllegalArgumentException("Incorrect name of sound/music");
        }
    }
    /**
     * Возвращает переменную системы по имени
     * "planets" - число планет
     * "goods" - число грузов
     * "velocity" - скорость корабля
     * "star" - массу звезды
     */
    public static Integer getSystemVariableByName(String name) {
        switch (name) {
            case "planets":
                return SYSTEM_VARIABLES[0];
            case "goods":
                return SYSTEM_VARIABLES[1];
            case "velocity":
                return SYSTEM_VARIABLES[2];
            case "star":
                return SYSTEM_VARIABLES[3];
            default:
                throw new IllegalArgumentException("Incorrect name of system's variable");
        }

    }
    /**
     * Настроить значение глобальной переменной системы по индексу
     */
    public static void setSystemVariables(int index, int value) {
        SYSTEM_VARIABLES[index] = value;
    }
}
