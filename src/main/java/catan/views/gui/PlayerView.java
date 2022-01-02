package catan.views.gui;

import catan.controllers.PlayerController;
import catan.controllers.TrayController;
import catan.models.cards.Card;
import catan.models.cards.Progress;
import catan.models.cards.VictoryPoint;
import catan.models.players.CardDeck;
import catan.models.players.Inventory;
import catan.models.players.Player;
import catan.models.tiles.Resource;
import catan.models.tiles.Tile;
import catan.views.View;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.ActionListener;
import java.util.LinkedList;
import java.util.function.Function;
import java.util.function.ToIntFunction;

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
        model.getInventory().addChangeListener(inventory::update);
        model.getCards().addChangeListener(cardDeckView::update);
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
            Inventory inventory = model.getInventory();
            resources[0][1].setText(inventory.getColonies().size() + "");
            resources[1][1].setText(inventory.getCities().size() + "");
            resources[2][1].setText(inventory.getRoads().size() + "");
            resources[3][1].setText(inventory.getResource(Resource.Brick) + "");
            resources[4][1].setText(inventory.getResource(Resource.Ore) + "");
            resources[5][1].setText(inventory.getResource(Resource.Wool) + "");
            resources[6][1].setText(inventory.getResource(Resource.Lumber) + "");
            resources[7][1].setText(inventory.getResource(Resource.Grain) + "");
        }
    }

    public class CardDeckView extends JPanel {
        private final LinkedList<CardsView> cardsViews;

        public CardDeckView() {
            setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
            setBorder(BorderFactory.createTitledBorder(new LineBorder(Color.BLACK, 2), "Card deck"));

            cardsViews = new LinkedList<>();
            cardsViews.add(new CardsView("Knight", CardDeck::countKnightCard));

            for(var c : Progress.values())
                cardsViews.add(new CardsView(c.toString(), (d) -> d.countProgressCard(c)));
            for(var c : VictoryPoint.values())
                cardsViews.add(new CardsView(c.toString(), (d) -> d.countVictoryPointCard(c)));
            cardsViews.forEach(this::add);
            setMinimumSize(new Dimension(200, 200));
        }

        private void update() {
            cardsViews.forEach(CardsView::update);
        }

        public class CardsView extends JPanel {
            private final JLabel label;
            private final String cardName;
            private final ToIntFunction<CardDeck> function;

            public CardsView(String cardName, ToIntFunction<CardDeck> function) {
                this.cardName = cardName;
                this.function = function;
                setLayout(new BorderLayout());
                Border border = new LineBorder(Color.BLACK, 2, true);
                setBorder(border);

                label = new JLabel(cardName + ": 0");
                label.setBorder(new EmptyBorder(1, 1, 1, 1));
                add(label, BorderLayout.CENTER);
            }

            private void update() {
                int value = model != null ? function.applyAsInt(model.getCards()) : -1;
                label.setText(cardName + ": " + value);
            }
        }
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