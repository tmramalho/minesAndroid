package com.nehalemlabs.mines;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.tools.imagepacker.TexturePacker2;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;

public class Main {
	public static void main(String[] args) {
		LwjglApplicationConfiguration cfg = new LwjglApplicationConfiguration();
		cfg.title = "mines";
		cfg.useGL20 = false;
		cfg.width = 800;
		cfg.height = 480;
		TexturePacker2.process("../images", "../mines-android/assets", "tiles");
		
		new LwjglApplication(new MinesMain(), cfg);
	}
}
