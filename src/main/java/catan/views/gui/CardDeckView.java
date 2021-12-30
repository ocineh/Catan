package catan.views.gui;

import catan.models.Card;
import catan.models.CardDeck;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.util.LinkedList;
import java.util.function.ToIntFunction;

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

    public static class CardsView extends JPanel {
        private final JLabel label;
        private final String cardName;
        private final ToIntFunction<CardDeck> function;

        public CardsView(String cardName, ToIntFunction<CardDeck> function) {
            this.cardName = cardName;
            this.function = function;
            Border border = new LineBorder(Color.BLACK, 2, true);
            setBorder(border);

            label = new JLabel(cardName + ": 0");
            label.setBorder(new EmptyBorder(1, 1, 1, 1));
            add(label);
        }

        public void update(CardDeck cardDeck) {
            label.setText(cardName + ": " + function.applyAsInt(cardDeck));
        }
    }
}