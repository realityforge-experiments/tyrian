package com.blogspot.steigert.tyrian.domain;

import com.blogspot.steigert.tyrian.utils.TextUtils;
import java.util.Locale;

/**
 * The available ship's front-guns.
 */
public enum FrontGun
  implements Item
{
  PULSE_CANNON( "Pulse-Cannon", 500 ),
  MISSILE_LAUNCHER( "Missile Launcher", 1000 ),
  VULCAN_CANNON( "Vulcan Cannon", 2000 ),
  PROTON_LAUNCHER( "Proton Launcher", 3500 ),
  WAVE_CANNON( "Wave-Cannon", 5000 );

  private final String name;
  private final int price;

  private FrontGun( final String name, final int price )
  {
    this.name = name;
    this.price = price;
  }

  public String getSimpleName()
  {
    return "front-gun-" + name().replaceAll( "_", "-" ).toLowerCase();
  }

  public int getPrice()
  {
    return price;
  }

  @Override
  public String toString()
  {
    return String.format( Locale.US, "%s (%s)", name, TextUtils.creditStyle( price ) );
  }
}
