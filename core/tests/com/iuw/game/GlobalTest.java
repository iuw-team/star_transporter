package com.iuw.game;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import static org.junit.jupiter.api.Assertions.assertEquals;


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
    public void UITest() {
        Process game = Mockito.mock(Process.class);
        Stage stage = Mockito.mock(Stage.class);
        SpriteBatch batch = Mockito.mock(SpriteBatch.class);
        SelectBox box = Mockito.mock(SelectBox.class);
        TextButton button = Mockito.mock(TextButton.class);
        Label label = Mockito.mock(Label.class);
        CheckBox check = Mockito.mock(CheckBox.class);
        Slider slider = Mockito.mock(Slider.class);
        Texture texture = Mockito.mock(Texture.class);
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
        MainPlayScreen play = new MainPlayScreen(game);
    }

}