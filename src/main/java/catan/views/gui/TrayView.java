package catan.views.gui;

import catan.controllers.listeners.ClickedMouseListener;
import catan.models.tiles.Tray;
import catan.views.View;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.util.List;
import java.util.stream.Collectors;

public class TrayView extends JPanel implements View<Tray> {
    private Tray model;
    private TrayCellView selected = null;

    public TrayView() {
        setBorder(new EmptyBorder(5, 5, 5, 5));
    }

    public Tray.TrayCell getSelected() {
        return selected != null ? selected.cell : null;
    }

    @Override
    public void setModel(Tray model) {
        this.model = model;
        setLayout(new GridLayout(model.getHeight(), model.getWidth()));
        for(var c : getComponents()) remove(c);
        List<TrayCellView> tileViews = model.stream().map(TrayCellView::new).collect(Collectors.toList());
        for(TrayCellView cell : tileViews) add(cell);
        setPreferredSize(new Dimension(model.getWidth() * 100, model.getHeight() * 100));
    }

    public class TrayCellView extends JPanel {
        private final Tray.TrayCell cell;
        private final TileView tileView;

        public TrayCellView(Tray.TrayCell cell) {
            this.cell = cell;
            this.tileView = new TileView(cell.getTile());

            setLayout(new BorderLayout());
            add(tileView, BorderLayout.CENTER);

            addMouseListener((ClickedMouseListener) e -> {
                if(selected != null) selected.tileView.setBackground(selected.tileView.getModel().getColor());
                if(selected != this) {
                    selected = this;
                    selected.tileView.setBackground(Color.CYAN);
                } else selected = null;
            });
        }
    }
}