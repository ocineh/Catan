package catan.models.cards;

public enum VictoryPoint implements Card {
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
