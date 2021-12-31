package catan.models.cards;

import catan.models.players.Player;
import catan.models.tiles.Resource;

public enum Progress implements Card, UsableCard {
    Invention, Monopoly, BuildRoad;
    private boolean used = false;

    @Override
    public boolean isProgress() {
        return true;
    }

    @Override
    public void use(Player player) {
        if(!used) {
            used = true;
            switch(this) {
                case Invention -> {
                    // TODO: Allow the player to choose two resources of their choice
                }
                case Monopoly -> {
                    // TODO: Allow the player designates a type of resource and all players must give him the chosen type of resource that they have
                }
                case BuildRoad -> {
                    addRoad(player);
                    addRoad(player);
                }
            }
        }
    }

    private void addRoad(Player player) {
        player.getInventory().addResource(Resource.Lumber);
        player.getInventory().addResource(Resource.Brick);
        player.buildRoad();
    }

    @Override
    public boolean isUsed() {
        return used;
    }
}
