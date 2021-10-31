package com.iuw.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.backends.headless.HeadlessApplication;
import com.badlogic.gdx.backends.headless.HeadlessApplicationConfiguration;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import org.junit.Before;
import org.junit.runner.RunWith;
import org.junit.Test;
import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import com.iuw.game.Process;

import java.util.concurrent.TimeUnit;

@RunWith(GdxTestRunner.class)

public class ConfigScreenTest {
    Process game;
    Screen temp_screen;

    @Test
    public void testingSomething() throws InterruptedException {

        game = new Process();
        // boot the game
        LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
        new LwjglApplication(game, config);
        game.create();
        game.render();
        // wait for the game to be ready
        TimeUnit.SECONDS.sleep(5);

        // Test the results
        assertNull("Bad", Process.gameSkin);
        if(Process.gameSkin == null) System.out.println("Impossible get new screen");
//        temp_screen = game.GetNextScreen(1);
//        if (temp_screen != null) System.out.println("screen is good");
//        else System.out.println("Impossible get new screen");
        // do something
        // suppose the SpecialGameController is a special class that simply executes game logic that is used normally in the game
        //SpecialGameController.letPlayerWalkUp(20);

        // wait again
        TimeUnit.SECONDS.sleep(5);

        // Test again
        //assertTrue(getSomeValue());
        }

    @Test

    public void ApplicationSetupTest(){
        if(Process.SCREEN_HEIGHT !=0 && Process.SCREEN_WIDTH !=0 && Process.BUTTON_WIDTH !=0 &&
                Process.BUTTON_HEIGHT !=0 && Process.BOX_WIDTH !=0 && Process.BOX_HEIGHT !=0 &&
                Process.SMALL_BUTTON_WIDTH != 0 && Process.SMALL_BUTTON_HEIGHT != 0 &&
                Process.SLIDER_WIDTH != 0 && Process.SLIDER_HEIGHT != 0 && Process.TYPE != 0f &&
                Process.ChosenSkin != null && Process.VOLUME_LEVELS[0] != 0 && Process.VOLUME_LEVELS[1] != 0)
            System.out.println("Data is good");
        else System.out.println("Some data are lost");
    }
    @Test
    public void ConfigMenuTest(){
        game = (new Process());
        ConfigScreen config = new ConfigScreen(game);
        config.render(10);
        temp_screen = game.GetNextScreen(1);
        if (temp_screen != null) System.out.println("screen is good");
        else System.out.println("Impossible get new screen");
    }
    @Test
    public void nextScreenTest(){
//

    }
}