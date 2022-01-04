package catan.models.players;

import catan.models.AbstractModel;
import catan.models.tiles.Resource;

import java.util.LinkedList;

public abstract class Player extends AbstractModel {
    private final static LinkedList<Player> players = new LinkedList<>();
    private final Color color;
    private final CardDeck cards;
    private final Inventory inventory;

    public Player(Color color) {
        this.color = color;
        this.cards = new CardDeck(this);
        this.inventory = new Inventory(this);
        players.add(this);
    }

    public static LinkedList<Player> getPlayers() {
        return players;
    }

    public static Player playerWithTheBiggestArmy() {
        if(players.size() == 0) return null;
        Player player = null;
        boolean tmp = false;
        for(Player p : players) {
            if(player == null) player = p;
            else if(p.cards.countKnightCard() > player.cards.countKnightCard()) {
                player = p;
                tmp = false;
            } else if(p.cards.countKnightCard() == player.cards.countKnightCard()) {
                tmp = true;
            }
        }
        if(!tmp) return player;
        return null;
    }

    public Color getColor() {
        return color;
    }

    public CardDeck getCards() {
        return cards;
    }

    public Inventory getInventory() {
        return inventory;
    }

    public int getPoints() {
        return cards.getTotalPoints() + inventory.getPoints();
    }

    public void buildRoad() {
        if(inventory.canBuildRoad()) {
            inventory.use(Resource.Lumber, Resource.Brick);
            inventory.add(new Building.Road(this));
            changed();
        }
    }

    public void buildColony() {
        if(inventory.canBuildColony()) {
            inventory.use(Resource.Lumber, Resource.Brick, Resource.Grain, Resource.Wool);
            inventory.add(new Building.Colony(this));
            changed();
        }
    }

    public void buildCity() {
        if(inventory.canBuildCity()) {
            inventory.use(Resource.Grain, Resource.Grain, Resource.Ore, Resource.Ore);
            inventory.add(new Building.City(this));
            changed();
        }
    }

    public void buyCard() {
        if(inventory.canBuyCard()) {
            inventory.use(Resource.Grain, Resource.Ore, Resource.Wool);
            cards.addRandomCard();
            changed();
        }
    }

    public boolean isBot(){
        return false;
    }

    public enum Color {
        Blue, White, Red;

        public java.awt.Color toAwtColor() {
            switch(this) {
                case Blue: return java.awt.Color.BLUE;
                case White: return java.awt.Color.WHITE;
                case Red: return java.awt.Color.RED;
            }
            return null;
        }
    }
}