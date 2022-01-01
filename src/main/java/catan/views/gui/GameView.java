package catan.views.gui;

import catan.models.Game;
import catan.views.View;

import javax.swing.*;
import java.awt.*;

public class GameView extends JFrame implements View<Game> {
    private final PlayerView playerView;
    private final TrayView trayView;
    private final CardDeckView cardDeckView;
    private Game model;

    public GameView() {
        playerView = new PlayerView();
        trayView = new TrayView();
        cardDeckView = new CardDeckView();

        add(playerView, BorderLayout.EAST);
        add(trayView, BorderLayout.CENTER);
        add(cardDeckView, BorderLayout.SOUTH);

        setResizable(false);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
    }

    @Override
    public void setModel(Game model) {
        this.model = model;
        model.addChangeListener(() -> {
            playerView.setModel(model.getActualPlayer());
            cardDeckView.setModel(model.getActualPlayer().getCards());
        });
        trayView.setModel(model.getTray());
        playerView.setModel(model.getActualPlayer());
        cardDeckView.setModel(model.getActualPlayer().getCards());
    }

    public PlayerView getPlayerView() {
        return playerView;
    }

    public TrayView getTrayView() {
        return trayView;
    }

    public CardDeckView getCardDeckView() {
        return cardDeckView;
    }
}