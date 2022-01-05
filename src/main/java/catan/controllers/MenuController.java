package catan.controllers;

import catan.models.Game;
import catan.models.Menu;
import catan.views.gui.InformationView;
import catan.views.gui.GameView;
import catan.views.gui.GameWindow;
import catan.views.gui.MenuView;

import java.awt.*;

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
        Game game = new Game(model.getPlayers(), model.getTray(), model.getScore());
        GameView gameView = new GameView();
        GameController.getInstance().setView(gameView);
        GameController.getInstance().setModel(game);
        GameWindow.getInstance().remove(view);
        GameWindow.getInstance().add(gameView);
        GameWindow.getInstance().add(new InformationView(), BorderLayout.SOUTH);
        GameWindow.getInstance().pack();
    }
}