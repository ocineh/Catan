package catan.models.tiles;

public class Mountain extends Tile {
    Mountain() {
        super();
    }

    @Override
    public Resource produce() {
        return Resource.Ore;
    }
}