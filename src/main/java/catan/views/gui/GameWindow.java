package catan.views.gui;

import javax.swing.*;
import java.awt.*;

public class GameWindow extends JFrame {
    private static final GameWindow instance = new GameWindow();

    private GameWindow() throws HeadlessException {
        MenuView menuView = new MenuView();
        add(menuView, BorderLayout.CENTER);

        setResizable(false);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        pack();
        setLocationRelativeTo(null);
    }

    public static GameWindow getInstance() {
        return instance;
    }
}
