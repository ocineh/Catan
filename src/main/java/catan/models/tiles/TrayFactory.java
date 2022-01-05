package catan.models.tiles;

import catan.models.players.Thief;

import java.util.Collections;
import java.util.LinkedList;
import java.util.Random;

public class TrayFactory {
    private final static Random random = new Random();
    private final LinkedList<Tile> tiles;

    private TrayFactory() {
        tiles = new LinkedList<>();
    }

    public static Tray buildNormal() {
        return new TrayFactory()
                .addPasture(4)
                .addForest(4)
                .addField(4)
                .addHill(3)
                .addMountain(3)
                .addDesert(2)
                .build(4);
    }

    public static Tray buildBig() {
        return new TrayFactory()
                .addPasture(6)
                .addForest(6)
                .addField(6)
                .addHill(6)
                .addMountain(6)
                .addDesert(6)
                .build(6);
    }

    public static Tray buildGigantic() {
        return new TrayFactory()
                .addPasture(11)
                .addForest(11)
                .addField(11)
                .addHill(11)
                .addMountain(10)
                .addDesert(10)
                .build(8);
    }

    private TrayFactory addPasture(int number) {
        while(number-- > 0) tiles.add(new Tile.Pasture());
        return this;
    }

    private TrayFactory addForest(int number) {
        while(number-- > 0) tiles.add(new Tile.Forest());
        return this;
    }

    private TrayFactory addField(int number) {
        while(number-- > 0) tiles.add(new Tile.Field());
        return this;
    }

    private TrayFactory addHill(int number) {
        while(number-- > 0) tiles.add(new Tile.Hill());
        return this;
    }

    private TrayFactory addMountain(int number) {
        while(number-- > 0) tiles.add(new Tile.Mountain());
        return this;
    }

    private TrayFactory addDesert(int number) {
        while(number-- > 0) tiles.add(new Tile.Desert());
        return this;
    }

    private Tray build(int width) {
        Collections.shuffle(tiles);
        Thief.getInstance().setTile(tiles.get(random.nextInt(tiles.size())));
        return new Tray(tiles, width);
    }
}
