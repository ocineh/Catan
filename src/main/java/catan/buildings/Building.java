package catan.buildings;


public abstract class Building {
    //Colony(1), City(2), Road(1);
    private final int points;

    Building(int points) {
        this.points = points;
    }

    public final int getPoints() {
        return points;
    }

    public static class Colony extends Building {
        Colony(int points) {
            super(points);
        }

        Colony() {
            this(2);
        }
    }

    public static class City extends Colony {
        City() {
            super(2);
        }
    }

    public static class Road extends Building {
        Road() {
            super(1);
        }
    }
}