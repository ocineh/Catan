package catan.cards;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Predicate;

public class CardDeck {
    private final HashMap<Card, Integer> cards;

    public CardDeck() {
        this.cards = new HashMap<>();
    }

    public static CardDeck buildSetCard() {
        CardDeck cardDeck = new CardDeck();
        for(Card card : Card.values()) {
            if(card.isKnight()) cardDeck.cards.put(card, 14);
            else if(card.isProgressCard()) cardDeck.cards.put(card, 2);
            else if(card.isVictoryPointCard()) cardDeck.cards.put(card, 1);
        }
        return cardDeck;
    }

    public int count() {
        return cards.values().stream().mapToInt(i -> i).sum();
    }

    public int count(Predicate<Map.Entry<Card, Integer>> predicate) {
        return cards.entrySet().stream()
                .filter(predicate)
                .mapToInt(Map.Entry::getValue).sum();
    }

    public boolean use(Card card) {
        if(card.isVictoryPointCard() || cards.get(card) == 0) return false;
        cards.computeIfPresent(card, ((c, integer) -> integer--));
        return true;
    }

    public void add(Card card) {
        if(cards.containsKey(card)) cards.computeIfPresent(card, (c, integer) -> integer++);
        else cards.put(card, 1);
    }
}