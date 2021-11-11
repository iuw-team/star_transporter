package com.iuw.game;

import com.badlogic.gdx.*;
import com.badlogic.gdx.backends.headless.HeadlessApplication;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3Graphics;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.GL30;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.utils.BufferUtils;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.mockito.Mockito;
import org.powermock.api.mockito.PowerMockito;
import sun.misc.Unsafe;
import java.nio.IntBuffer;
import java.nio.Buffer;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.lang.reflect.UndeclaredThrowableException;

import static com.badlogic.gdx.Gdx.gl;
import static org.mockito.Matchers.any;

/**
 * This class is the base class for all game tests. It takes care of starting
 * the game headlessly and mocking {@linkplain Gdx#gl20 open gl}.
 */
public abstract class LibgdxUnitTest {

    protected static Application application;

    @BeforeAll
    public static void init() throws IOException {
        application = new HeadlessApplication(new ApplicationAdapter() {
        });
        Gdx.graphics = Mockito.mock(Graphics.class);
        Gdx.gl20 = Mockito.spy(GL20.class);
        gl = Gdx.gl20;

        Gdx.files = Mockito.mock(Files.class);
        FileHandle file = Mockito.mock(FileHandle.class);
        Reader reader = Mockito.mock(Reader.class);
        Mockito.when(Gdx.files.internal(Mockito.anyString()))
                        .thenReturn(file);
        Mockito.when(file.sibling(Mockito.anyString()))
                .thenReturn(file);
        Mockito.when(file.name())
                .thenReturn("pixel_planet.png");
        Mockito.when(file.readBytes())
                .thenReturn(new byte[]{1, 3, 5});
        Mockito.when(file.reader(Mockito.anyString()))
                .thenReturn(reader);
        char[] data = new char[1024];
        Mockito.when(reader.read(data, 0, data.length))
                .thenReturn(-1);
        IntBuffer params = Mockito.mock(IntBuffer.class);
        Mockito.when(params.get(0))
                .thenReturn(1);
        Mockito.when(Gdx.gl20.glCreateShader(GL20.GL_VERTEX_SHADER))
                .thenReturn(1);
        Mockito.when(Gdx.gl20.glCreateShader(GL20.GL_FRAGMENT_SHADER))
                .thenReturn(1);
        Mockito.doNothing()
                .when(Gdx.gl20).glGetShaderiv(1, GL20.GL_COMPILE_STATUS, params);
    }

    @AfterAll
    public static void cleanUp() {
        application.exit();
        application = null;
    }

}