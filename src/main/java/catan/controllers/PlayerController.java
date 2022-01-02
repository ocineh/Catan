package catan.controllers;

import catan.models.players.Player;
import catan.models.tiles.Resource;
import catan.views.gui.PlayerView;

import java.util.Random;

public class PlayerController extends AbstractController<Player, PlayerView> {
    private static final PlayerController instance = new PlayerController();

    private PlayerController() {
    }

    public static PlayerController getInstance() {
        return instance;
    }

    public void buildRoad() {
        model.buildRoad();
    }

    public void buildColony() {
        model.buildColony();
    }

    public void buildCity() {
        model.buildCity();
    }

    public void buyCard() {
        model.buyCard();
    }

    public void addRandomResource() {
        Random random = new Random();
        int index = random.nextInt(Resource.values().length);
        Resource resource = Resource.values()[index];
        model.getInventory().addResource(resource);
    }
}