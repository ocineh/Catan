package catan.views;

import catan.models.Card;
import catan.models.CardDeck;

import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.util.LinkedList;

public class CardDeckView extends JPanel {
    private final LinkedList<CardsView> cardsViews;

    public CardDeckView(CardDeck deck) {
        setBorder(BorderFactory.createTitledBorder(new LineBorder(Color.BLACK, 2), "Cards"));
        setPreferredSize(new Dimension(100, 100));

        cardsViews = new LinkedList<>();
        cardsViews.add(new CardsView("Knight", CardDeck::countKnightCard));

        for(var c : Card.Progress.values())
            cardsViews.add(new CardsView(c.toString(), (d) -> d.countProgressCard(c)));
        for(var c : Card.VictoryPoint.values())
            cardsViews.add(new CardsView(c.toString(), (d) -> d.countVictoryPointCard(c)));

        cardsViews.forEach(this::add);
        update(deck);
    }

    public void update(CardDeck cardDeck) {
        cardsViews.forEach(cardsView -> cardsView.update(cardDeck));
    }
}