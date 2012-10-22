package com.blogspot.steigert.tyrian.domain;

/**
 * An item that can be added to the ship.
 */
public interface Item
{
  /**
   * Retrieves the price to acquire this item.
   */
  int getPrice();
}
