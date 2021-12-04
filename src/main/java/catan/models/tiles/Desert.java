package catan.models.tiles;

public class Desert extends Tile {
    Desert() {
        super();
    }

    @Override
    public int getNumber() {
        return -1;
    }

    @Override
    public Resource produce() {
        return null;
    }
}