package com.iuw.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;


public class GlobalTest extends LibgdxUnitTest {

    @Test
    public void ApplicationSetupTest() {
        if (GameSettings.SCREEN_HEIGHT != 0 && GameSettings.SCREEN_WIDTH != 0 && GameSettings.BUTTON_WIDTH != 0 &&
                GameSettings.BUTTON_HEIGHT != 0 && GameSettings.BOX_WIDTH != 0 && GameSettings.BOX_HEIGHT != 0 &&
                GameSettings.SMALL_BUTTON_WIDTH != 0 && GameSettings.SMALL_BUTTON_HEIGHT != 0 &&
                GameSettings.SLIDER_WIDTH != 0 && GameSettings.SLIDER_HEIGHT != 0 &&
                Process.ChosenSkin != null && GameSettings.VOLUME_LEVELS[0] != 0 && GameSettings.VOLUME_LEVELS[1] != 0)
            System.out.println("Data is good");
        else System.out.println("Some data are lost");
    }
    @Test
    public void UITest() {


        GameSettings.setResolution(0);
        GameSettings.setDeliveredGoods(2);
        GameSettings.setGameResult("Hello World!");


        Process game = Mockito.mock(Process.class);
        Stage stage = Mockito.mock(Stage.class);
        SpriteBatch batch = Mockito.mock(SpriteBatch.class);
        SelectBox box = Mockito.mock(SelectBox.class);
        ShapeRenderer shape = Mockito.mock(ShapeRenderer.class);
        TextButton button = Mockito.mock(TextButton.class);
        Label label = Mockito.mock(Label.class);
        CheckBox check = Mockito.mock(CheckBox.class);
        Slider slider = Mockito.mock(Slider.class);
        Sound sound = Mockito.mock(Sound.class);
        Texture texture = Mockito.mock(Texture.class);
        game.batch = batch;
        GameSettings.game = game;

        Mockito.when(game.getBatch())
                .thenReturn(batch);
        Mockito.when(game.getSound(Mockito.anyString()))
                .thenReturn(sound);
        Mockito.when(game.getStage())
                .thenReturn(stage);
        Mockito.when(game.getSelectBox())
                .thenReturn(box);
        Mockito.when(game.getTextButton(Mockito.anyString()))
                .thenReturn(button);
        Mockito.when(game.getLabel(Mockito.anyString()))
                .thenReturn(label);
        Mockito.when(game.getCheckBox(Mockito.anyString()))
                .thenReturn(check);
        Mockito.when(game.getSlider())
                .thenReturn(slider);
        Mockito.when(game.getTextureByName(Mockito.anyString()))
                .thenReturn(texture);

        assertEquals(sound, game.getSound(Mockito.anyString()));
        assertEquals(stage, game.getStage());
        assertEquals(batch, game.getBatch());
        assertEquals(box, game.getSelectBox());
        assertEquals(button, game.getTextButton(Mockito.anyString()));
        assertEquals(label, game.getLabel(Mockito.anyString()));
        assertEquals(check, game.getCheckBox(Mockito.anyString()));
        assertEquals(slider, game.getSlider());
        assertEquals(texture, game.getTextureByName(Mockito.anyString()));

        ConfigScreen config = new ConfigScreen(game);
        MainMenuScreen menu = new MainMenuScreen(game);
        SetScreen set = new SetScreen(game);
        GameSettings.setIndexSystemVariables(0, 3);
        MainPlayScreen play = new MainPlayScreen(game);
        play.numDelivered = GameSettings.getSystemVariableByName("goods");
        Process test = new Process();
        for (int i = 0; i < 3; i++) {
            test.setCurrentScreen(i);
            Assertions.assertEquals(test.getCurrentScreen(), i);
        }
        for (int i = 0; i < 5; i++) {
            test.GetScreenByIndex(i);
        }
        for (int i = 1; i < 160; i++) {
            Mockito.when(Gdx.input.isKeyPressed(i))
                    .thenReturn(true);
        }
        play.camera.position.set(100f,100f, 100f);
        play.render(0.001f);
        config.render(1f);
        menu.render(10f);
        set.render(10f);
        for (int i = 1; i < 160; i++) {
            Mockito.when(Gdx.input.isKeyPressed(i))
                    .thenReturn(false);
        }
        play.render(0.001f);
        play.show();
        config.render(1f);
        config.show();
        menu.render(10f);
        menu.show();
        set.render(10f);
        set.show();



        //Input keyboard test
        boolean ans = true;
        int key = 45;
        for (int i = 0; i < 2; i++, key -= 12) {
            Mockito.when(Gdx.input.isKeyPressed(Input.Keys.SHIFT_LEFT))
                    .thenReturn(ans);
            Mockito.when(Gdx.input.isKeyPressed(Input.Keys.W))
                    .thenReturn(true);
            play.render(0.001f);
            play.render(0.001f);
            play.render(0.001f);
            play.render(0.001f);
            play.render(0.001f);
            Mockito.when(Gdx.input.isKeyPressed(Input.Keys.W))
                    .thenReturn(false);
            for (int j = 0; j < 20; j++) {
                Mockito.when(Gdx.input.isKeyPressed(key))
                        .thenReturn(true);
                play.render(0.001f);
            }
            play.render(0.001f);

            Mockito.when(Gdx.input.isKeyPressed(key - 12))
                    .thenReturn(false);
            Mockito.when(Gdx.input.isKeyPressed(Input.Keys.W))
                    .thenReturn(true);
            play.render(0.001f);
            play.render(0.001f);
            play.render(0.001f);
            Mockito.when(Gdx.input.isKeyPressed(Input.Keys.W))
                    .thenReturn(false);
            Mockito.when(Gdx.input.isKeyPressed(Input.Keys.S))
                    .thenReturn(true);
            play.render(0.001f);
            play.render(0.001f);
            play.render(0.001f);
            Mockito.when(Gdx.input.isKeyPressed(Input.Keys.S))
                    .thenReturn(false);
            Mockito.when(Gdx.input.isKeyPressed(Input.Keys.A))
                    .thenReturn(true);
            play.render(0.001f);
            play.render(0.001f);
            play.render(0.001f);
            Mockito.when(Gdx.input.isKeyPressed(Input.Keys.A))
                    .thenReturn(false);
            Mockito.when(Gdx.input.isKeyPressed(Input.Keys.D))
                    .thenReturn(true);
            play.render(0.001f);
            play.render(0.001f);
            play.render(0.001f);
            Mockito.when(Gdx.input.isKeyPressed(Input.Keys.D))
                    .thenReturn(false);
            for (int j = 0; j < 20; j++) {
                Mockito.when(Gdx.input.isKeyPressed(Input.Keys.A))
                        .thenReturn(true);
                play.render(0.001f);
            }
            Mockito.when(Gdx.input.isKeyPressed(Input.Keys.A))
                    .thenReturn(false);
            for (int j = 0; j < 20; j++) {
                Mockito.when(Gdx.input.isKeyPressed(Input.Keys.W))
                        .thenReturn(true);
                play.render(0.001f);
            }
            Mockito.when(Gdx.input.isKeyPressed(Input.Keys.W))
                    .thenReturn(false);
            ans = false;
        }
        ///
        play.render(0.001f);
        GameSettings.setIndexSystemVariables(0, 3);
        assertEquals(7, (int) GameSettings.getSystemVariableByName("planets"));
        GameSettings.setIndexSystemVariables(1, 2);
        assertEquals(3, (int) GameSettings.getSystemVariableByName("goods"));
        GameSettings.setIndexSystemVariables(2, 3);
        assertEquals(7, (int) GameSettings.getSystemVariableByName("asteroids"));
        GameSettings.setIndexSystemVariables(3, 3);
        assertEquals(4, (int) GameSettings.getSystemVariableByName("star"));

        play.gameState = GameState.TARGET_FIRST;
        play.render(0.001f);
        for (int j = 0; j < GameSettings.getSystemVariableByName("goods"); j++) {
            for (int i = 0; i < GameSettings.getSystemVariableByName("planets"); i++) {
                play.sim.ship.position.set(play.sim.planets.get(i).position);
                play.render(0.001f);
                play.render(0.001f);
            }
            for (int i = 0; i < GameSettings.getSystemVariableByName("planets"); i++) {
                play.sim.ship.position.set(play.sim.planets.get(i).position);
                play.render(0.001f);
                play.render(0.001f);
            }
        }
        assertSame(GameState.FADING, play.gameState);
        for (int i = 1; i < 5; i++) {
            play.render(0.001f);
            play.render(0.001f);
        }
        //Nul position
        play.sim.ship.position.set(play.sim.sun.position.x, play.sim.sun.position.y);
        for (int i = 1; i < 5; i++) {
            play.render(0.001f);
            play.render(0.001f);
            play.render(0.001f);
        }
        assertSame(GameState.FADING, play.gameState);
        play.sim.ship.position.set(play.sim.asteroids.get(0).position);
        play.render(0.01f);
        play.render(0.01f);
        play.numDelivered = 0;
        play.render(0.01f);
        play.render(0.01f);


        play.hide();
        play.dispose();
        GameSound sounds = new GameSound();
        sounds.done();
        sounds.turnStop();
        sounds.turnStart();
        sounds.engineStart();
        sounds.engineStop();
        sounds.ambienceStart();
        sounds.ambienceStop();
        sounds.dispose();

      //  Process test = new Process();
        test.getSound("explosion.mp3");
        test.getSound("space_ambience.wav");
        test.getSound("transformer-1.mp3");
        test.getSound("space_engine.wav");
        for (int i = 0; i < 3; i++) {
            test.setCurrentScreen(i);
            Assertions.assertEquals(test.getCurrentScreen(), i);
        }
        for (int i = 0; i < 5; i++) {
            test.GetScreenByIndex(i);
        }
        Mockito.when(Gdx.input.isKeyPressed(Input.Keys.ESCAPE))
                .thenReturn(true);
        Mockito.when(Gdx.input.isKeyPressed(Input.Keys.SPACE))
                .thenReturn(true);
        Mockito.when(Gdx.input.isKeyPressed(Input.Keys.ENTER))
                .thenReturn(true);
        ResultScreen result = new ResultScreen(game);
        result.show();
        result.render(1f);
        config.render(1f);
        Mockito.when(Gdx.input.isKeyPressed(Input.Keys.ENTER))
                .thenReturn(false);
        config.render(1f);
        Mockito.when(Gdx.input.isKeyPressed(Input.Keys.SPACE))
                .thenReturn(false);
        config.render(1f);
        Mockito.when(Gdx.input.isKeyPressed(Input.Keys.ESCAPE))
                .thenReturn(false);
        config.render(1f);



        menu.hide();
        config.hide();
        set.hide();
        play.hide();
        result.hide();
        menu.dispose();
        config.dispose();
        set.dispose();
        play.dispose();
        result.dispose();
    }

}