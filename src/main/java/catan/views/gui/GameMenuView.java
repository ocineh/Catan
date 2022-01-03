package catan.views.gui;

import catan.controllers.GameController;

import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;

public class GameMenuView extends JPanel {
    public GameMenuView() {
        setBorder(BorderFactory.createTitledBorder(new LineBorder(Color.BLACK, 2), "Menu"));
        add(new InformationSheetView());

        JButton button = new JButton("Finished");
        button.addActionListener(e -> {
            if(GameController.getInstance().getDice() != null) GameController.getInstance().nextRound();
            else JOptionPane.showMessageDialog(GameWindow.getInstance(), "You did not throw the dice", "Error", JOptionPane.ERROR_MESSAGE);
        });
        add(button);
    }

    public static class InformationSheetView extends JPanel {
        public InformationSheetView() {
            setPreferredSize(new Dimension(305, 80));
            setBorder(BorderFactory.createTitledBorder(new LineBorder(Color.BLACK, 2), "Costs"));

            JLabel road = new JLabel("Road: 1 lumber and 1 brick");
            road.setBorder(new LineBorder(Color.BLACK, 2));
            JLabel colony = new JLabel("Colony: 1 lumber, 1 brick, 1 grain and 1 wool");
            colony.setBorder(new LineBorder(Color.BLACK, 2));
            JLabel city = new JLabel("City: 2 grain and 2 ore");
            city.setBorder(new LineBorder(Color.BLACK, 2));

            add(colony);
            add(road);
            add(city);
        }
    }
}