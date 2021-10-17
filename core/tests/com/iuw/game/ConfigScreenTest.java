package com.iuw.game;

import com.badlogic.gdx.scenes.scene2d.ui.SelectBox;
import org.junit.Before;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
class ConfigScreenTest {

    @Before
    public void AddListenerTest(){
        Process game = new Process();
        ConfigScreen testConfigScreen = new ConfigScreen(game);
       final boolean b = testConfigScreen.ListenBox(new SelectBox(Process.gameSkin), 3);
        if(b)assertTrue(true, "Listener was added");
        else fail("Something was broken");

    }
    @Before
    public void AddGameSkinTest(){
        if(Process.gameSkin != null) assertTrue(true, "Skin is good");
        else fail("Skin removed");
    }
    @Test
    void show() {

    }

    @Test
    void render() {
    }

    @Test
    void dispose() {
    }
}