package com.iuw.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;

import static org.junit.jupiter.api.Assertions.*;


public class GlobalTest extends LibgdxUnitTest {

    @Test
    public void ApplicationSetupTest() {
        if (Process.SCREEN_HEIGHT != 0 && Process.SCREEN_WIDTH != 0 && Process.BUTTON_WIDTH != 0 &&
                Process.BUTTON_HEIGHT != 0 && Process.BOX_WIDTH != 0 && Process.BOX_HEIGHT != 0 &&
                Process.SMALL_BUTTON_WIDTH != 0 && Process.SMALL_BUTTON_HEIGHT != 0 &&
                Process.SLIDER_WIDTH != 0 && Process.SLIDER_HEIGHT != 0 &&
                Process.ChosenSkin != null && GameSettings.VOLUME_LEVELS[0] != 0 && GameSettings.VOLUME_LEVELS[1] != 0)
            System.out.println("Data is good");
        else System.out.println("Some data are lost");
    }
@Test
public void ProcessTest(){
    Texture texture = new Texture(super.textureData);

//   Assertions.assertAll(()-> Assertions.assertNull( game.getTextureByName("ship")
//    ));
}

    @Test
    public void UITest() {
        OrthographicCamera camera = new OrthographicCamera();
        Process game = Mockito.mock(Process.class);
        Stage stage = Mockito.mock(Stage.class);
        SpriteBatch batch = Mockito.mock(SpriteBatch.class);
        SelectBox box = Mockito.mock(SelectBox.class);
        ShapeRenderer shape = Mockito.mock(ShapeRenderer.class);
        TextButton button = Mockito.mock(TextButton.class);
        Label label = Mockito.mock(Label.class);
        CheckBox check = Mockito.mock(CheckBox.class);
        Slider slider = Mockito.mock(Slider.class);
        Texture texture = Mockito.mock(Texture.class);
        Sound sound = Mockito.mock(Sound.class);
        Music music = Mockito.mock(Music.class);
        game.batch = batch;
        Mockito.when(game.getBatch())
                .thenReturn(batch);
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
        Mockito.when(game.getShapeRenderer())
                .thenReturn(shape);
        Mockito.when(game.getSoundByName(Mockito.anyString()))
                .thenReturn(sound);
        Mockito.when(game.getMusic())
                .thenReturn(music);

        assertEquals(music, game.getMusic());
        assertEquals(sound, game.getSoundByName(Mockito.anyString()));
        assertEquals(shape, game.getShapeRenderer());
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
        GameSettings.setSystemVariables(0, 8);
        MainPlayScreen play = new MainPlayScreen(game);
        play.numDelivered = GameSettings.getSystemVariableByName("goods");

        for(int i = 1; i < 160; i++){
            Mockito.when(Gdx.input.isKeyPressed(i))
                    .thenReturn(true);
        }
        play.render(0.001f);
        config.render(1f);
        menu.render(10f);
        set.render(10f);
        for(int i = 1; i < 160; i++){
            Mockito.when(Gdx.input.isKeyPressed(i))
                    .thenReturn(false);
        }
        play.render(0.001f);
        config.render(1f);
        menu.render(10f);
        set.render(10f);



        //Input keyboard test
        boolean ans = true;
        int key = 45;
        for(int i = 0; i < 2; i++, key -= 12) {
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
            for(int j = 0; j <20; j++){
                Mockito.when(Gdx.input.isKeyPressed(key))
                        .thenReturn(true);
                play.render(0.001f);
            }
            Mockito.when(Gdx.input.isKeyPressed(key-12))
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
            for(int j = 0; j <20; j++){
                Mockito.when(Gdx.input.isKeyPressed(Input.Keys.A))
                        .thenReturn(true);
                play.render(0.001f);
            }
            Mockito.when(Gdx.input.isKeyPressed(Input.Keys.A))
                    .thenReturn(false);
            for(int j = 0; j <20; j++){
                Mockito.when(Gdx.input.isKeyPressed(Input.Keys.W))
                        .thenReturn(true);
                play.render(0.001f);
            }
            Mockito.when(Gdx.input.isKeyPressed(Input.Keys.W))
                    .thenReturn(false);
            ans = false;
        }
        ///
       play.sim.ship.isCollide = true;
        play.render(0.001f);
        GameSettings.setSystemVariables(0, 8);
        assertEquals(8, (int) GameSettings.getSystemVariableByName("planets"));
        GameSettings.setSystemVariables(1, 4);
        assertEquals(4, (int) GameSettings.getSystemVariableByName("goods"));
        GameSettings.setSystemVariables(2, 3);
        assertEquals(3, (int) GameSettings.getSystemVariableByName("velocity"));
        GameSettings.setSystemVariables(3, 10);
        assertEquals(10, (int) GameSettings.getSystemVariableByName("star"));
        play.sim.ship.isCollide = false;

        play.gameState = GameState.TARGET_FIRST;
        play.render(0.001f);
        for(int j = 1; j < GameSettings.getSystemVariableByName("goods"); j++){
        for(int i = 0; i<GameSettings.getSystemVariableByName("planets"); i++){
            play.sim.ship.position.set(play.sim.planets.get(i).position);
            play.render(0.001f);
        }
        for(int i = 0; i<GameSettings.getSystemVariableByName("planets"); i++){
            play.sim.ship.position.set(play.sim.planets.get(i).position);
            play.render(0.001f);
        }
        }
        assertSame(GameState.DONE, play.gameState);



        Process test = new Process();
        GameSettings.game = game;
        for(int i = 1; i < 100; i++) {
            test.getSoundByName("ship");
        }
        test.getSoundByName("collision");
        for(int i = 0; i< 3; i++) {
            test.setCurrentScreen(i);
            Assertions.assertEquals(test.getCurrentScreen(), i);
        }
        for(int i = 0; i < 5; i++) {
            test.GetScreenByIndex(i);
        }
        Mockito.when(Gdx.input.isKeyPressed(Input.Keys.ESCAPE))
                .thenReturn(true);

    }

}