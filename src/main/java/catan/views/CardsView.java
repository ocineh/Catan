package catan.views;

import catan.models.CardDeck;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.util.function.ToIntFunction;

public class CardsView extends JPanel {
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