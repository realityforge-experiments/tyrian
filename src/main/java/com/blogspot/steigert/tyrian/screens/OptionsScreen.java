package com.blogspot.steigert.tyrian.screens;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.CheckBox;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Slider;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.blogspot.steigert.tyrian.Tyrian;
import com.blogspot.steigert.tyrian.services.MusicManager.TyrianMusic;
import com.blogspot.steigert.tyrian.services.SoundManager.TyrianSound;
import java.util.Locale;

/**
 * A simple options screen.
 */
public class OptionsScreen
  extends AbstractScreen
{
  private Label volumeValue;

  public OptionsScreen( final Tyrian game )
  {
    super( game );
  }

  @Override
  public void show()
  {
    super.show();

    // retrieve the default table actor
    final Table table = super.getTable();
    table.defaults().spaceBottom( 30 );
    table.columnDefaults( 0 ).padRight( 20 );
    table.add( "Options" ).colspan( 3 );

    // create the labels widgets
    final CheckBox soundEffectsCheckbox = new CheckBox( "", getSkin() );
    soundEffectsCheckbox.setChecked( game.getPreferencesManager().isSoundEnabled() );
    soundEffectsCheckbox.addListener( new ChangeListener()
    {
      @Override
      public void changed(
        final ChangeEvent event,
        final Actor actor )
      {
        final boolean enabled = soundEffectsCheckbox.isChecked();
        game.getPreferencesManager().setSoundEnabled( enabled );
        game.getSoundManager().setEnabled( enabled );
        game.getSoundManager().play( TyrianSound.CLICK );
      }
    } );
    table.row();
    table.add( "Sound Effects" );
    table.add( soundEffectsCheckbox ).colspan( 2 ).left();

    final CheckBox musicCheckbox = new CheckBox( "", getSkin() );
    musicCheckbox.setChecked( game.getPreferencesManager().isMusicEnabled() );
    musicCheckbox.addListener( new ChangeListener()
    {
      @Override
      public void changed(
        final ChangeEvent event,
        final Actor actor )
      {
        final boolean enabled = musicCheckbox.isChecked();
        game.getPreferencesManager().setMusicEnabled( enabled );
        game.getMusicManager().setEnabled( enabled );
        game.getSoundManager().play( TyrianSound.CLICK );

        // if the music is now enabled, start playing the menu music
        if( enabled ) game.getMusicManager().play( TyrianMusic.MENU );
      }
    } );
    table.row();
    table.add( "Music" );
    table.add( musicCheckbox ).colspan( 2 ).left();

    // range is [0.0,1.0]; step is 0.1f
    final Slider volumeSlider = new Slider( 0f, 1f, 0.1f, true, getSkin() );
    volumeSlider.setValue( game.getPreferencesManager().getVolume() );
    volumeSlider.addListener( new ChangeListener()
    {
      @Override
      public void changed(
        final ChangeEvent event,
        final Actor actor )
      {
        final float value = ( (Slider) actor ).getValue();
        game.getPreferencesManager().setVolume( value );
        game.getMusicManager().setVolume( value );
        game.getSoundManager().setVolume( value );
        updateVolumeLabel();
      }
    } );

    // create the volume label
    volumeValue = new Label( "", getSkin() );
    updateVolumeLabel();

    // add the volume row
    table.row();
    table.add( "Volume" );
    table.add( volumeSlider );
    table.add( volumeValue ).width( 40 );

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
    table.add( backButton ).size( 250, 60 ).colspan( 3 );
  }

  /**
   * Updates the volume label next to the slider.
   */
  private void updateVolumeLabel()
  {
    final float volume = ( game.getPreferencesManager().getVolume() * 100 );
    volumeValue.setText( String.format( Locale.US, "%1.0f%%", volume ) );
  }
}
