package catan.views.gui;

import catan.controllers.PlayerController;
import catan.models.cards.Card;
import catan.models.cards.Progress;
import catan.models.cards.VictoryPoint;
import catan.models.players.CardDeck;
import catan.views.View;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.util.LinkedList;
import java.util.function.ToIntFunction;

public class CardDeckView extends JPanel implements View<CardDeck> {
    private final LinkedList<CardsView> cardsViews;
    private CardDeck model;

    public CardDeckView() {
        setLayout(new BoxLayout(this, BoxLayout.X_AXIS));
        setPreferredSize(new Dimension(400, 110));
        setBorder(BorderFactory.createTitledBorder(new LineBorder(Color.BLACK, 2), "Card deck"));

        JPanel cards = new JPanel();
        cards.setBorder(BorderFactory.createTitledBorder(new LineBorder(Color.BLACK, 2), "Cards"));
        cardsViews = new LinkedList<>();
        cardsViews.add(new CardsView("Knight", CardDeck::countKnightCard));

        for(var c : Progress.values())
            cardsViews.add(new CardsView(c.toString(), (d) -> d.countProgressCard(c)));
        for(var c : VictoryPoint.values())
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
        use.addActionListener(e -> {
            String cardName = (String) comboBox.getSelectedItem();
            if(cardName != null) {
                switch(cardName) {
                    case "BuildRoad" -> {}
                    case "Invention" -> {}
                    case "Knight" -> {}
                    case "Monopoly" -> {}
                    default -> {}
                }
            }
        });
        action.add(use);

        JButton buy = new JButton("buy");
        buy.addActionListener(e -> PlayerController.getInstance().buyCard());
        action.add(buy);

        add(action);
    }

    public void setModel(CardDeck model) {
        this.model = model;
        this.model.addChangeListener(this::update);
        update();
    }

    private void update() {
        cardsViews.forEach(CardsView::update);
    }

    public class CardsView extends JPanel {
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

        private void update() {
            int value = model != null ? function.applyAsInt(model) : -1;
            label.setText(cardName + ": " + value);
        }
    }
}