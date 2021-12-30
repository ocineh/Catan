package catan.views.gui;

import catan.models.Player;
import catan.models.tiles.Resource;
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

    public class InventoryView extends JPanel {
        private final JLabel[][] resources = new JLabel[][]{
                {new JLabel("Colonies"), new JLabel("0")},
                {new JLabel("Cities"), new JLabel("0")},
                {new JLabel("Roads"), new JLabel("0")},
                {new JLabel("Brick"), new JLabel("0")},
                {new JLabel("Ore"), new JLabel("0")},
                {new JLabel("Wool"), new JLabel("0")},
                {new JLabel("Lumber"), new JLabel("0")},
                {new JLabel("Grain"), new JLabel("0")}
        };
        private Player player;

        public InventoryView(Player player) {
            this.player = player;
            setBorder(BorderFactory.createTitledBorder(new LineBorder(Color.BLACK, 2), "Inventory"));
            setMaximumSize(new Dimension(200, 150));
            setLayout(new GridBagLayout());

            GridBagConstraints constraints = new GridBagConstraints();
            constraints.gridx = 1;
            for(int row = 0; row < 8; ++row) {
                constraints.gridy = row;
                add(getValueAt(row, 1), constraints);
            }

            constraints.gridx = 0;
            constraints.fill = GridBagConstraints.HORIZONTAL;
            constraints.weightx = 1.0;
            for(int row = 0; row < 8; ++row) {
                constraints.gridy = row;
                add(getValueAt(row, 0), constraints);
            }
        }

        public void setPlayer(Player player) {
            this.player = player;
        }

        public JLabel getValueAt(int rowIndex, int columnIndex) {
            return resources[rowIndex][columnIndex];
        }

        public void update() {
            resources[0][1].setText(player.getColonies().size() + "");
            resources[1][1].setText(player.getCities().size() + "");
            resources[2][1].setText(player.getRoads().size() + "");
            resources[3][1].setText(player.getResource(Resource.Brick) + "");
            resources[4][1].setText(player.getResource(Resource.Ore) + "");
            resources[5][1].setText(player.getResource(Resource.Wool) + "");
            resources[6][1].setText(player.getResource(Resource.Lumber) + "");
            resources[7][1].setText(player.getResource(Resource.Grain) + "");
        }
    }
}