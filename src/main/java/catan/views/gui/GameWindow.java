package catan.views.gui;

import catan.controllers.GameController;
import catan.models.Game;

import javax.swing.*;
import java.awt.*;

public class GameWindow extends JFrame {
    public GameWindow() throws HeadlessException {
        GameController controller = GameController.getInstance();
        controller.setView(new GameView());
        controller.setModel(new Game());

        add(controller.getView(), BorderLayout.CENTER);

        setResizable(false);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        pack();
        setLocationRelativeTo(null);
    }
}
