package catan.controllers;

import catan.models.exceptions.CardAlreadyUsed;
import catan.models.exceptions.NoCardAvailableException;
import catan.models.exceptions.NoTileSelectedException;
import catan.models.players.Player;
import catan.models.players.Thief;
import catan.models.tiles.Tray;
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

    public void useKnightCard() throws NoTileSelectedException, NoCardAvailableException, CardAlreadyUsed {
        Tray.TrayCell cell = TrayController.getInstance().getSelected();
        if(cell == null) throw new NoTileSelectedException();
        model.getCards().useKnightCard();
        Thief.getInstance().setTile(cell.getTile());
    }
}