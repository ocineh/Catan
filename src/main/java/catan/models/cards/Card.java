package catan.models.cards;

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
}