package catan.views.gui;

import catan.controllers.GameController;
import catan.controllers.PlayerController;
import catan.models.cards.ProgressCard;
import catan.models.cards.VictoryPointCard;
import catan.models.exceptions.CardAlreadyUsed;
import catan.models.exceptions.NoCardAvailableException;
import catan.models.exceptions.NoTileSelectedException;
import catan.models.players.CardDeck;
import catan.models.players.Inventory;
import catan.models.players.Player;
import catan.models.tiles.Resource;
import catan.views.View;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.util.LinkedList;
import java.util.function.ToIntFunction;

public class PlayerView extends JPanel implements View<Player> {
    private final InventoryView inventory;
    private final ActionView actionView;
    private final CardDeckView cardDeckView;
    private Player model;

    public PlayerView() {
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        setPreferredSize(new Dimension(200, 100));

        JTabbedPane tabbedPane = new JTabbedPane();

        actionView = new ActionView();
        tabbedPane.add(actionView, "Action");

        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        inventory = new InventoryView();
        cardDeckView = new CardDeckView();
        panel.add(inventory);
        panel.add(cardDeckView);
        tabbedPane.add(panel, "Inventory");
        add(tabbedPane);
    }

    public Player getModel() {
        return model;
    }

    @Override
    public void setModel(Player model) {
        this.model = model;
        inventory.update();
        cardDeckView.update();
        model.getInventory().addChangeListener(inventory::update);
        model.getCards().addChangeListener(cardDeckView::update);
    }

    public Resource askResourcePopup() {
        return (Resource) JOptionPane.showInputDialog(
                GameWindow.getInstance(),
                "Choose a resource type",
                "Brick",
                JOptionPane.PLAIN_MESSAGE,
                null,
                Resource.values(),
                Resource.values()[0]
        );
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

            for(var c : ProgressCard.values())
                cardsViews.add(new CardsView(c.toString(), (d) -> d.countProgressCard(c)));
            for(var c : VictoryPointCard.values())
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

            JPanel panel = new JPanel();
            panel.setBorder(BorderFactory.createTitledBorder(new LineBorder(Color.BLACK, 2), "Round"));
            JButton button = new JButton("Throw dice");
            button.addActionListener(e -> {
                JLabel label = new JLabel(GameController.getInstance().getBackThrownDice().toString());
                JOptionPane.showMessageDialog(GameWindow.getInstance(), label, "", JOptionPane.QUESTION_MESSAGE);
            });
            panel.add(button, Component.CENTER_ALIGNMENT);

            JButton finished = new JButton("Finished");
            finished.addActionListener(e -> {
                if(GameController.getInstance().getDice() != null) GameController.getInstance().nextRound();
                else JOptionPane.showMessageDialog(GameWindow.getInstance(), "You did not throw the dice", "Error", JOptionPane.ERROR_MESSAGE);
            });
            panel.add(finished);
            add(panel);
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
            return build;
        }

        private JPanel getPlacementPanel() {
            JPanel putting = new JPanel();
            putting.setBorder(BorderFactory.createTitledBorder(new LineBorder(Color.BLACK, 2), "Placement"));

            JButton placeRoad = new JButton("Put a road");
            placeRoad.addActionListener(e -> PlayerController.getInstance().placeRoad());
            putting.add(placeRoad);

            JButton placeColony = new JButton("Put a colony");
            placeColony.addActionListener(e -> PlayerController.getInstance().placeColony());
            putting.add(placeColony);

            JButton placeCity = new JButton("Put a city");
            placeCity.addActionListener(e -> PlayerController.getInstance().placeCity());
            putting.add(placeCity);
            return putting;
        }

        private JPanel getCardAction() {
            JPanel action = new JPanel();
            action.setPreferredSize(new Dimension(200, 35));
            action.setBorder(BorderFactory.createTitledBorder(new LineBorder(Color.BLACK, 2), "Cards"));

            JComboBox<String> comboBox = new JComboBox<>(new String[]{"Build road", "Invention", "Monopoly", "Knight"});
            action.add(comboBox);
            JButton use = new JButton("use");
            use.addActionListener(e -> {
                String cardName = (String) comboBox.getSelectedItem();
                if(cardName != null) {
                    try {
                        switch(cardName) {
                            case "Build road": PlayerController.getInstance().useProgressCard(ProgressCard.BuildRoad);
                                break;
                            case "Invention": PlayerController.getInstance().useProgressCard(ProgressCard.Invention);
                                break;
                            case "Monopoly": PlayerController.getInstance().useProgressCard(ProgressCard.Monopoly);
                                break;
                            case "Knight": PlayerController.getInstance().useKnightCard();
                                break;
                            default: break;
                        }
                    } catch(NoTileSelectedException | NoCardAvailableException | CardAlreadyUsed exception) {
                        showErrorPopup(exception.getMessage());
                    }
                }
            });
            action.add(use);

            JButton buy = new JButton("buy");
            buy.addActionListener(e -> PlayerController.getInstance().buyCard());
            action.add(buy);
            return action;
        }

        private void showErrorPopup(String message) {
            JOptionPane.showMessageDialog(GameWindow.getInstance(), message, "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}