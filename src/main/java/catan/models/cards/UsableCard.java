package catan.models.cards;

import catan.models.players.Player;

public interface UsableCard {
    void use(Player player);

    boolean isUsed();
}