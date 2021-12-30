package catan.views.gui;

import catan.controllers.PlayerController;
import catan.controllers.TrayController;
import catan.models.CardDeck;
import catan.models.Color;
import catan.models.Player;

import javax.swing.*;
import java.awt.*;

public class Game extends JFrame {
    public Game() {
        TrayController trayController = TrayController.getInstance();
        add(trayController.getView(), BorderLayout.CENTER);

        Player[] players = new Player[]{new Player(Color.Red), new Player(Color.Blue), new Player(Color.White)};
        PlayerController playerController = PlayerController.getInstance();
        playerController.setModel(players[0]);
        add(playerController.getView(), BorderLayout.EAST);

        CardDeck cardDeck = new CardDeck();
        CardDeckView cardDeckView = new CardDeckView(cardDeck);
        add(cardDeckView, BorderLayout.SOUTH);

        pack();
        setLocationRelativeTo(null);
        setResizable(false);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
    }
}