package catan.models;

import java.util.Random;

public enum Card {
    Knight,
    Invention, Monopoly, BuildRoad,
    University(1), Church(1), Library(1), Parliament(1), MarketPlace(1);
    private final int points;

    Card(int points) {
        this.points = points;
    }

    Card() {
        this(0);
    }

    public int getPoints() {
        return points;
    }

    public boolean isProgressCard() {
        return switch(this) {
            case Invention, BuildRoad, Monopoly -> true;
            default -> false;
        };
    }

    public boolean isVictoryPointCard() {
        return points > 0;
    }

    public boolean isKnight() {
        return this.equals(Knight);
    }

    private static final Random random = new Random();

    public static Card pickRandom() {
        int index = random.nextInt(Card.values().length);
        return Card.values()[index];
    }
}