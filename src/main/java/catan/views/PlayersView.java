package catan.views;

import catan.models.Player;
import catan.models.tiles.Tile;

import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;

public class PlayersView extends JPanel {
    private final Player[] players;
    private final InventoryView inventory;
    private final ActionView actionView;
    private int actual = 0;

    public PlayersView(Player[] players) {
        this.players = players;
        setBorder(BorderFactory.createTitledBorder(new LineBorder(Color.BLACK, 2), "Player"));
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        inventory = new InventoryView(players[actual]);
        add(inventory);

        actionView = new ActionView();
        add(actionView);
    }

    public void nextPlayer() {
        if(++actual == players.length) actual = 0;
        inventory.setPlayer(players[actual]);
    }

    private static class ActionView extends JPanel {
        private static final Font font = new Font("Default", Font.PLAIN, 10);
        private final JPanel build;
        private final JPanel placement;

        public ActionView() {
            setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

            build = getBuildPanel();
            placement = getPlacementPanel();

            add(build);
            add(placement);
        }

        private JPanel getBuildPanel() {
            JPanel build = new JPanel();
            build.setBorder(BorderFactory.createTitledBorder(new LineBorder(Color.BLACK, 2), "Build"));
            build.setPreferredSize(new Dimension(200, 125));

            Insets insets = new Insets(0, 2, 0, 2);
            JButton colony = new JButton("Colony");
            colony.setMargin(insets);
            build.add(colony);

            JButton city = new JButton("City");
            city.setMargin(insets);
            build.add(city);

            JButton road = new JButton("Road");
            road.setMargin(insets);
            build.add(road);
            return build;
        }

        private JPanel getPlacementPanel() {
            JPanel putting = new JPanel();
            putting.setBorder(BorderFactory.createTitledBorder(new LineBorder(Color.BLACK, 2), "Placement"));
            putting.setLayout(new BoxLayout(putting, BoxLayout.Y_AXIS));
            putting.add(getBuildingPlacementPanel("Colony", Tile.Vertex.values()));
            putting.add(getBuildingPlacementPanel("City", Tile.Vertex.values()));
            putting.add(getBuildingPlacementPanel("Road", Tile.Edge.values()));
            return putting;
        }

        private <T> JPanel getBuildingPlacementPanel(String title, T[] tmp) {
            JPanel panel = new JPanel();
            panel.setBorder(BorderFactory.createTitledBorder(new LineBorder(Color.BLACK, 2), title));
            panel.add(new JComboBox<>(tmp));
            panel.add(new JButton("Put"));
            return panel;
        }
    }
}