package com.iuw.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;

public class GameSound {
    Sound engineS;
    boolean engineRunning;
    Sound turnS;
    boolean turnRunning;

    Sound doneS;
    Sound spaceAmbienceS;
    Sound explosionS;

    Sound getSound(String filename) {
        return Gdx.audio.newSound(Gdx.files.internal("Sounds/".concat(filename)));
    }

    public GameSound() {
        engineS = getSound("space_engine.wav");
        engineRunning = false;
        turnS = getSound("transformer-1.mp3");
        turnRunning = false;

        doneS = getSound("done.mp3");
        spaceAmbienceS = getSound("space_ambience.wav");
        explosionS = getSound("explosion.mp3");
        explosionS = getSound("explosion.mp3");
    }

    public void ambienceStart() {
        spaceAmbienceS.loop();
    }
    public void ambienceStop() {
        spaceAmbienceS.stop();
    }

    public void engineStart() {
        if (!engineRunning) {
            engineS.loop();
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
            turnS.loop();
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
        doneS.play();
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
