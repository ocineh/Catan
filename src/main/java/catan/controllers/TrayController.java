package catan.controllers;

import catan.models.players.Building;
import catan.models.players.Player;
import catan.models.tiles.Tile;
import catan.models.tiles.Tray;
import catan.models.tiles.TrayBuilder;
import catan.views.gui.TrayView;

public class TrayController extends AbstractController<Tray, TrayView> {
    private static final TrayController instance = new TrayController();

    private TrayController() {
        model = TrayBuilder.buildDefault();
        view = new TrayView(model);
    }

    public static TrayController getInstance() {
        return instance;
    }

    public TrayView.TrayCellView getSelected() {
        return view.getSelected();
    }

    public void placeColony(Player player, Tile.Vertex vertex) {
        TrayView.TrayCellView cellView = getSelected();
        if(cellView != null) {
            Tray.TrayCell cell = cellView.getCell();
            if(cell.isEmpty(vertex)) {
                Building.Colony colony = player.getInventory().getColony();
                if(colony != null) cell.placeColony(colony, vertex);
            }
        }
    }

    public void placeCity(Player player, Tile.Vertex vertex) {
        TrayView.TrayCellView cellView = getSelected();
        if(cellView != null) {
            Tray.TrayCell cell = cellView.getCell();
            if(cell.isEmpty(vertex)) {
                Building.City city = player.getInventory().getCity();
                if(city != null) cell.placeColony(city, vertex);
            }
        }
    }

    public void placeRoad(Player player, Tile.Edge edge) {
        TrayView.TrayCellView cellView = getSelected();
        if(cellView != null) {
            Tray.TrayCell cell = cellView.getCell();
            if(cell.isEmpty(edge)) {
                Building.Road road = player.getInventory().getRoad();
                if(road != null) cell.placeRoad(road, edge);
            }
        }
    }
}