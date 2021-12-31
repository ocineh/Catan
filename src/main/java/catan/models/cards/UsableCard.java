package catan.models.cards;

import catan.models.Player;

public interface UsableCard {
    void use(Player player);

    boolean isUsed();
}