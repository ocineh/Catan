package catan.models.players;

import catan.models.AbstractModel;
import catan.models.cards.KnightCard;
import catan.models.cards.ProgressCard;
import catan.models.cards.VictoryPointCard;
import catan.models.exceptions.CardAlreadyUsed;
import catan.models.exceptions.NoCardAvailableException;

import java.util.LinkedList;
import java.util.List;
import java.util.Random;

public final class CardDeck extends AbstractModel {
    private final static Random random = new Random();
    private final LinkedList<VictoryPointCard> victoryPointCard;
    private final LinkedList<KnightCard> knightCard;
    private final LinkedList<ProgressCard> progressCard;
    private final Player player;

    CardDeck(Player player) {
        this.player = player;
        this.victoryPointCard = new LinkedList<>();
        this.knightCard = new LinkedList<>();
        this.progressCard = new LinkedList<>();
    }

    public List<VictoryPointCard> getVictoryPointCard() {
        return List.copyOf(victoryPointCard);
    }

    public List<ProgressCard> getProgressCards() {
        return List.copyOf(progressCard);
    }

    public List<KnightCard> getKnightCard() {
        return List.copyOf(knightCard);
    }

    public int countKnightCard() {
        return knightCard.size();
    }

    public int countVictoryPointCard() {
        return victoryPointCard.size();
    }

    public int countVictoryPointCard(VictoryPointCard card) {
        return (int) victoryPointCard.stream().filter(c -> c == card).count();
    }

    public int countProgressCard() {
        return progressCard.size();
    }

    public int countProgressCard(ProgressCard card) {
        return (int) progressCard.stream().filter(c -> c == card).count();
    }

    public int getTotalPoints() {
        return victoryPointCard.stream().mapToInt(VictoryPointCard::getPoints).sum();
    }

    public void addRandomCard() {
        switch(random.nextInt(9)) {
            case 0: progressCard.add(ProgressCard.Invention);
                break;
            case 1: progressCard.add(ProgressCard.Monopoly);
                break;
            case 2: progressCard.add(ProgressCard.BuildRoad);
                break;
            case 3: victoryPointCard.add(VictoryPointCard.University);
                break;
            case 4: victoryPointCard.add(VictoryPointCard.Church);
                break;
            case 5: victoryPointCard.add(VictoryPointCard.Library);
                break;
            case 6: victoryPointCard.add(VictoryPointCard.Parliament);
                break;
            case 7: victoryPointCard.add(VictoryPointCard.MarketPlace);
                break;
            case 8: knightCard.add(new KnightCard());
                break;
        }
        changed();
    }

    public void useKnightCard() throws NoCardAvailableException, CardAlreadyUsed {
        if(knightCard.size() > 0) {
            KnightCard knightCard = this.knightCard.remove();
            if(knightCard.isUsed()) useKnightCard();
            else {
                knightCard.use();
                changed();
            }
        } else throw new NoCardAvailableException();
    }
}