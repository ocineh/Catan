package catan.models.tiles;

/**
 * The type Field represents a tile that produces grain.
 *
 * @author Hocine
 * @see Tile
 */
public class Field extends Tile {
    Field() {
        super();
    }

    @Override
    protected Resource produce() {
        return Resource.Grain;
    }
}