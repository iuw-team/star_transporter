package com.iuw.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;

public class GameSound {
    Sound engineS;
    boolean engineRunning;
    Sound turnS;
    boolean turnRunning;
    Sound musicS;
    Sound doneS;
    Sound spaceAmbienceS;
    Sound explosionS;

    public GameSound() {
        engineS = GameSettings.game.getSound("space_engine.wav");
        engineRunning = false;
        turnS = GameSettings.game.getSound("transformer-1.mp3");
        turnRunning = false;
        musicS = GameSettings.game.getSound("meet-the-princess.wav");
        doneS = GameSettings.game.getSound("done.mp3");
        spaceAmbienceS = GameSettings.game.getSound("space_ambience.wav");
        explosionS = GameSettings.game.getSound("explosion.mp3");
    }

    public void ambienceStart() {
        spaceAmbienceS.loop(GameSettings.getVolumeLevelByName("music"));
        musicS.loop(GameSettings.getVolumeLevelByName("music"));
    }
    public void ambienceStop() {
        spaceAmbienceS.stop();
        musicS.stop();
    }

    public void engineStart() {
        if (!engineRunning) {
            engineS.loop(GameSettings.getVolumeLevelByName("sound"));
            engineRunning = true;
        }
    }

    public void engineStop() {
        if (engineRunning) {
            engineS.stop();
            engineRunning = false;
        }
    }

    public void turnStart() {
        if (!turnRunning) {
            turnS.loop(GameSettings.getVolumeLevelByName("sound"));
            turnRunning = true;
        }
    }

    public void turnStop() {
        if (turnRunning) {
            turnS.stop();
            turnRunning = false;
        }
    }

    public void done() {
        doneS.play(GameSettings.getVolumeLevelByName("sound"));
    }

    public void explosion() {
        explosionS.play();
    }

    public void dispose(){
        engineS.dispose();
        turnS.dispose();
        doneS.dispose();
        spaceAmbienceS.dispose();
        explosionS.dispose();

    }
}
