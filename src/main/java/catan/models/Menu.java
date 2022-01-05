package catan.models;

import catan.models.players.Bot;
import catan.models.players.Human;
import catan.models.players.Player;
import catan.models.tiles.Tray;
import catan.models.tiles.TrayFactory;

public class Menu extends AbstractModel {
    private final PlayerSetting[] playerSettings;
    private String mode = "normal 4*5";
    private int score = 10;

    public Menu() {
        Player.Color[] colors = Player.Color.values();
        this.playerSettings = new PlayerSetting[colors.length];
        for(int i = 0; i < colors.length; ++i) playerSettings[i] = new PlayerSetting(colors[i]);
    }

    public void setMode(String trayMode) {
        this.mode = trayMode;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public PlayerSetting[] getPlayerSettings() {
        return playerSettings;
    }

    public Player[] getPlayers() {
        Player[] players = new Player[playerSettings.length];
        for(int i = 0; i < players.length; ++i) players[i] = playerSettings[i].toPlayer();
        return players;
    }

    public Tray getTray() throws IllegalArgumentException {
        switch(mode) {
            case "normal 4*5": return TrayFactory.buildNormal();
            case "big 6*6": return TrayFactory.buildBig();
            case "gigantic 8*8": return TrayFactory.buildGigantic();
            default: throw new IllegalArgumentException(mode);
        }
    }

    public Game toGame() {
        return new Game(getPlayers(), getTray(), score);
    }

    public static class PlayerSetting {
        private final Player.Color color;
        private String type = "Human";

        public PlayerSetting(Player.Color color) {
            this.color = color;
        }

        public Player.Color getColor() {
            return color;
        }

        public Player toPlayer() {
            switch(type) {
                case "Human": return new Human(color);
                case "Bot": return new Bot(color);
                default: throw new IllegalArgumentException();
            }
        }

        public void setType(String type) {
            this.type = type;
        }
    }
}