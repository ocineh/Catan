package catan.views.gui;

import catan.controllers.PlayerController;
import catan.controllers.TrayController;
import catan.models.Player;
import catan.models.tiles.Resource;
import catan.models.tiles.Tile;

import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.ActionListener;
import java.util.function.Function;

public class PlayerView extends JPanel {
    private final InventoryView inventory;
    private final ActionView actionView;
    private Player player;

    public PlayerView() {
        setBorder(BorderFactory.createTitledBorder(new LineBorder(Color.BLACK, 2), "Player"));
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        inventory = new InventoryView();
        add(inventory);

        actionView = new ActionView();
        add(actionView);
    }

    public Player getPlayer() {
        return player;
    }

    public void setPlayer(Player player) {
        this.player = player;
        player.addChangeListener(this::update);
    }

    private void update() {
        inventory.update();
    }

    private class ActionView extends JPanel {
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
            colony.addActionListener(e -> PlayerController.getInstance().buildColony());
            colony.setMargin(insets);
            build.add(colony);

            JButton city = new JButton("City");
            city.addActionListener(e -> PlayerController.getInstance().buildCity());
            city.setMargin(insets);
            build.add(city);

            JButton road = new JButton("Road");
            road.addActionListener(e -> PlayerController.getInstance().buildRoad());
            road.setMargin(insets);
            build.add(road);

            JButton randomResource = new JButton("random resource");
            randomResource.addActionListener(e -> PlayerController.getInstance().addRandomResource());
            randomResource.setMargin(insets);
            build.add(randomResource);
            return build;
        }

        private JPanel getPlacementPanel() {
            JPanel putting = new JPanel();
            putting.setBorder(BorderFactory.createTitledBorder(new LineBorder(Color.BLACK, 2), "Placement"));
            putting.setLayout(new BoxLayout(putting, BoxLayout.Y_AXIS));

            putting.add(getBuildingPlacementPanel("Colony", Tile.Vertex.values(), comboBox -> e -> {
                if(getPlayer() != null) {
                    Tile.Vertex vertex = (Tile.Vertex) comboBox.getSelectedItem();
                    TrayController.getInstance().placeColony(getPlayer(), vertex);
                }
            }));

            putting.add(getBuildingPlacementPanel("City", Tile.Vertex.values(), comboBox -> e -> {
                if(getPlayer() != null) {
                    Tile.Vertex vertex = (Tile.Vertex) comboBox.getSelectedItem();
                    TrayController.getInstance().placeColony(getPlayer(), vertex);
                }
            }));

            putting.add(getBuildingPlacementPanel("Road", Tile.Edge.values(), comboBox -> e -> {
                if(getPlayer() != null) {
                    Tile.Edge edge = (Tile.Edge) comboBox.getSelectedItem();
                    TrayController.getInstance().placeRoad(getPlayer(), edge);
                }
            }));
            return putting;
        }

        private <T> JPanel getBuildingPlacementPanel(String title, T[] tmp, Function<JComboBox<T>, ActionListener> function) {
            JPanel panel = new JPanel();
            panel.setBorder(BorderFactory.createTitledBorder(new LineBorder(Color.BLACK, 2), title));
            JComboBox<T> comboBox = new JComboBox<>(tmp);
            panel.add(comboBox);
            JButton button = new JButton("Put");
            button.addActionListener(function.apply(comboBox));
            panel.add(button);
            return panel;
        }
    }

    private class InventoryView extends JPanel {
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

        public InventoryView() {
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

        public JLabel getValueAt(int rowIndex, int columnIndex) {
            return resources[rowIndex][columnIndex];
        }

        private void update() {
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