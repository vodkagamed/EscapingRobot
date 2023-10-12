package com.phoenix;

import com.badlogic.gdx.backends.lwjgl3.Lwjgl3Application;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3ApplicationConfiguration;

public class DesktopLauncher
{
	public static void main (String[] arg)
	{
		Lwjgl3ApplicationConfiguration config = new Lwjgl3ApplicationConfiguration();
		config.setForegroundFPS(144);
		config.useVsync(true);
		config.setResizable(false);
		int width = (int) ((Lwjgl3ApplicationConfiguration.getDisplayMode().width)*(55.0/100.0));  //Ratio of width @Kareem
		int height = (int) ((Lwjgl3ApplicationConfiguration.getDisplayMode().height)*(75.0/100.0)); //Ratio of height @Kareem
		config.setWindowedMode(width,height);
		//config.setFullscreenMode(Lwjgl3ApplicationConfiguration.getDisplayMode());
		config.setTitle("Phoenix");
		config.setWindowIcon("Client/Icon2.png");
		new Lwjgl3Application(new MultipleScreen(), config);
	}
}

