package catan.models;

import catan.models.tiles.Tile;

public class Thief {
    private static final Thief instance = new Thief();
    private Tile tile;

    private Thief() {
    }

    public static Thief getInstance() {
        return instance;
    }

    public Tile getTile() {
        return tile;
    }

    public void setTile(Tile tile) {
        this.tile = tile;
    }
}
