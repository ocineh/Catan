package catan.models.tiles;

import catan.models.players.Thief;

import java.util.Collections;
import java.util.LinkedList;
import java.util.Random;

public class TrayBuilder {
    private final static Random random = new Random();
    private final LinkedList<Tile> tiles;
    private int width;

    public TrayBuilder() {
        tiles = new LinkedList<>();
    }

    public static Tray buildDefault() {
        return new TrayBuilder()
                .addPasture(4)
                .addForest(4)
                .addField(4)
                .addHill(3)
                .addMountain(3)
                .addDesert(2)
                .build(4);
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public TrayBuilder addPasture(int number) {
        while(number-- > 0) tiles.add(new Tile.Pasture());
        return this;
    }

    public TrayBuilder addForest(int number) {
        while(number-- > 0) tiles.add(new Tile.Forest());
        return this;
    }

    public TrayBuilder addField(int number) {
        while(number-- > 0) tiles.add(new Tile.Field());
        return this;
    }

    public TrayBuilder addHill(int number) {
        while(number-- > 0) tiles.add(new Tile.Hill());
        return this;
    }

    public TrayBuilder addMountain(int number) {
        while(number-- > 0) tiles.add(new Tile.Mountain());
        return this;
    }

    public TrayBuilder addDesert(int number) {
        while(number-- > 0) tiles.add(new Tile.Desert());
        return this;
    }

    public Tray build(int width) {
        Collections.shuffle(tiles);
        Thief.getInstance().setTile(tiles.get(random.nextInt(tiles.size())));
        return new Tray(tiles, width);
    }
}
