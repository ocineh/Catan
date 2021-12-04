package catan.tiles;

public class Forest extends Tile {
    Forest() {
        super();
    }

    @Override
    public Resource produce() {
        return Resource.Lumber;
    }
}