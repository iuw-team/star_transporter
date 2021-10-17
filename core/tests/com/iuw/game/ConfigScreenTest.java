package com.iuw.game;

import com.badlogic.gdx.scenes.scene2d.ui.SelectBox;


import static org.junit.jupiter.api.Assertions.*;
class ConfigScreenTest {

    @org.junit.jupiter.api.BeforeEach
    public void AddListenerTest(){
        Process game = new Process();
        ConfigScreen testConfigScreen = new ConfigScreen(game);
       final boolean b = testConfigScreen.ListenBox(new SelectBox(Process.gameSkin), 3);
        if(b)assertTrue(true, "Listener was added");
        else fail("Something was broken");

    }
    @org.junit.jupiter.api.BeforeEach
    public void AddGameSkinTest(){
        if(Process.gameSkin != null) assertTrue(true, "Skin is good");
        else fail("Skin removed");
    }
}