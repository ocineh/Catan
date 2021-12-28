package catan.models.tiles;

/**
 * The type Desert represents a tile that does not produce any resources.
 *
 * @author Hocine
 * @see Tile
 */
public class Desert extends Tile {
    Desert() {
        super();
    }

    @Override
    public int getNumber() {
        return -1;
    }

    @Override
    protected Resource produce() {
        return null;
    }
}