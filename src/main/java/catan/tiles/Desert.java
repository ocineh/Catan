package catan.tiles;

public class Desert extends Tile {
    Desert() {
        super();
    }

    @Override
    public int getNumber() {
        return -1;
    }

    @Override
    public Tile.Resource produce() {
        return null;
    }
}