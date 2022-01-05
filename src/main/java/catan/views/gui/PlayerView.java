package catan.views.gui;

import catan.controllers.GameController;
import catan.controllers.PlayerController;
import catan.models.cards.ProgressCard;
import catan.models.cards.VictoryPointCard;
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
        setBorder(BorderFactory.createTitledBorder(new LineBorder(Color.BLACK, 2), model.isBot() ? "Bot" : "Player", 0, 0, new Font("Default", Font.BOLD, 15), model
                .getColor().toAwtColor()));
        this.model = model;
        inventory.update();
        cardDeckView.update();
        model.getInventory().addChangeListener(inventory::update);
        model.getCards().addChangeListener(cardDeckView::update);

        model.addChangeListener(() -> actionView.buildColony.setEnabled(model.getInventory().canBuildColony()));
        model.addChangeListener(() -> actionView.buildCity.setEnabled(model.getInventory().canBuildCity()));
        model.addChangeListener(() -> actionView.buildRoad.setEnabled(model.getInventory().canBuildRoad()));
        model.addChangeListener(() -> actionView.placeColony.setEnabled(model.getInventory().getColony() != null));
        model.addChangeListener(() -> actionView.placeCity.setEnabled(model.getInventory().getCity() != null));
        model.addChangeListener(() -> actionView.placeRoad.setEnabled(model.getInventory().getRoad() != null));
        model.changed();
    }

    public Resource askResourcePopup() {
        return (Resource) JOptionPane.showInputDialog(GameWindow.getInstance(), "Choose a resource type", "Brick", JOptionPane.PLAIN_MESSAGE, null, Resource.values(), Resource.values()[0]);
    }

    private static class ActionView extends JPanel {
        private final JButton buildColony = new JButton("Colony");
        private final JButton buildCity = new JButton("City");
        private final JButton buildRoad = new JButton("Road");
        private final JButton placeRoad = new JButton("Put a road");
        private final JButton placeColony = new JButton("Put a colony");
        private final JButton placeCity = new JButton("Put a city");

        public ActionView() {
            setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

            JPanel build = getBuildPanel();
            JPanel placement = getPlacementPanel();
            JPanel cardAction = getCardAction();

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

            panel.add(ThiefView.getMoveThiefButton());

            JButton finished = new JButton("Finished");
            finished.addActionListener(e -> {
                if(GameController.getInstance().getDice() != null) GameController.getInstance().nextRound();
                else
                    JOptionPane.showMessageDialog(GameWindow.getInstance(), "You did not throw the dice", "Error", JOptionPane.ERROR_MESSAGE);
            });
            panel.add(finished);
            add(panel);
        }

        private JPanel getBuildPanel() {
            JPanel build = new JPanel();
            build.setBorder(BorderFactory.createTitledBorder(new LineBorder(Color.BLACK, 2), "Build"));

            Insets insets = new Insets(0, 0, 0, 0);
            buildColony.addActionListener(e -> PlayerController.getInstance().buildColony());
            buildColony.setMargin(insets);
            build.add(buildColony);

            buildCity.addActionListener(e -> PlayerController.getInstance().buildCity());
            buildCity.setMargin(insets);
            build.add(buildCity);

            buildRoad.addActionListener(e -> PlayerController.getInstance().buildRoad());
            buildRoad.setMargin(insets);
            build.add(buildRoad);
            return build;
        }

        private JPanel getPlacementPanel() {
            JPanel putting = new JPanel();
            putting.setBorder(BorderFactory.createTitledBorder(new LineBorder(Color.BLACK, 2), "Placement"));

            placeRoad.addActionListener(e -> PlayerController.getInstance().placeRoad());
            putting.add(placeRoad);

            placeColony.addActionListener(e -> PlayerController.getInstance().placeColony());
            putting.add(placeColony);

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
                    } catch(NoTileSelectedException | NoCardAvailableException exception) {
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
}