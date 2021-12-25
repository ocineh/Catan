package catan.models;


public abstract class Building {
    private final Color color;
    private final int points;

    Building(Color color, int points) {
        this.color = color;
        this.points = points;
    }

    public java.awt.Color getColor() {
        return switch(color) {
            case Blue -> java.awt.Color.BLUE;
            case White -> java.awt.Color.WHITE;
            case Red -> java.awt.Color.RED;
        };
    }

    public final int getPoints() {
        return points;
    }

    public static class Colony extends Building {
        private Colony(Color color, int points) {
            super(color, points);
        }

        public Colony(Color color) {
            this(color, 2);
        }
    }

    public static class City extends Colony {
        public City(Color color) {
            super(color, 2);
        }
    }

    public static class Road extends Building {
        public Road(Color color) {
            super(color, 1);
        }
    }
}