package com.iuw.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;

/**
 * Воспроизведение звуков и аудио-эффектов в игре
 */
public class GameSound {
    Sound engineS;
    boolean engineRunning;
    Sound turnS;
    boolean turnRunning;

    Sound doneS;
    Sound spaceAmbienceS;
    Sound explosionS;

    public GameSound() {
        engineS = GameSettings.game.getSound("space_engine.wav");
        engineRunning = false;
        turnS = GameSettings.game.getSound("transformer-1.mp3");
        turnRunning = false;

        doneS = GameSettings.game.getSound("done.mp3");
        spaceAmbienceS = GameSettings.game.getSound("space_ambience.wav");
        explosionS = GameSettings.game.getSound("explosion.mp3");
    }

    /**
     * Запуск фоновых звуков/музыки
     */
    public void ambienceStart() {
        spaceAmbienceS.loop(GameSettings.getVolumeLevelByName("music"));
    }

    /**
     * Остановка фоновых звуков/музыки
     */
    public void ambienceStop() {
        spaceAmbienceS.stop();
    }

    /**
     * Запуск звука двигателя
     */
    public void engineStart() {
        if (!engineRunning) {
            engineS.loop(GameSettings.getVolumeLevelByName("sound"));
            engineRunning = true;
        }
    }

    /**
     * Остановка звука двигателя
     */
    public void engineStop() {
        if (engineRunning) {
            engineS.stop();
            engineRunning = false;
        }
    }

    /**
     * Запуск звука поворота корабля
     */
    public void turnStart() {
        if (!turnRunning) {
            turnS.loop(GameSettings.getVolumeLevelByName("sound"));
            turnRunning = true;
        }
    }

    /**
     * Остановка звука поворота корабля
     */
    public void turnStop() {
        if (turnRunning) {
            turnS.stop();
            turnRunning = false;
        }
    }


    /**
     *  Воспроизведение звука выполнения задания
     */
    public void done() {
        doneS.play(GameSettings.getVolumeLevelByName("sound"));
    }

    /**
     *  Воспроизведение звука взрыва
     */
    public void explosion() {
        explosionS.play();
    }


    /**
     *  Освобождение ресурсов
     */
    public void dispose(){
        engineS.dispose();
        turnS.dispose();
        doneS.dispose();
        spaceAmbienceS.dispose();
        explosionS.dispose();

    }
}
