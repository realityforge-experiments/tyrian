package com.blogspot.steigert.tyrian.screens.scene2d;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Input.Peripheral;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureAtlas.AtlasRegion;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Array;
import com.blogspot.steigert.tyrian.domain.Ship;
import com.blogspot.steigert.tyrian.utils.VectorUtils;
import java.util.HashMap;
import java.util.Map;

/**
 * The ship's 2D representation.
 */
public class Ship2D
  extends Image
{
  /**
   * The ship's maximum speed; given in pixels per second.
   */
  private static final float MAX_SPEED = 240;

  /**
   * The ship's maximum acceleration; given in pixels per second².
   */
  private static final float MAX_ACCELERATION = 10;

  /**
   * The ship's maximum deceleration; given in pixels per second².
   * <p/>
   * This is the force that makes the ship to stop flying.
   */
  private static final float MAX_DECELERATION = MAX_ACCELERATION / 2;

  /**
   * The ship's position.
   */
  private final Vector2 position;

  /**
   * The ship's velocity.
   */
  private final Vector2 velocity;

  /**
   * The ship's acceleration.
   */
  private final Vector2 acceleration;

  /**
   * The ship's tilt animation.
   */
  private final Animation tiltAnimation;

  /**
   * The ship's tilt animation's state time.
   * <p/>
   * With this info we know what frame to show.
   */
  private float tiltAnimationStateTime;

  /**
   * The drawables of the tilt animation.
   * <p/>
   * Here we cache the drawables or we would have to create them on demand
   * (hence waking up the garbage collector).
   */
  private final Map<TextureRegion, Drawable> tiltAnimationDrawables;

  /**
   * Creates a new {@link Ship2D}.
   */
  private Ship2D( final Array<AtlasRegion> tiltAnimationFrames )
  {
    // the super constructor does a lot of work
    super( tiltAnimationFrames.get( 0 ) );
    super.setTouchable( Touchable.disabled );

    // set some basic attributes
    this.position = new Vector2();
    this.velocity = new Vector2();
    this.acceleration = new Vector2();

    // create the tilt animation (each frame will be shown for 0.15
    // seconds when the animation is active)
    this.tiltAnimation = new Animation( 0.15f, tiltAnimationFrames );

    // create the tilt animation drawable cache
    this.tiltAnimationDrawables = new HashMap<TextureRegion, Drawable>();
    for( final AtlasRegion region : tiltAnimationFrames )
    {
      tiltAnimationDrawables.put( region, new TextureRegionDrawable( region ) );
    }
  }

  /**
   * Factory method to create a {@link Ship2D}.
   */
  public static Ship2D create( final Ship ship, final TextureAtlas textureAtlas )
  {
    // load all the regions of our ship in the image atlas
    final Array<AtlasRegion> regions = textureAtlas.findRegions( "level-screen/"
                                                           + ship.getShipModel().getSimpleName() );

    // create the ship
    return new Ship2D( regions );
  }

  /**
   * Sets the ship's initial position.
   */
  public void setInitialPosition( final float x, final float y )
  {
    position.set( x, y );
  }

  @Override
  public void act( final float delta )
  {
    super.act( delta );
    moveShip( delta );
    tiltShip( delta );
  }

  /**
   * Moves the ship around the screen.
   */
  private void moveShip( final float delta )
  {
    // check the input and calculate the acceleration
    if( Gdx.input.isPeripheralAvailable( Peripheral.Accelerometer ) )
    {

      // set the acceleration base on the accelerometer input; notice the
      // inverted axis because the game is displayed in landscape mode
      acceleration.set( Gdx.input.getAccelerometerY(), Gdx.input.getAccelerometerX() );

      // set the acceleration bounds
      VectorUtils.adjustByRange( acceleration, -2, 2 );

      // set the input deadzone
      if( !VectorUtils.adjustDeadzone( acceleration, 1f, 0f ) )
      {
        // we're out of the deadzone, so let's adjust the acceleration
        // (2 is 100% of the max acceleration)
        acceleration.x = ( acceleration.x / 2 * MAX_ACCELERATION );
        acceleration.y = ( -acceleration.y / 2 * MAX_ACCELERATION );
      }
    }
    else
    {
      // when the keys aren't pressed the acceleration will be zero, so
      // the ship's velocity won't be affected by it
      acceleration.x = ( Gdx.input.isKeyPressed( Input.Keys.LEFT ) ? -MAX_ACCELERATION
        : ( Gdx.input.isKeyPressed( Input.Keys.RIGHT ) ? MAX_ACCELERATION : 0 ) );
      acceleration.y = ( Gdx.input.isKeyPressed( Input.Keys.UP ) ? MAX_ACCELERATION
        : ( Gdx.input.isKeyPressed( Input.Keys.DOWN ) ? -MAX_ACCELERATION : 0 ) );
    }

    // if there is no acceleration and the ship is moving, let's calculate
    // an appropriate deceleration
    if( acceleration.len() == 0f && velocity.len() > 0f )
    {

      // horizontal deceleration
      if( velocity.x > 0 )
      {
        acceleration.x = -MAX_DECELERATION;
        if( velocity.x - acceleration.x < 0 )
        {
          acceleration.x = -( acceleration.x - velocity.x );
        }
      }
      else if( velocity.x < 0 )
      {
        acceleration.x = MAX_DECELERATION;
        if( velocity.x + acceleration.x > 0 )
        {
          acceleration.x = ( acceleration.x - velocity.x );
        }
      }

      // vertical deceleration
      if( velocity.y > 0 )
      {
        acceleration.y = -MAX_DECELERATION;
        if( velocity.y - acceleration.y < 0 )
        {
          acceleration.y = -( acceleration.y - velocity.y );
        }
      }
      else if( velocity.y < 0 )
      {
        acceleration.y = MAX_DECELERATION;
        if( velocity.y + acceleration.y > 0 )
        {
          acceleration.y = ( acceleration.y - velocity.y );
        }
      }

    }

    // modify and check the ship's velocity
    velocity.add( acceleration );
    VectorUtils.adjustByRange( velocity, -MAX_SPEED, MAX_SPEED );

    // modify and check the ship's position, applying the delta parameter
    position.add( velocity.x * delta, velocity.y * delta );

    // we can't let the ship go off the screen, so here we check the new
    // ship's position against the stage's dimensions, correcting it if
    // needed and zeroing the velocity, so that the ship stops flying in the
    // current direction
    if( VectorUtils.adjustByRangeX( position, 0, ( getStage().getWidth() - getWidth() ) ) )
    { velocity.x = 0; }
    if( VectorUtils.adjustByRangeY( position, 0, ( getStage().getHeight() - getHeight() ) ) )
    { velocity.y = 0; }

    // update the ship's actual position
    setX( position.x );
    setY( position.y );
  }

  /**
   * Tilts the ship to the direction its moving.
   */
  private void tiltShip( final float delta )
  {
    // the animation's frame to be shown
    final TextureRegion frame;

    // find the appropriate frame of the tilt animation to be drawn
    if( velocity.x < 0 )
    {
      frame = tiltAnimation.getKeyFrame( tiltAnimationStateTime += delta, false );
      if( frame.isFlipX() )
      {
        frame.flip( true, false );
      }
    }
    else if( velocity.x > 0 )
    {
      frame = tiltAnimation.getKeyFrame( tiltAnimationStateTime += delta, false );
      if( !frame.isFlipX() )
      {
        frame.flip( true, false );
      }
    }
    else
    {
      tiltAnimationStateTime = 0;
      frame = tiltAnimation.getKeyFrame( 0, false );
    }

    // there is no performance issues when setting the same frame multiple
    // times as the current region (the call will be ignored in this case)
    setDrawable( tiltAnimationDrawables.get( frame ) );
  }

}
