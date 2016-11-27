package se.gafw.level;

import se.gafw.graphics.Sprite;

/**
 * Created by Alexander on 11/27/2016.
 */
public class AirTile extends Tile {

    public AirTile(Sprite sprite){super(sprite);}

    public boolean solid()
    {
        return false;
    }
}
