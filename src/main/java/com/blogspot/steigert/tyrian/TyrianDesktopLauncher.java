package com.blogspot.steigert.tyrian;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.backends.lwjgl.LwjglApplication;

/**
 * This class simply creates a desktop LWJGL application.
 */
public class TyrianDesktopLauncher
{
  public static void main( final String[] args )
  {
    // create the listener that will receive the application events
    final ApplicationListener listener = new Tyrian();

    // define the window's title
    final String title = "Tyrian";

    // define the window's size
    final int width = 800;
    final int height = 480;

    // whether to use OpenGL ES 2.0
    final boolean useOpenGLES2 = true;

    // create the game
    new LwjglApplication( listener, title, width, height, useOpenGLES2 );
  }
}
