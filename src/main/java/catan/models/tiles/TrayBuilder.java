package catan.models.tiles;

import java.util.Collections;
import java.util.LinkedList;

public class TrayBuilder {
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
        while (number-- > 0) tiles.add(new Pasture());
        return this;
    }

    public TrayBuilder addForest(int number) {
        while (number-- > 0) tiles.add(new Forest());
        return this;
    }

    public TrayBuilder addField(int number) {
        while (number-- > 0) tiles.add(new Field());
        return this;
    }

    public TrayBuilder addHill(int number) {
        while (number-- > 0) tiles.add(new Hill());
        return this;
    }

    public TrayBuilder addMountain(int number) {
        while (number-- > 0) tiles.add(new Mountain());
        return this;
    }

    public TrayBuilder addDesert(int number) {
        while (number-- > 0) tiles.add(new Desert());
        return this;
    }

    public Tray build(int width) {
        Collections.shuffle(tiles);
        return new Tray(tiles, width);
    }
}
