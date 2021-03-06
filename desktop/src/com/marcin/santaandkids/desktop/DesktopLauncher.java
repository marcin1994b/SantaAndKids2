package com.marcin.santaandkids.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.marcin.santaandkids.SantaAndKids;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.title = "Santa and Kids";
		config.useGL30 = false;
		config.width = 800;
		config.height = 600;
		config.resizable = false;
		new LwjglApplication(new SantaAndKids(), config);
	}
}
