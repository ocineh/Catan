package catan.controllers;

import catan.models.Player;
import catan.models.tiles.Resource;
import catan.views.gui.PlayerView;

import java.util.Random;

public class PlayerController {
    private static final PlayerController instance = new PlayerController();
    private final PlayerView view;
    private Player model;

    private PlayerController() {
        view = new PlayerView();
    }

    public static PlayerController getInstance() {
        return instance;
    }

    public PlayerView getView() {
        return view;
    }

    public Player getModel() {
        return model;
    }

    public void setModel(Player model) {
        this.model = model;
        view.setPlayer(model);
    }

    public void buildRoad() {
        model.buildRoad();
        view.update();
    }

    public void buildColony() {
        model.buildColony();
        view.update();
    }

    public void buildCity() {
        model.buildCity();
        view.update();
    }

    public void addRandomResource(){
        Random random = new Random();
        int index = random.nextInt(Resource.values().length);
        Resource resource = Resource.values()[index];
        model.addResource(resource);
        view.update();
    }
}