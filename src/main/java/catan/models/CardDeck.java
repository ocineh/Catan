package catan.models;

import java.util.LinkedList;
import java.util.List;
import java.util.Random;

public final class CardDeck {
    private final static Random random = new Random();
    private final LinkedList<Card.VictoryPoint> victoryPointCard;
    private final LinkedList<Card.Knight> knightCard;
    private final LinkedList<Card.Progress> progressCard;

    public CardDeck() {
        this.victoryPointCard = new LinkedList<>();
        this.knightCard = new LinkedList<>();
        this.progressCard = new LinkedList<>();
    }

    public List<Card.VictoryPoint> getVictoryPointCard() {
        return List.copyOf(victoryPointCard);
    }

    public List<Card.Progress> getProgressCards() {
        return List.copyOf(progressCard);
    }

    public List<Card.Knight> getKnightCard() {
        return List.copyOf(knightCard);
    }

    public int countKnightCard() {
        return knightCard.size();
    }

    public int countVictoryPointCard() {
        return victoryPointCard.size();
    }

    public int countVictoryPointCard(Card.VictoryPoint card) {
        return (int) victoryPointCard.stream().filter(c -> c == card).count();
    }

    public int countProgressCard() {
        return progressCard.size();
    }

    public int countProgressCard(Card.Progress card) {
        return (int) progressCard.stream().filter(c -> c == card).count();
    }

    public int getTotalPoints() {
        return victoryPointCard.stream().mapToInt(Card::getPoints).sum();
    }

    void addRandomCard() {
        switch(random.nextInt(9)) {
            case 0 -> progressCard.add(Card.Progress.Invention);
            case 1 -> progressCard.add(Card.Progress.Monopoly);
            case 2 -> progressCard.add(Card.Progress.BuildRoad);
            case 3 -> victoryPointCard.add(Card.VictoryPoint.University);
            case 4 -> victoryPointCard.add(Card.VictoryPoint.Church);
            case 5 -> victoryPointCard.add(Card.VictoryPoint.Library);
            case 6 -> victoryPointCard.add(Card.VictoryPoint.Parliament);
            case 7 -> victoryPointCard.add(Card.VictoryPoint.MarketPlace);
            case 8 -> knightCard.add(new Card.Knight());
        }
    }
}