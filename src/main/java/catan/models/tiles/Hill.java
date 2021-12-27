package catan.models.tiles;

/**
 * The type Hill represents a tile that produces brick.
 *
 * @author Hocine
 * @see Tile
 */
public class Hill extends Tile {
    Hill() {
        super();
    }

    @Override
    public Resource produce() {
        return Resource.Brick;
    }
}