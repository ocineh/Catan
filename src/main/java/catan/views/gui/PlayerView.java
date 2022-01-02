package catan.views.gui;

import catan.controllers.PlayerController;
import catan.controllers.TrayController;
import catan.models.cards.Card;
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
    private final CardDeckView cardDeckView;
    private Player model;

    public PlayerView() {
        setBorder(BorderFactory.createTitledBorder(new LineBorder(Color.BLACK, 2), "Player"));
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        setPreferredSize(new Dimension(150, 100));

        JTabbedPane tabbedPane = new JTabbedPane();

        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        inventory = new InventoryView();
        cardDeckView = new CardDeckView();
        panel.add(inventory);
        panel.add(cardDeckView);
        tabbedPane.add(panel, "Inventory");

        actionView = new ActionView();
        tabbedPane.add(actionView, "Action");
        add(tabbedPane);
    }

    public Player getModel() {
        return model;
    }

    @Override
    public void setModel(Player model) {
        this.model = model;
        inventory.setModel(model.getInventory());
        cardDeckView.setModel(model.getCards());
    }

    private class ActionView extends JPanel {
        private final JPanel build;
        private final JPanel placement;
        private final JPanel cardAction;

        public ActionView() {
            setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

            build = getBuildPanel();
            placement = getPlacementPanel();
            cardAction = getCardAction();

            add(build);
            add(placement);
            add(cardAction);
        }

        private JPanel getBuildPanel() {
            JPanel build = new JPanel();
            build.setBorder(BorderFactory.createTitledBorder(new LineBorder(Color.BLACK, 2), "Build"));

            Insets insets = new Insets(0, 0, 0, 0);
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

        private JPanel getCardAction() {
            JPanel action = new JPanel();
            action.setBorder(BorderFactory.createTitledBorder(new LineBorder(Color.BLACK, 2), "Cards"));

            JComboBox<String> comboBox = new JComboBox<>(Card.values());
            action.add(comboBox);
            JButton use = new JButton("use");
            use.addActionListener(e -> {
                String cardName = (String) comboBox.getSelectedItem();
                if(cardName != null) {
                    switch(cardName) {
                        case "BuildRoad" : break;
                        case "Invention" : break;
                        case "Knight" : break;
                        case "Monopoly" : break;
                        default : break;
                    }
                }
            });
            action.add(use);

            JButton buy = new JButton("buy");
            buy.addActionListener(e -> PlayerController.getInstance().buyCard());
            action.add(buy);
            return action;
        }
    }
}