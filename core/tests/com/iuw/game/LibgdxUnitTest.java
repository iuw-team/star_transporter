package com.iuw.game;

import com.badlogic.gdx.*;
import com.badlogic.gdx.backends.headless.HeadlessApplication;
import com.badlogic.gdx.backends.headless.HeadlessFileHandle;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3Graphics;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.GL20;

import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.TextureData;
import com.badlogic.gdx.utils.StreamUtils;
import com.sun.source.tree.ModuleTree;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.powermock.api.mockito.PowerMockito;
import sun.misc.Unsafe;

import java.io.*;
import java.nio.IntBuffer;
import java.nio.Buffer;
import java.lang.reflect.UndeclaredThrowableException;

import static com.badlogic.gdx.Gdx.gl;
import static com.badlogic.gdx.Gdx.input;
import static org.mockito.Matchers.any;

/**
 * This class is the base class for all game tests. It takes care of starting
 * the game headlessly and mocking {@linkplain Gdx#gl20 open gl}.
 */
public abstract class LibgdxUnitTest {

    protected static Application application;
    protected static Texture texture;
    protected static TextureData textureData;

    @BeforeAll
    public static void init() throws IOException {
        application = new HeadlessApplication(new ApplicationAdapter() {
        });
        Gdx.graphics = Mockito.mock(Graphics.class);
        Gdx.input = Mockito.mock(Input.class);
        Gdx.gl20 = Mockito.spy(GL20.class);
        gl = Gdx.gl20;
//        byte[] bytes = Mockito.mock(byte[].class);
        Gdx.files = Mockito.mock(Files.class);
        textureData = Mockito.mock(TextureData.class);
        FileHandle file = Mockito.mock(FileHandle.class);
        Pixmap pixmap = Mockito.mock(Pixmap.class);
        Reader reader = Mockito.mock(Reader.class);
//        Mockito.when(file.readBytes())
//                .thenReturn(bytes);
        Mockito.when(textureData.consumePixmap())
                        .thenReturn(pixmap);
        Mockito.when(Gdx.files.internal(Mockito.anyString()))
                        .thenReturn(file);
        Mockito.when(file.sibling(Mockito.anyString()))
              .thenReturn(file);
        Mockito.when(file.name())
                .thenReturn("pixel_planet.png");
      //  InputStream input = Mockito.mock(InputStream.class);
       // Mockito.when(file.read())
          //      .thenReturn(input);
        byte[] bytes =  new byte[512];//StreamUtils.copyStreamToByteArray(file.read(), 512);
        Mockito.when(file.readBytes())
                .thenReturn(bytes);
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
        texture = new Texture(textureData);
    }

    @AfterAll
    public static void cleanUp() {
        application.exit();
        application = null;
    }

}