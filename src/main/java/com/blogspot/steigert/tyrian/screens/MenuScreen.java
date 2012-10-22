package com.blogspot.steigert.tyrian.screens;

import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.blogspot.steigert.tyrian.Tyrian;
import com.blogspot.steigert.tyrian.services.SoundManager.TyrianSound;

public class MenuScreen
  extends
  AbstractScreen
{
  public MenuScreen( final Tyrian game )
  {
    super( game );
  }

  @Override
  public void show()
  {
    super.show();

    // retrieve the default table actor
    final Table table = super.getTable();
    table.add( "Welcome to Tyrian for Android!" ).spaceBottom( 50 );
    table.row();

    // register the button "start game"
    final TextButton startGameButton = new TextButton( "Start game", getSkin() );
    startGameButton.addListener( new ClickListener()
    {
      @Override
      public void touchUp( final InputEvent event, final float x, final float y, final int pointer, final int button )
      {
        super.touchUp( event, x, y, pointer, button );
        game.getSoundManager().play( TyrianSound.CLICK );
        game.setScreen( new StartGameScreen( game ) );
      }
    } );
    table.add( startGameButton ).size( 300, 60 ).uniform().spaceBottom( 10 );
    table.row();

    // register the button "options"
    final TextButton optionsButton = new TextButton( "Options", getSkin() );
    optionsButton.addListener( new ClickListener()
    {
      @Override
      public void touchUp( final InputEvent event, final float x, final float y, final int pointer, final int button )
      {
        super.touchUp( event, x, y, pointer, button );
        game.getSoundManager().play( TyrianSound.CLICK );
        game.setScreen( new OptionsScreen( game ) );
      }
    } );
    table.add( optionsButton ).uniform().fill().spaceBottom( 10 );
    table.row();

    // register the button "high scores"
    final TextButton highScoresButton = new TextButton( "High Scores", getSkin() );
    highScoresButton.addListener( new ClickListener()
    {
      @Override
      public void touchUp( final InputEvent event, final float x, final float y, final int pointer, final int button )
      {
        super.touchUp( event, x, y, pointer, button );
        game.getSoundManager().play( TyrianSound.CLICK );
        game.setScreen( new HighScoresScreen( game ) );
      }
    } );
    table.add( highScoresButton ).uniform().fill();
  }
}
