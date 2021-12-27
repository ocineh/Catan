package catan;

import catan.models.Color;
import catan.models.Player;
import catan.models.tiles.Tray;
import catan.models.tiles.TrayBuilder;
import catan.views.PlayersView;
import catan.views.TrayView;

import javax.swing.*;
import java.awt.*;

public class Game extends JFrame {
    public Game() {
        Tray tray = TrayBuilder.buildDefault();
        TrayView trayView = new TrayView(tray);
        add(trayView, BorderLayout.CENTER);

        Player[] players = new Player[]{new Player(Color.Red), new Player(Color.Blue), new Player(Color.White)};
        PlayersView playersView = new PlayersView(players);
        add(playersView, BorderLayout.EAST);

        pack();
        setLocationRelativeTo(null);
        setResizable(false);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
    }

    public static void main(String[] args) {
        Game game = new Game();
        game.setVisible(true);
    }
}