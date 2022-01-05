package catan.views.gui;

import catan.models.players.Player;

import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;

public class InformationView extends JPanel {
    public InformationView() {
        setLayout(new BoxLayout(this, BoxLayout.X_AXIS));

        JPanel cost = new JPanel();
        cost.setPreferredSize(new Dimension(200, 80));
        cost.setBorder(BorderFactory.createTitledBorder(new LineBorder(Color.BLACK, 2), "Costs"));

        JLabel road = new JLabel("Road: 1 lumber and 1 brick");
        road.setBorder(new LineBorder(Color.BLACK, 2));
        JLabel colony = new JLabel("Colony: 1 lumber, 1 brick, 1 grain and 1 wool");
        colony.setBorder(new LineBorder(Color.BLACK, 2));
        JLabel city = new JLabel("City: 2 grain and 2 ore");
        city.setBorder(new LineBorder(Color.BLACK, 2));

        cost.add(colony);
        cost.add(road);
        cost.add(city);

        add(cost);
        add(getScoreboard());
    }

    private JPanel getScoreboard() {
        var players = Player.getPlayers();
        JPanel scoreTable = new JPanel();
        scoreTable.setLayout(new GridLayout(players.size(), 2));
        scoreTable.setBorder(BorderFactory.createTitledBorder(new LineBorder(Color.BLACK, 2), "Score board"));
        scoreTable.setMaximumSize(new Dimension(80, 80));

        for(Player player : players) {
            scoreTable.add(new JLabel(player.getColor() + ": "));
            JLabel score = new JLabel(player.getPoints() + "");
            player.addChangeListener(() -> score.setText(player.getPoints() + ""));
            scoreTable.add(score);
        }
        return scoreTable;
    }
}