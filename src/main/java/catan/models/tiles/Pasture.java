package catan.models.tiles;

public class Pasture extends Tile {
    Pasture() {
        super();
    }

    @Override
    public Resource produce() {
        return Resource.Wool;
    }
}