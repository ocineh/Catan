package catan.models.tiles;

public class Hill extends Tile {
    Hill() {
        super();
    }

    @Override
    public Resource produce() {
        return Resource.Brick;
    }
}