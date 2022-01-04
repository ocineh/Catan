package catan.controllers;

import catan.models.Game;
import catan.views.gui.GameView;
import catan.views.gui.GameWindow;

public class GameController extends AbstractController<Game, GameView> {
    private static final GameController instance = new GameController();
    private final PlayerController playerController = PlayerController.getInstance();
    private final TrayController trayController = TrayController.getInstance();

    public GameController() {
    }

    public static GameController getInstance() {
        return instance;
    }

    public void nextRound() {
        if(model.getActualPlayer().isBot()) model.makeBotPlay();
        try {
            model.nextRound();
            if(model.getActualPlayer().isBot()) nextRound();
        } catch(Exception e) {GameWindow.getInstance().showError(e.getMessage());}
        playerController.setModel(model.getActualPlayer());
    }

    @Override
    public void setModel(Game model) {
        this.model = model;
        if(model.getActualPlayer().isBot()) nextRound();
        playerController.setModel(model.getActualPlayer());
        trayController.setModel(model.getTray());
    }

    @Override
    public void setView(GameView view) {
        this.view = view;
        if(model != null) view.setModel(model);
        playerController.setView(view.getPlayerView());
        trayController.setView(view.getTrayView());
    }

    public Game.Dice getBackThrownDice() {
        return model.getBackThrownDice();
    }

    public Game.Dice getDice() {
        return model.getDice();
    }
}