package com.iuw.game;


import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
class ConfigScreenTest {

    @org.junit.jupiter.api.BeforeEach
    public void AddGameSkinTest(){
        if(Process.gameSkin == null) assertTrue(true, "Skin is good");
        else fail("Skin removed");
    }
    @Test
    public void ApplicationSetupTest(){
        if(Process.SCREEN_HEIGHT !=0 && Process.SCREEN_WIDTH !=0 && Process.BUTTON_WIDTH !=0 &&
                Process.BOX_WIDTH !=0 && Process.BOX_WIDTH !=0 && Process.BOX_HEIGHT !=0)
            assertTrue(true, "Data is good");
        else fail("Some data are lost");
    }
}