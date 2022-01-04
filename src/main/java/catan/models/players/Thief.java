package catan.models.players;

import catan.models.AbstractModel;
import catan.models.tiles.Tile;

public class Thief extends AbstractModel {
    private static final Thief instance = new Thief();
    private Tile tile;
    private boolean movable = false;

    private Thief() {
    }

    public static Thief getInstance() {
        return instance;
    }

    public boolean isMovable() {
        return movable;
    }

    public void setMovable(boolean movable) {
        this.movable = movable;
        changed();
    }

    public Tile getTile() {
        return tile;
    }

    public void setTile(Tile tile) {
        if(this.tile != null) {
            this.tile.setThief(null);
            this.tile.changed();
        }
        this.tile = tile;
        movable = false;
        tile.setThief(this);
        tile.changed();
        changed();
    }
}
