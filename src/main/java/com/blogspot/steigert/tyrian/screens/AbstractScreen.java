package com.blogspot.steigert.tyrian.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.blogspot.steigert.tyrian.Tyrian;

/**
 * The base class for all game screens.
 */
abstract class AbstractScreen
  implements Screen
{
  // the fixed viewport dimensions (ratio: 1.6)
  private static final int GAME_VIEWPORT_WIDTH = 400;
  private static final int GAME_VIEWPORT_HEIGHT = 240;
  private static final int MENU_VIEWPORT_WIDTH = 800;
  private static final int MENU_VIEWPORT_HEIGHT = 480;

  final Tyrian game;
  final Stage stage;

  private BitmapFont font;
  private SpriteBatch batch;
  private Skin skin;
  private TextureAtlas atlas;
  private Table table;

  AbstractScreen( final Tyrian game )
  {
    this.game = game;
    final int width = ( isGameScreen() ? GAME_VIEWPORT_WIDTH : MENU_VIEWPORT_WIDTH );
    final int height = ( isGameScreen() ? GAME_VIEWPORT_HEIGHT : MENU_VIEWPORT_HEIGHT );
    this.stage = new Stage( width, height, true );
  }

  String getName()
  {
    return getClass().getSimpleName();
  }

  boolean isGameScreen()
  {
    return false;
  }

  // Lazily loaded collaborators

  public BitmapFont getFont()
  {
    if( font == null )
    {
      font = new BitmapFont();
    }
    return font;
  }

  public SpriteBatch getBatch()
  {
    if( batch == null )
    {
      batch = new SpriteBatch();
    }
    return batch;
  }

  TextureAtlas getAtlas()
  {
    if( atlas == null )
    {
      atlas = new TextureAtlas( Gdx.files.internal( "image-atlases/pages.atlas" ) );
    }
    return atlas;
  }

  Skin getSkin()
  {
    if( skin == null )
    {
      final FileHandle skinFile = Gdx.files.internal( "skin/uiskin.json" );
      skin = new Skin( skinFile );
    }
    return skin;
  }

  Table getTable()
  {
    if( table == null )
    {
      table = new Table( getSkin() );
      table.setFillParent( true );
      if( Tyrian.DEV_MODE )
      {
        table.debug();
      }
      stage.addActor( table );
    }
    return table;
  }

  // Screen implementation

  @Override
  public void show()
  {
    Gdx.app.log( Tyrian.LOG, "Showing screen: " + getName() );

    // set the stage as the input processor
    Gdx.input.setInputProcessor( stage );
  }

  @Override
  public void resize( final int width, final int height )
  {
    Gdx.app.log( Tyrian.LOG, "Resizing screen: " + getName() + " to: " + width + " x " + height );
  }

  @Override
  public void render( final float delta )
  {
    // (1) process the game logic

    // update the actors
    stage.act( delta );

    // (2) draw the result

    // clear the screen with the given RGB color (black)
    Gdx.gl.glClearColor( 0f, 0f, 0f, 1f );
    Gdx.gl.glClear( GL20.GL_COLOR_BUFFER_BIT );

    // draw the actors
    stage.draw();

    // draw the table debug lines
    Table.drawDebug( stage );
  }

  @Override
  public void hide()
  {
    Gdx.app.log( Tyrian.LOG, "Hiding screen: " + getName() );

    // dispose the screen when leaving the screen;
    // note that the dipose() method is not called automatically by the
    // framework, so we must figure out when it's appropriate to call it
    dispose();
  }

  @Override
  public void pause()
  {
    Gdx.app.log( Tyrian.LOG, "Pausing screen: " + getName() );
  }

  @Override
  public void resume()
  {
    Gdx.app.log( Tyrian.LOG, "Resuming screen: " + getName() );
  }

  @Override
  public void dispose()
  {
    Gdx.app.log( Tyrian.LOG, "Disposing screen: " + getName() );

    // the following call disposes the screen's stage, but on my computer it
    // crashes the game so I commented it out; more info can be found at:
    // http://www.badlogicgames.com/forum/viewtopic.php?f=11&t=3624
    // stage.dispose();

    // as the collaborators are lazily loaded, they may be null
    if( font != null ) font.dispose();
    if( batch != null ) batch.dispose();
    if( skin != null ) skin.dispose();
    if( atlas != null ) atlas.dispose();
  }
}
