package com.blogspot.steigert.tyrian.screens;

import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.blogspot.steigert.tyrian.Tyrian;
import com.blogspot.steigert.tyrian.domain.Profile;
import com.blogspot.steigert.tyrian.screens.scene2d.Ship2D;
import com.blogspot.steigert.tyrian.services.MusicManager.TyrianMusic;

public class LevelScreen
  extends AbstractScreen
{
  private final Profile profile;

  public LevelScreen( final Tyrian game )
  {
    super( game );

    // set the basic attributes
    profile = game.getProfileManager().retrieveProfile();
  }

  @Override
  protected boolean isGameScreen()
  {
    return true;
  }

  @Override
  public void show()
  {
    super.show();

    // play the level music
    game.getMusicManager().play( TyrianMusic.LEVEL );

    // create the ship and add it to the stage
    final Ship2D ship2d = Ship2D.create( profile.getShip(), getAtlas() );

    // center the ship horizontally
    ship2d.setInitialPosition( ( stage.getWidth() / 2 - ship2d.getWidth() / 2 ), ship2d.getHeight() );

    // add the ship to the stage
    stage.addActor( ship2d );

    // add a fade-in effect to the whole stage
    stage.getRoot().getColor().a = 0f;
    stage.getRoot().addAction( Actions.fadeIn( 0.5f ) );
  }
}
