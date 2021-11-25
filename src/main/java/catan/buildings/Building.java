package catan.buildings;


import catan.Color;

public abstract class Building {
    private final Color color;
    private final int points;

    Building(Color color, int points) {
        this.color = color;
        this.points = points;
    }

    public final int getPoints() {
        return points;
    }

    public static class Colony extends Building {
        private Colony(Color color, int points) {
            super(color, points);
        }

        Colony(Color color) {
            this(color, 2);
        }
    }

    public static class City extends Colony {
        City(Color color) {
            super(color, 2);
        }
    }

    public static class Road extends Building {
        Road(Color color) {
            super(color, 1);
        }
    }
}