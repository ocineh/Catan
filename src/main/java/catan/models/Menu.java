package catan.models;

import catan.models.players.Bot;
import catan.models.players.Human;
import catan.models.players.Player;
import catan.models.tiles.Tray;
import catan.models.tiles.TrayFactory;

import java.util.ArrayList;

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

    public int countPlayers() {
        int n = 0;
        for(var p: playerSettings) if(!p.type.equals("None")) ++n;
        return n;
    }

    public Player[] getPlayers() {
        ArrayList<Player> players = new ArrayList<>();
        for(PlayerSetting playerSetting: playerSettings) {
            Player player = playerSetting.toPlayer();
            if(player != null) players.add(player);
        }
        return players.toArray(new Player[0]);
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
        private String type = "None";

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
                case "None": return null;
                default: throw new IllegalArgumentException();
            }
        }

        public void setType(String type) {
            this.type = type;
        }
    }
}