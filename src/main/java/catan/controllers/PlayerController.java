package catan.controllers;

import catan.models.cards.ProgressCard;
import catan.models.exceptions.CardAlreadyUsed;
import catan.models.exceptions.NoCardAvailableException;
import catan.models.exceptions.NoTileSelectedException;
import catan.models.players.Player;
import catan.models.players.Thief;
import catan.models.tiles.Resource;
import catan.models.tiles.Tile;
import catan.models.tiles.Tray;
import catan.views.gui.GameWindow;
import catan.views.gui.PlayerView;

public class PlayerController extends AbstractController<Player, PlayerView> {
    private static final PlayerController instance = new PlayerController();

    private PlayerController() {
    }

    public static PlayerController getInstance() {
        return instance;
    }

    public void placeRoad() {
        Tile.Edge edge = GameWindow.getInstance().askEdge("Choose a edge to place the road");
        TrayController.getInstance().placeRoad(model, edge);
    }

    public void placeColony() {
        Tile.Vertex vertex = GameWindow.getInstance().askVertex("Choose a vertex to place the colony");
        TrayController.getInstance().placeColony(model, vertex);
    }

    public void placeCity() {
        Tile.Vertex vertex = GameWindow.getInstance().askVertex("Choose a vertex to place the city");
        TrayController.getInstance().placeCity(model, vertex);
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

    public void useProgressCard(ProgressCard card) throws NoCardAvailableException {
        model.getCards().useProgressCard(card);
        switch(card) {
            case Invention:
                model.getInventory().addResource(view.askResourcePopup());
                model.getInventory().addResource(view.askResourcePopup());
                break;
            case Monopoly:
                Resource resource = view.askResourcePopup();
                var number = Player.getPlayers().stream().map(player -> player.getInventory().removeAll(resource))
                                   .mapToInt(i -> i).sum();
                while(number-- > 0) model.getInventory().addResource(resource);
                break;
            case BuildRoad:
                model.getInventory().addResource(Resource.Lumber);
                model.getInventory().addResource(Resource.Brick);
                model.buildRoad();
                model.getInventory().addResource(Resource.Lumber);
                model.getInventory().addResource(Resource.Brick);
                model.buildRoad();
                break;
        }
    }
}