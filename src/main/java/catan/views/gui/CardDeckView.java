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

    public CardDeckView() {
        setLayout(new BoxLayout(this, BoxLayout.X_AXIS));
        setPreferredSize(new Dimension(400, 110));
        setBorder(BorderFactory.createTitledBorder(new LineBorder(Color.BLACK, 2), "Card deck"));

        JPanel cards = new JPanel();
        cards.setBorder(BorderFactory.createTitledBorder(new LineBorder(Color.BLACK, 2), "Cards"));
        cardsViews = new LinkedList<>();
        cardsViews.add(new CardsView("Knight", CardDeck::countKnightCard));

        for(var c : Card.Progress.values())
            cardsViews.add(new CardsView(c.toString(), (d) -> d.countProgressCard(c)));
        for(var c : Card.VictoryPoint.values())
            cardsViews.add(new CardsView(c.toString(), (d) -> d.countVictoryPointCard(c)));
        cardsViews.forEach(cards::add);
        cards.setMinimumSize(new Dimension(200, 200));
        add(cards);

        JPanel action = new JPanel();
        action.setMinimumSize(new Dimension(200, 200));
        action.setBorder(BorderFactory.createTitledBorder(new LineBorder(Color.BLACK, 2), "Action"));

        JComboBox<String> comboBox = new JComboBox<>(Card.values());
        action.add(comboBox);
        JButton use = new JButton("use");
        action.add(use);

        JButton buy = new JButton("buy");
        action.add(buy);

        add(action);
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
            setLayout(new BorderLayout());
            Border border = new LineBorder(Color.BLACK, 2, true);
            setBorder(border);

            label = new JLabel(cardName + ": 0");
            label.setBorder(new EmptyBorder(1, 1, 1, 1));
            add(label, BorderLayout.CENTER);
        }

        public void update(CardDeck cardDeck) {
            label.setText(cardName + ": " + function.applyAsInt(cardDeck));
        }
    }
}