package catan.controllers;

import catan.models.Building;
import catan.models.Player;
import catan.models.tiles.Tile;
import catan.models.tiles.Tray;
import catan.models.tiles.TrayBuilder;
import catan.views.gui.TrayView;

public class TrayController {
    private static final TrayController instance = new TrayController();

    private final Tray model;
    private final TrayView view;

    private TrayController() {
        model = TrayBuilder.buildDefault();
        view = new TrayView(model);
    }

    public static TrayController getInstance() {
        return instance;
    }

    public TrayView getView() {
        return view;
    }

    public TrayView.TrayCellView getSelected() {
        return view.getSelected();
    }

    public void update() {
        view.repaint();
    }

    public void placeColony(Player player, Tile.Vertex vertex) {
        Building.Colony colony = player.getColony();
        TrayView.TrayCellView cellView = getSelected();
        if(colony != null && cellView != null) {
            cellView.getCell().placeColony(colony, vertex);
            update();
        }
    }

    public void placeRoad(Player player, Tile.Edge edge) {
        Building.Road road = player.getRoad();
        TrayView.TrayCellView cellView = getSelected();
        if(road != null && cellView != null) {
            cellView.getCell().placeRoad(road, edge);
            update();
        }
    }
}