package catan.tiles;

public class Field extends Tile {
    Field() {
        super();
    }

    @Override
    public Resource produce() {
        return Resource.Grain;
    }
}