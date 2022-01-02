package catan.models.players;

import catan.models.AbstractModel;
import catan.models.cards.Card;
import catan.models.cards.Knight;
import catan.models.cards.Progress;
import catan.models.cards.VictoryPoint;

import java.util.LinkedList;
import java.util.List;
import java.util.Random;

public final class CardDeck extends AbstractModel {
    private final static Random random = new Random();
    private final LinkedList<VictoryPoint> victoryPointCard;
    private final LinkedList<Knight> knightCard;
    private final LinkedList<Progress> progressCard;
    private final Player player;

    CardDeck(Player player) {
        this.player = player;
        this.victoryPointCard = new LinkedList<>();
        this.knightCard = new LinkedList<>();
        this.progressCard = new LinkedList<>();
    }

    public List<VictoryPoint> getVictoryPointCard() {
        return List.copyOf(victoryPointCard);
    }

    public List<Progress> getProgressCards() {
        return List.copyOf(progressCard);
    }

    public List<Knight> getKnightCard() {
        return List.copyOf(knightCard);
    }

    public int countKnightCard() {
        return knightCard.size();
    }

    public int countVictoryPointCard() {
        return victoryPointCard.size();
    }

    public int countVictoryPointCard(VictoryPoint card) {
        return (int) victoryPointCard.stream().filter(c -> c == card).count();
    }

    public int countProgressCard() {
        return progressCard.size();
    }

    public int countProgressCard(Progress card) {
        return (int) progressCard.stream().filter(c -> c == card).count();
    }

    public int getTotalPoints() {
        return victoryPointCard.stream().mapToInt(Card::getPoints).sum();
    }

    public void addRandomCard() {
        switch(random.nextInt(9)) {
            case 0: progressCard.add(Progress.Invention);
                break;
            case 1: progressCard.add(Progress.Monopoly);
                break;
            case 2: progressCard.add(Progress.BuildRoad);
                break;
            case 3: victoryPointCard.add(VictoryPoint.University);
                break;
            case 4: victoryPointCard.add(VictoryPoint.Church);
                break;
            case 5: victoryPointCard.add(VictoryPoint.Library);
                break;
            case 6: victoryPointCard.add(VictoryPoint.Parliament);
                break;
            case 7: victoryPointCard.add(VictoryPoint.MarketPlace);
                break;
            case 8: knightCard.add(new Knight());
                break;
        }
        changed();
    }

    public void useKnightCard() {
        if(knightCard.size() > 0) {
            Knight knight = knightCard.remove();
            knight.use(player);
        }
    }

    public void useProgressCard(Progress card) {
        if(progressCard.removeFirstOccurrence(card)) card.use(player);
    }
}