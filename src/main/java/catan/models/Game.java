package catan.models;

import catan.models.exceptions.CannotFinishRoundException;
import catan.models.players.Player;
import catan.models.players.Thief;
import catan.models.tiles.Tray;

import java.util.Random;

public class Game extends AbstractModel {
    private static final Random random = new Random();
    private final Player[] players;
    private final Tray tray;
    private int actual = 0;
    private Dice dice;

    public Game(Player[] players, Tray tray) {
        this.players = players;
        this.tray = tray;
    }

    public void nextRound() throws CannotFinishRoundException {
        if(Thief.getInstance().isMovable()) throw new CannotFinishRoundException("you must move the thief.");
        if(++actual == players.length) actual = 0;
        dice = null;
        changed();
    }

    public Player getActualPlayer() {
        return players[actual];
    }

    public Tray getTray() {
        return tray;
    }

    public Dice getBackThrownDice() {
        if(dice == null) {
            dice = new Dice();
            tray.harvest(dice.sum());
            if(dice.sum() == 7) Thief.getInstance().setMovable(true);
            changed();
        }
        return dice;
    }

    public Dice getDice() {
        return dice;
    }

    public class Dice {
        private final int a, b;

        private Dice() {
            a = 1 + random.nextInt(6);
            b = 1 + random.nextInt(6);
            dice = this;
        }

        public int sum() {
            return a + b;
        }

        @Override
        public String toString() {
            return "The result of the dice is " + a + " and " + b + " so the total is " + sum();
        }
    }
}