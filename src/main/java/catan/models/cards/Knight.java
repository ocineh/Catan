package catan.models.cards;

import catan.models.players.Player;

public class Knight implements Card, UsableCard {
    private boolean used = false;

    public Knight() {
    }

    @Override
    public boolean isKnight() {
        return true;
    }

    @Override
    public void use(Player player) {
        if(!used) {
            used = true;
            // TODO: Allows the player to move the thief
        }
    }

    @Override
    public boolean isUsed() {
        return used;
    }
}
