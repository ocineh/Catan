package catan.models;

import catan.models.players.Color;
import catan.models.players.Player;
import catan.models.tiles.Tray;
import catan.models.tiles.TrayBuilder;

public class Game extends AbstractModel {
    private final Player[] players;
    private final Tray tray;
    private int actual = 0;

    public Game(Player[] players, Tray tray) {
        this.players = players;
        this.tray = tray;
    }

    public Game() {
        this(new Player[]{
                new Player(Color.Blue),
                new Player(Color.Red),
                new Player(Color.White)
        }, TrayBuilder.buildDefault());
    }

    public void nextRound() {
        if(++actual == players.length) actual = 0;
        changed();
    }

    public Player getActualPlayer() {
        return players[actual];
    }

    public Tray getTray() {
        return tray;
    }
}