package catan.controllers;

import catan.models.players.Player;
import catan.views.gui.PlayerView;

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
}