package catan.models.players;

import catan.models.AbstractModel;
import catan.models.tiles.Tile;

public class Thief extends AbstractModel {
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
        if(this.tile != null){
            this.tile.setThief(null);
            this.tile.changed();
        }
        this.tile = tile;
        tile.setThief(this);
        tile.changed();
        changed();
    }
}
