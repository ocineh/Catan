package catan.controllers;

import catan.models.Game;
import catan.models.Menu;
import catan.views.gui.GameView;
import catan.views.gui.GameWindow;
import catan.views.gui.MenuView;

public class MenuController extends AbstractController<Menu, MenuView> {
    private static final MenuController instance = new MenuController();

    private MenuController() {
        setView(new MenuView());
        setModel(new Menu());
    }

    public static MenuController getInstance() {
        return instance;
    }

    public void start(String gameMode) {
        Game game = new Game(model.getPlayers(), model.getTray(gameMode));
        GameView gameView = new GameView();
        GameController.getInstance().setView(gameView);
        GameController.getInstance().setModel(game);
        GameWindow.getInstance().remove(view);
        GameWindow.getInstance().add(gameView);
        GameWindow.getInstance().pack();
    }
}