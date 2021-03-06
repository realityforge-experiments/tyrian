package com.blogspot.steigert.tyrian.utils;

import com.badlogic.gdx.math.Vector2;

public class VectorUtils
{
  /**
   * Checks if the vector's X coordinate is inside the range [min,max],
   * adjusting it if needed.
   * <p/>
   * Returns <code>true</code> if the value was adjusted, <code>false</code>
   * otherwise.
   */
  public static boolean adjustByRangeX( final Vector2 vector, final float min, final float max )
  {
    if( vector.x < min )
    {
      vector.x = min;
      return true;
    }
    else if( vector.x > max )
    {
      vector.x = max;
      return true;
    }
    return false;
  }

  /**
   * Checks if the vector's Y coordinate is inside the range [min,max],
   * adjusting it if needed.
   * <p/>
   * Returns <code>true</code> if the value was adjusted, <code>false</code>
   * otherwise.
   */
  public static boolean adjustByRangeY( final Vector2 vector, final float min, final float max )
  {
    if( vector.y < min )
    {
      vector.y = min;
      return true;
    }
    else if( vector.y > max )
    {
      vector.y = max;
      return true;
    }
    return false;
  }

  /**
   * Checks if the vector's coordinates are inside the range [xMin,xMax] and
   * [yMin,yMax], adjusting them if needed.
   * <p/>
   * Returns <code>true</code> if at least one of the values was adjusted,
   * <code>false</code> otherwise.
   */
  private static boolean adjustByRange( final Vector2 vector, final float xMin, final float xMax, final float yMin, final float yMax )
  {
    boolean modified = false;
    if( adjustByRangeX( vector, xMin, xMax ) ) modified = true;
    if( adjustByRangeY( vector, yMin, yMax ) ) modified = true;
    return modified;
  }

  /**
   * Checks if both the vector's coordinates are inside the range [min,max],
   * adjusting them if needed.
   * <p/>
   * Returns <code>true</code> if at least one of the values was adjusted,
   * <code>false</code> otherwise.
   */
  public static boolean adjustByRange( final Vector2 vector, final float min, final float max )
  {
    return adjustByRange( vector, min, max, min, max );
  }

  /**
   * Uses the given value when the vector coordinates are less than or equal
   * to the specified radius value.
   */
  public static boolean adjustDeadzone( final Vector2 vector, final float radius, final float adjustedValue )
  {
    if( vector.len() <= radius )
    {
      vector.x = adjustedValue;
      vector.y = adjustedValue;
      return true;
    }
    return false;
  }

}
