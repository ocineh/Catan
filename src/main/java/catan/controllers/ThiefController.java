package catan.controllers;

import catan.models.players.Thief;
import catan.models.tiles.Tray;
import catan.views.gui.GameWindow;
import catan.views.gui.ThiefView;

public class ThiefController extends AbstractController<Thief, ThiefView> {
    private static final ThiefController instance = new ThiefController();

    public ThiefController() {
        model = Thief.getInstance();
    }

    public static ThiefController getInstance() {
        return instance;
    }

    public boolean isMovable() {
        return model.isMovable();
    }

    public void move() {
        Tray.TrayCell cell = TrayController.getInstance().getSelected();
        if(cell != null) model.setTile(cell.getTile());
        else GameWindow.getInstance().showError("No tile selected.");
    }
}