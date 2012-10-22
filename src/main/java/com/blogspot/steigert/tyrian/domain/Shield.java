package com.blogspot.steigert.tyrian.domain;

import com.blogspot.steigert.tyrian.utils.TextUtils;
import java.util.Locale;

/**
 * A shield for the ship.
 */
public enum Shield
  implements Item
{
  SIF( "Structural Field", 100 ),
  AIF( "Advanced Field", 250 ),
  GLES( "Gencore LE Shield", 500 ),
  GHEF( "Gencore HE Shield", 1000 ),
  MLXS( "MicroCorp LXS-A", 2000 );

  private final String name;
  private final int price;

  private Shield( final String name, final int price )
  {
    this.name = name;
    this.price = price;
  }

  public String getSimpleName()
  {
    return "shield-" + name().replaceAll( "_", "-" ).toLowerCase();
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
