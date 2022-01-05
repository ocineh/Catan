package catan.models.players;


import catan.models.tiles.Resource;
import catan.models.tiles.Tile;

public abstract class Building {
    private final Player player;
    private final int points;
    private Tile tile;

    private Building(Player player, int points) {
        this.player = player;
        this.points = points;
    }

    public void setTile(Tile tile) {
        this.tile = tile;
    }

    public boolean isNotPlaced() {
        return tile == null;
    }

    public java.awt.Color getColor() {
        return player.getColor().toAwtColor();
    }

    public final int getPoints() {
        return points;
    }

    public boolean isColony() {
        return false;
    }

    public boolean isCity() {
        return false;
    }

    public boolean isRoad() {
        return false;
    }

    public void harvest(Resource resource) {
        player.getInventory().addResource(resource);
    }

    public static class Colony extends Building {
        private Colony(Player player, int points) {
            super(player, points);
        }

        Colony(Player player) {
            this(player, 2);
        }

        @Override
        public boolean isColony() {
            return true;
        }
    }

    public static class City extends Colony {
        City(Player player) {
            super(player, 2);
        }

        @Override
        public boolean isColony() {
            return false;
        }

        @Override
        public boolean isCity() {
            return true;
        }
    }

    public static class Road extends Building {
        Road(Player player) {
            super(player, 1);
        }

        @Override
        public boolean isRoad() {
            return true;
        }
    }
}