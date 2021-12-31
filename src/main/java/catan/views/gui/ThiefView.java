package catan.views.gui;

import javax.swing.*;
import java.awt.*;

public class ThiefView extends JPanel {
    private final static ThiefView instance = new ThiefView();

    private ThiefView() {
        setSize(10, 10);
        setBackground(Color.BLACK);
    }

    public static ThiefView getInstance() {
        return instance;
    }
}