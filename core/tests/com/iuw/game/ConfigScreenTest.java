package com.iuw.game;


import com.badlogic.gdx.backends.headless.HeadlessApplication;
import com.badlogic.gdx.backends.headless.HeadlessApplicationConfiguration;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import static org.junit.jupiter.api.Assertions.*;

@RunWith(GdxTestRunner.class)
public class ConfigScreenTest {
    @Test
    public void ApplicationSetupTest(){
        if(Process.SCREEN_HEIGHT !=0 && Process.SCREEN_WIDTH !=0 && Process.BUTTON_WIDTH !=0 &&
                Process.BOX_WIDTH !=0 && Process.BOX_WIDTH !=0 && Process.BOX_HEIGHT !=0)
            assertTrue(true, "Data is good");
        else fail("Some data are lost");
    }
    @Test
    public void ConfigMenuTest(){
    }
    @Test
    public void nextSCreenTest(){
    }
}