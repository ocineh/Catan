package catan.models;

public interface Card {
    static String[] values() {
        return new String[]{"BuildRoad", "Invention", "Knight", "Monopoly"};
    }

    default int getPoints() {
        return 0;
    }

    default boolean usable() {
        return true;
    }

    default boolean isProgress() {
        return false;
    }

    default boolean isVictoryPoint() {
        return false;
    }

    default boolean isKnight() {
        return false;
    }

    enum Progress implements Card {
        Invention, Monopoly, BuildRoad;

        @Override
        public boolean isProgress() {
            return true;
        }
    }

    enum VictoryPoint implements Card {
        University(1), Church(1), Library(1), Parliament(1), MarketPlace(1);
        private final int points;

        VictoryPoint(int points) {
            this.points = points;
        }

        public int getPoints() {
            return points;
        }

        @Override
        public boolean usable() {
            return false;
        }

        @Override
        public boolean isVictoryPoint() {
            return true;
        }
    }

    class Knight implements Card {
        @Override
        public boolean isKnight() {
            return true;
        }
    }
}