package catan.models;


public abstract class Building {
    private final Color color;
    private final int points;

    Building(Color color, int points) {
        this.color = color;
        this.points = points;
    }

    public java.awt.Color getColor() {
        return color.toAwtColor();
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

    public static class Colony extends Building {
        private Colony(Color color, int points) {
            super(color, points);
        }

        public Colony(Color color) {
            this(color, 2);
        }

        @Override
        public boolean isColony() {
            return true;
        }
    }

    public static class City extends Colony {
        public City(Color color) {
            super(color, 2);
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
        public Road(Color color) {
            super(color, 1);
        }

        @Override
        public boolean isRoad() {
            return true;
        }
    }
}