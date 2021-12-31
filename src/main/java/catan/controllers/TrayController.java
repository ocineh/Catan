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

    public void placeColony(Player player, Tile.Vertex vertex) {
        TrayView.TrayCellView cellView = getSelected();
        if(cellView != null) {
            Tray.TrayCell cell = cellView.getCell();
            if(cell.isEmpty(vertex)) {
                Building.Colony colony = player.getColony();
                if(colony != null) cell.placeColony(colony, vertex);
            }
        }
    }

    public void placeCity(Player player, Tile.Vertex vertex) {
        TrayView.TrayCellView cellView = getSelected();
        if(cellView != null) {
            Tray.TrayCell cell = cellView.getCell();
            if(cell.isEmpty(vertex)) {
                Building.City city = player.getCity();
                if(city != null) cell.placeColony(city, vertex);
            }
        }
    }

    public void placeRoad(Player player, Tile.Edge edge) {
        TrayView.TrayCellView cellView = getSelected();
        if(cellView != null) {
            Tray.TrayCell cell = cellView.getCell();
            if(cell.isEmpty(edge)) {
                Building.Road road = player.getRoad();
                if(road != null) cell.placeRoad(road, edge);
            }
        }
    }
}