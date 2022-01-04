package catan.views.gui;

import catan.controllers.ThiefController;
import catan.models.players.Thief;
import catan.views.View;

import javax.swing.*;
import java.awt.*;

public class ThiefView extends JPanel implements View<Thief> {
    private final static JButton moveThief = new JButton("Move thief");

    static {
        moveThief.addActionListener(e -> ThiefController.getInstance().move());
        moveThief.setEnabled(false);
        Thief.getInstance().addChangeListener(() -> moveThief.setEnabled(Thief.getInstance().isMovable()));
    }

    public ThiefView() {
        setSize(20, 20);
        setBackground(Color.BLACK);
    }

    public static JButton getMoveThiefButton() {
        return moveThief;
    }

    @Override
    public void setModel(Thief model) {
    }
}