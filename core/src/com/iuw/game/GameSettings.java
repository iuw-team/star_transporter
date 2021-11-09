package com.iuw.game;

public class GameSettings {
    public static Process game;
    /**
     * Переменные конфигурации системы
     */
    private final static Integer[] SYSTEM_VARIABLES = new Integer[]{0, 0, 0, 0};
    /**
     * Возвращает переменную системы по имени
     * "planets" - число планет
     * "goods" - число грузов
     * "velocity" - скорость корабля
     * "star" - массу звезды
     */
    public static Integer getSystemVariableByName(String name){
        switch (name) {
            case "planets":
                return SYSTEM_VARIABLES[0];
            case "goods":
                return SYSTEM_VARIABLES[1];
            case "velocity":
                return SYSTEM_VARIABLES[2];
            case "star":
                return SYSTEM_VARIABLES[3];
            default: throw new IllegalArgumentException("Incorrect name of system's variable");
        }

    }
    public static void setSystemVariables(int index, int value){
        SYSTEM_VARIABLES[index] = value;
    }
    public final static float DEFAULT_CAMERA_SPEED = 500f;
}
