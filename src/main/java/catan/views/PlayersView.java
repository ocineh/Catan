package catan.views;

import catan.models.Player;

import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;

public class PlayersView extends JPanel {
    private final Player[] players;
    private final InventoryView inventory;
    private int actual = 0;

    public PlayersView(Player[] players) {
        this.players = players;
        setBorder(BorderFactory.createTitledBorder(new LineBorder(Color.BLACK, 2), "Player"));
        setPreferredSize(new Dimension(100, 100));

        inventory = new InventoryView(players[actual]);
        add(inventory);
    }

    public void nextPlayer() {
        if(++actual == players.length) actual = 0;
        inventory.setPlayer(players[actual]);
    }
}