package com.iuw.game.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.iuw.game.Process;

public class DesktopLauncher {
	final private static String title = new String("Star Transporter");
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.title = title;
		config.width = 600;
		config.height = 800;
		config.resizable = false;
		new LwjglApplication(new Process(), config);
	}

}
