package com.blogspot.steigert.tyrian.domain;

import com.blogspot.steigert.tyrian.utils.TextUtils;
import java.util.Locale;

/**
 * The available ship's models.
 */
public enum ShipModel
  implements Item
{
  USP_TALON( "USP Talon", 6000 ),
  GENCORE_PHOENIX( "Gencore Phoenix", 12000 ),
  GENCORE_II( "Gencore II", 17000 ),
  MICROSOL_STALKER( "Microsol Stalker", 20000 ),
  SUPER_CARROT( "Super Carrot", 50000 );

  private final String name;
  private final int price;

  private ShipModel( final String name, final int price )
  {
    this.name = name;
    this.price = price;
  }

  public String getSimpleName()
  {
    return "ship-model-" + name().replaceAll( "_", "-" ).toLowerCase();
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
