package catan.models.tiles;

/**
 * The type Forest represents a tile that produces lumber.
 *
 * @author Hocine
 * @see Tile
 */
public class Forest extends Tile {
    Forest() {
        super();
    }

    @Override
    protected Resource produce() {
        return Resource.Lumber;
    }
}