package com.blogspot.steigert.tyrian.screens;

import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.blogspot.steigert.tyrian.Tyrian;
import com.blogspot.steigert.tyrian.domain.Profile;
import com.blogspot.steigert.tyrian.services.SoundManager.TyrianSound;

/**
 * A simple high scores screen.
 */
public class HighScoresScreen
  extends AbstractScreen
{
  public HighScoresScreen( final Tyrian game )
  {
    super( game );
  }

  @Override
  public void show()
  {
    super.show();
    final Profile profile = game.getProfileManager().retrieveProfile();

    // retrieve the default table actor
    final Table table = super.getTable();
    table.defaults().spaceBottom( 30 );
    table.add( "High scores" ).colspan( 2 );

    // episode 1 high-score
    final String level1Highscore = String.valueOf( profile.getHighScore( 0 ) );
    final Label episode1HighScore = new Label( level1Highscore, getSkin() );
    table.row();
    table.add( "Episode 1" );
    table.add( episode1HighScore );

    final String level2Highscore = String.valueOf( profile.getHighScore( 1 ) );
    final Label episode2HighScore = new Label( level2Highscore, getSkin() );
    table.row();
    table.add( "Episode 2" ).center();
    table.add( episode2HighScore );

    final String level3Highscore = String.valueOf( profile.getHighScore( 2 ) );
    final Label episode3HighScore = new Label( level3Highscore, getSkin() );
    table.row();
    table.add( "Episode 3" );
    table.add( episode3HighScore );

    // register the back button
    final TextButton backButton = new TextButton( "Back to main menu", getSkin() );
    backButton.addListener( new ClickListener()
    {
      @Override
      public void touchUp( final InputEvent event, final float x, final float y, final int pointer, final int button )
      {
        super.touchUp( event, x, y, pointer, button );
        game.getSoundManager().play( TyrianSound.CLICK );
        game.setScreen( new MenuScreen( game ) );
      }
    } );
    table.row();
    table.add( backButton ).size( 250, 60 ).colspan( 2 );
  }
}
