package com.iuw.game.desktop;

import com.badlogic.gdx.Files;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3Application;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3ApplicationConfiguration;
import com.iuw.game.Process;

public class DesktopLauncher {
	final private static String title = "Star Transporter";
	public static void main (String[] arg) {
		Lwjgl3ApplicationConfiguration config = new Lwjgl3ApplicationConfiguration();
		config.setTitle(title);
		config.setMaximized(true);
		config.setWindowIcon(Files.FileType.Internal, "icon128.png");
		config.setWindowIcon(Files.FileType.Internal, "icon32.png");
		config.setWindowIcon(Files.FileType.Internal, "icon16.png");
		config.setResizable(true);
		new Lwjgl3Application(new Process(), config);
	}
}
