package com.blogspot.steigert.tyrian.services;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;

/**
 * Handles the game preferences.
 */
public class PreferencesManager
{
  // constants
  private static final String PREF_VOLUME = "volume";
  private static final String PREF_MUSIC_ENABLED = "music.enabled";
  private static final String PREF_SOUND_ENABLED = "sound.enabled";
  private static final String PREFS_NAME = "tyrian";

  public PreferencesManager()
  {
  }

  Preferences getPrefs()
  {
    return Gdx.app.getPreferences( PREFS_NAME );
  }

  public boolean isSoundEnabled()
  {
    return getPrefs().getBoolean( PREF_SOUND_ENABLED, true );
  }

  public void setSoundEnabled( final boolean soundEffectsEnabled )
  {
    getPrefs().putBoolean( PREF_SOUND_ENABLED, soundEffectsEnabled );
    getPrefs().flush();
  }

  public boolean isMusicEnabled()
  {
    return getPrefs().getBoolean( PREF_MUSIC_ENABLED, true );
  }

  public void setMusicEnabled( final boolean musicEnabled )
  {
    getPrefs().putBoolean( PREF_MUSIC_ENABLED, musicEnabled );
    getPrefs().flush();
  }

  public float getVolume()
  {
    return getPrefs().getFloat( PREF_VOLUME, 0.5f );
  }

  public void setVolume( final float volume )
  {
    getPrefs().putFloat( PREF_VOLUME, volume );
    getPrefs().flush();
  }
}
