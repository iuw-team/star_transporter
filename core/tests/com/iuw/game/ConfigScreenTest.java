package com.iuw.game;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;


public class ConfigScreenTest extends LibgdxUnitTest {
    @Test
    public void testingSomething() {//throws InterruptedException {

        //   LibgdxUnitTest.init();
//        game = new Process();
        // boot the game
//        Lwjgl3ApplicationConfiguration config = new Lwjgl3ApplicationConfiguration();
//
//        // wait for the game to be ready
//        TimeUnit.SECONDS.sleep(5);
//        new Lwjgl3Application(game, config);

        // Test the results
        //    assertNull("Bad", Process.gameSkin);
//        temp_screen = game.GetNextScreen(1);
//        if (temp_screen != null) System.out.println("screen is good");
//        else System.out.println("Impossible get new screen");
        // do something
        // suppose the SpecialGameController is a special class that simply executes game logic that is used normally in the game
        //SpecialGameController.letPlayerWalkUp(20);

        // wait again
        //TimeUnit.SECONDS.sleep(5);

        // Test again
        //assertTrue(getSomeValue());
    }

    @Test

    public void ApplicationSetupTest() {
        if (Process.SCREEN_HEIGHT != 0 && Process.SCREEN_WIDTH != 0 && Process.BUTTON_WIDTH != 0 &&
                Process.BUTTON_HEIGHT != 0 && Process.BOX_WIDTH != 0 && Process.BOX_HEIGHT != 0 &&
                Process.SMALL_BUTTON_WIDTH != 0 && Process.SMALL_BUTTON_HEIGHT != 0 &&
                Process.SLIDER_WIDTH != 0 && Process.SLIDER_HEIGHT != 0 &&
                Process.ChosenSkin != null && Process.VOLUME_LEVELS[0] != 0 && Process.VOLUME_LEVELS[1] != 0)
            System.out.println("Data is good");
        else System.out.println("Some data are lost");
    }

    @Test
    public void ConfigMenuTest() {
        ConfigScreen config = Mockito.mock(ConfigScreen.class);
        Mockito.doNothing().
                doThrow(new RuntimeException())
                .when(config).show();

        Mockito.doNothing().
                doThrow(new RuntimeException())
                .when(config).render(30f);

        Mockito.doNothing().
                doThrow(new RuntimeException())
                .when(config).resume();

        Mockito.doNothing().
                doThrow(new RuntimeException())
                .when(config).pause();
        Mockito.doNothing().
                doThrow(new RuntimeException())
                .when(config).resize(Process.SCREEN_WIDTH, Process.SCREEN_HEIGHT);
        Mockito.doNothing().
                doThrow(new RuntimeException())
                .when(config).hide();

        Mockito.doNothing().
                doThrow(new RuntimeException())
                .when(config).dispose();
    }
}