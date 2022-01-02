package catan.models.cards;

public enum VictoryPointCard {
    University(1), Church(1), Library(1), Parliament(1), MarketPlace(1);
    private final int points;

    VictoryPointCard(int points) {
        this.points = points;
    }

    public int getPoints() {
        return points;
    }
}
