package catan.views.gui;

import catan.controllers.listeners.ClickedMouseListener;
import catan.models.tiles.Tray;
import catan.views.View;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.util.List;

public class TrayView extends JPanel implements View<Tray> {
    private Tray model;
    private TrayCellView selected = null;

    public TrayView(Tray model) {
        this.model = model;
        setLayout(new GridLayout(model.getHeight(), model.getWidth()));
        setBorder(new EmptyBorder(5, 5, 5, 5));
        List<TrayCellView> tileViews = model.stream().map(TrayCellView::new).toList();
        for(TrayCellView cell : tileViews) add(cell);
        setPreferredSize(new Dimension(model.getWidth() * 100, model.getHeight() * 100));
    }

    public TrayCellView getSelected() {
        return selected;
    }

    @Override
    public void setModel(Tray model) {
        this.model = model;
    }

    public class TrayCellView extends JPanel {
        private final Tray.TrayCell cell;
        private final TileView tileView;

        public TrayCellView(Tray.TrayCell cell) {
            this.cell = cell;
            this.tileView = new TileView(cell.getTile());

            setLayout(new BorderLayout());
            add(tileView, BorderLayout.CENTER);

            TrayCellView view = this;
            addMouseListener(new ClickedMouseListener() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    if(selected != null) selected.tileView.setBackground(selected.tileView.getModel().getColor());
                    if(selected != view) {
                        selected = view;
                        selected.tileView.setBackground(Color.CYAN);
                    } else selected = null;
                }
            });
        }

        public TileView getTileView() {
            return tileView;
        }

        public Tray.TrayCell getCell() {
            return cell;
        }
    }
}