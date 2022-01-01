package catan.views.gui;

import catan.models.Game;
import catan.views.View;

import javax.swing.*;
import java.awt.*;

public class GameView extends JPanel implements View<Game> {
    private final PlayerView playerView;
    private final TrayView trayView;
    private Game model;

    public GameView() {
        playerView = new PlayerView();
        trayView = new TrayView();

        setLayout(new BorderLayout());
        add(playerView, BorderLayout.EAST);
        add(trayView, BorderLayout.CENTER);
    }

    @Override
    public void setModel(Game model) {
        this.model = model;
        model.addChangeListener(() -> playerView.setModel(model.getActualPlayer()));
        trayView.setModel(model.getTray());
        playerView.setModel(model.getActualPlayer());
    }

    public PlayerView getPlayerView() {
        return playerView;
    }

    public TrayView getTrayView() {
        return trayView;
    }
}