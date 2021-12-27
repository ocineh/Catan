package catan.models.tiles;

/**
 * The type Pasture represents a tile that produces Wool.
 *
 * @author Hocine
 * @see Tile
 */
public class Pasture extends Tile {
    Pasture() {
        super();
    }

    @Override
    public Resource produce() {
        return Resource.Wool;
    }
}