package catan.views.gui;

import catan.controllers.GameController;
import catan.controllers.MenuController;
import catan.models.tiles.Tile;

import javax.swing.*;
import java.awt.*;

public class GameWindow extends JFrame {
    private static final GameWindow instance = new GameWindow();

    private GameWindow() throws HeadlessException {
        try {
            UIManager.setLookAndFeel(UIManager.getCrossPlatformLookAndFeelClassName());
        } catch(Exception ignored) {}

        switchToMenu();

        setResizable(false);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        pack();
        setLocationRelativeTo(null);
    }

    public static GameWindow getInstance() {
        return instance;
    }

    public void switchToGame() {
        getContentPane().removeAll();
        setLayout(new BorderLayout());
        GameController controller = GameController.getInstance();
        add(controller.getView(), BorderLayout.CENTER);
        add(new InformationView(), BorderLayout.SOUTH);
        pack();
    }

    public void switchToMenu() {
        getContentPane().removeAll();
        setLayout(new BorderLayout());
        add(MenuController.getInstance().getView(), BorderLayout.CENTER);
        pack();
    }

    public Tile.Vertex askVertex(String message) {
        return (Tile.Vertex) JOptionPane.showInputDialog(this, message, "Choose a vertex", JOptionPane.PLAIN_MESSAGE, null, Tile.Vertex.values(), Tile.Vertex.values()[0]);
    }

    public Tile.Edge askEdge(String message) {
        return (Tile.Edge) JOptionPane.showInputDialog(this, message, "Choose a edge", JOptionPane.PLAIN_MESSAGE, null, Tile.Edge.values(), Tile.Edge.values()[0]);
    }

    public void showError(String message) {
        JOptionPane.showMessageDialog(this, message, "Error", JOptionPane.ERROR_MESSAGE);
    }
}
