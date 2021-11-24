package catan.cards;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Predicate;

public class CardDeck {
    private final HashMap<Card, Integer> cards;

    public CardDeck() {
        this.cards = new HashMap<>();
        for(Card card : Card.values()) {
            if(card.isKnight()) cards.put(card, 14);
            else if(card.isProgressCard()) cards.put(card, 2);
            else if(card.isVictoryPointCard()) cards.put(card, 1);
        }
    }

    public int countCard() {
        return cards.values().stream().mapToInt(i -> i).sum();
    }

    public int countCard(Predicate<Card> predicate) {
        return cards.entrySet().stream()
                .filter(card -> predicate.test(card.getKey()))
                .mapToInt(Map.Entry::getValue).sum();
    }

    public boolean useCard(Card card) {
        if(cards.get(card) == 0) return false;
        cards.computeIfPresent(card, ((c, integer) -> integer--));
        return true;
    }
}