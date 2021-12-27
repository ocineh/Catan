package catan.models.tiles;

/**
 * The type Mountain represents a tile that produces ore.
 *
 * @author Hocine
 * @see Tile
 */
public class Mountain extends Tile {
    Mountain() {
        super();
    }

    @Override
    public Resource produce() {
        return Resource.Ore;
    }
}