package catan.views.gui;

import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;

public class InformationSheetView extends JPanel {
    public InformationSheetView() {
        setPreferredSize(new Dimension(200, 80));
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