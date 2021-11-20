package com.iuw.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;

/**
 * Playing sounds and audio effects in the game
 */
public class GameSound {
    Sound engineS;
    boolean engineRunning;
    Sound turnS;
    boolean turnRunning;

    Sound doneS;
    Sound spaceAmbienceS;
    Sound explosionS;
    private String[] musicName = new String[]{"SpaceMusic.mp3", "SpaceVacuum.wav"};

    public GameSound() {
        engineS = GameSettings.game.getSound("space_engine.wav");
        engineRunning = false;
        turnS = GameSettings.game.getSound("transformer-1.mp3");
        turnRunning = false;

        doneS = GameSettings.game.getSound("done.mp3");

        explosionS = GameSettings.game.getSound("explosion.mp3");
    }

    /**
     * Running background sounds/music
     */
    public void ambienceStart() {
        spaceAmbienceS = GameSettings.game.getSound(musicName[Process.ChosenSkin]);
        spaceAmbienceS.loop(GameSettings.getVolumeLevelByName("music"));
    }

    /**
     * Stop background sounds/music
     */
    public void ambienceStop() {
        spaceAmbienceS.stop();
    }

    /**
     * Starting the engine sound
     */
    public void engineStart() {
        if (!engineRunning) {
            engineS.loop(GameSettings.getVolumeLevelByName("sound"));
            engineRunning = true;
        }
    }

    /**
     * Stopping the engine sound
     */
    public void engineStop() {
        if (engineRunning) {
            engineS.stop();
            engineRunning = false;
        }
    }

    /**
     * Starting the ship's turn sound
     */
    public void turnStart() {
        if (!turnRunning) {
            turnS.loop(GameSettings.getVolumeLevelByName("sound"));
            turnRunning = true;
        }
    }

    /**
     * Stopping the sound of the ship turning
     */
    public void turnStop() {
        if (turnRunning) {
            turnS.stop();
            turnRunning = false;
        }
    }


    /**
     *  Playing back the sound of the task
     */
    public void done() {
        doneS.play(GameSettings.getVolumeLevelByName("sound"));
    }

    /**
     *  Playing back the sound of the explosion
     */
    public void explosion() {
        explosionS.play();
    }


    /**
     *  Release of resources
     */
    public void dispose(){
        engineS.dispose();
        turnS.dispose();
        doneS.dispose();
        spaceAmbienceS.dispose();
        explosionS.dispose();

    }
}
