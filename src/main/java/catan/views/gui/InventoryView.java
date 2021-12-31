package catan.views.gui;

import catan.models.players.Inventory;
import catan.models.tiles.Resource;
import catan.views.View;

import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;

class InventoryView extends JPanel implements View<Inventory> {
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
    private Inventory model;

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

    public void setModel(Inventory model) {
        this.model = model;
        model.addChangeListener(this::update);
    }

    private void update() {
        resources[0][1].setText(model.getColonies().size() + "");
        resources[1][1].setText(model.getCities().size() + "");
        resources[2][1].setText(model.getRoads().size() + "");
        resources[3][1].setText(model.getResource(Resource.Brick) + "");
        resources[4][1].setText(model.getResource(Resource.Ore) + "");
        resources[5][1].setText(model.getResource(Resource.Wool) + "");
        resources[6][1].setText(model.getResource(Resource.Lumber) + "");
        resources[7][1].setText(model.getResource(Resource.Grain) + "");
    }
}
