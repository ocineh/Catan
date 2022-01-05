package catan.controllers;

import catan.models.Menu;
import catan.views.gui.GameView;
import catan.views.gui.GameWindow;
import catan.views.gui.MenuView;

public class MenuController extends AbstractController<Menu, MenuView> {
    private static final MenuController instance = new MenuController();

    private MenuController() {
        setModel(new Menu());
        setView(new MenuView(getModel()));
    }

    public static MenuController getInstance() {
        return instance;
    }

    public void start() {
        GameView gameView = new GameView();
        GameController.getInstance().setView(gameView);
        GameController.getInstance().setModel(model.toGame());
        GameWindow.getInstance().switchToGame();
    }
}