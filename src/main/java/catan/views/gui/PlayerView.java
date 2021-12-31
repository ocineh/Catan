package catan.views.gui;

import catan.controllers.PlayerController;
import catan.controllers.TrayController;
import catan.models.players.Player;
import catan.models.tiles.Tile;
import catan.views.View;

import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.ActionListener;
import java.util.function.Function;

public class PlayerView extends JPanel implements View<Player> {
    private final InventoryView inventory;
    private final ActionView actionView;
    private Player model;

    public PlayerView() {
        setBorder(BorderFactory.createTitledBorder(new LineBorder(Color.BLACK, 2), "Player"));
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        inventory = new InventoryView();
        add(inventory);

        actionView = new ActionView();
        add(actionView);
    }

    public Player getModel() {
        return model;
    }

    @Override
    public void setModel(Player model) {
        this.model = model;
        inventory.setModel(model.getInventory());
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
                if(getModel() != null) {
                    Tile.Vertex vertex = (Tile.Vertex) comboBox.getSelectedItem();
                    TrayController.getInstance().placeColony(getModel(), vertex);
                }
            }));

            putting.add(getBuildingPlacementPanel("City", Tile.Vertex.values(), comboBox -> e -> {
                if(getModel() != null) {
                    Tile.Vertex vertex = (Tile.Vertex) comboBox.getSelectedItem();
                    TrayController.getInstance().placeCity(getModel(), vertex);
                }
            }));

            putting.add(getBuildingPlacementPanel("Road", Tile.Edge.values(), comboBox -> e -> {
                if(getModel() != null) {
                    Tile.Edge edge = (Tile.Edge) comboBox.getSelectedItem();
                    TrayController.getInstance().placeRoad(getModel(), edge);
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
}