package catan.models.players;


import catan.models.tiles.Resource;

public abstract class Building {
    protected final Player player;
    private final int points;
    private boolean placed = false;

    private Building(Player player, int points) {
        this.player = player;
        this.points = points;
    }

    public void setPlaced() {
        this.placed = true;
    }

    public boolean isNotPlaced() {
        return !placed;
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

    public abstract void harvest(Resource resource);

    public static class Colony extends Building {
        private Colony(Player player, int points) {
            super(player, points);
        }

        Colony(Player player) {
            this(player, 1);
        }

        @Override
        public void harvest(Resource resource) {
            player.getInventory().addResource(resource);
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
        public void harvest(Resource resource) {
            super.harvest(resource);
            super.harvest(resource);
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

        @Override
        public void harvest(Resource resource) {
        }
    }
}