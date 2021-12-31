package catan.controllers;

import catan.models.players.Player;
import catan.models.tiles.Resource;
import catan.views.gui.CardDeckView;
import catan.views.gui.PlayerView;

import java.util.Random;

public class PlayerController {
    private static final PlayerController instance = new PlayerController();
    private final CardDeckView cardDeckView;
    private final PlayerView view;
    private Player model;

    private PlayerController() {
        view = new PlayerView();
        cardDeckView = new CardDeckView();
    }

    public static PlayerController getInstance() {
        return instance;
    }

    public PlayerView getView() {
        return view;
    }

    public CardDeckView getCardDeckView() {
        return cardDeckView;
    }

    public Player getModel() {
        return model;
    }

    public void setModel(Player model) {
        this.model = model;
        view.setPlayer(model);
        cardDeckView.setModel(model.getCards());
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
        model.addResource(resource);
    }
}