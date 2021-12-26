package catan.views;

import catan.models.Player;

import javax.swing.*;
import java.awt.*;

public class PlayersView extends JPanel {
    private Player[] players;
    private int actual = 0;

    public PlayersView(Player[] players) {
        this.players = players;
        setBorder(BorderFactory.createTitledBorder("Player"));
        setPreferredSize(new Dimension(100, 100));
    }

    public void nextPlayer() {
        actual++;
    }
}