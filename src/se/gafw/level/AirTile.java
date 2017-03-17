package se.gafw.level;

import se.gafw.graphics.Sprite;

/**
 *
 * Used when creating Tile objects that will return false when calling solid()
 * 
 */
public class AirTile extends Tile {

    public AirTile(Sprite sprite){super(sprite);}

    public boolean solid()
    {
        return false;
    }
}
