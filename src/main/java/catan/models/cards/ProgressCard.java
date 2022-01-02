package catan.models.cards;

import catan.models.exceptions.CardAlreadyUsed;
import catan.models.players.Player;
import catan.models.tiles.Resource;

public enum ProgressCard {
    Invention, Monopoly, BuildRoad;
    private boolean used = false;

    public void use(Player player) throws CardAlreadyUsed {
        if(!used) {
            used = true;
            switch(this) {
                case Invention: // TODO: Allow the player to choose two resources of their choice
                    break;
                case Monopoly: // TODO: Allow the player designates a type of resource and all players must give him the chosen type of resource that they have
                    break;
                case BuildRoad:
                    addRoad(player);
                    addRoad(player);
                    break;
            }
        } else throw new CardAlreadyUsed();
    }

    private void addRoad(Player player) {
        player.getInventory().addResource(Resource.Lumber);
        player.getInventory().addResource(Resource.Brick);
        player.buildRoad();
    }

    public boolean isUsed() {
        return used;
    }
}
