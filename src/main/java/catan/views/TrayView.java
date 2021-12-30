package catan.views;

import catan.models.tiles.Tray;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.util.List;

public class TrayView extends JPanel {
    private final Tray tray;

    public TrayView(Tray tray) {
        this.tray = tray;
        setLayout(new GridLayout(tray.getHeight(), tray.getWidth()));
        setBorder(new EmptyBorder(5, 5, 5, 5));
        List<TrayCellView> tileViews = tray.stream().map(TrayCellView::new).toList();
        for(TrayCellView cell : tileViews) add(cell);
        setPreferredSize(new Dimension(tray.getWidth() * 100, tray.getHeight() * 100));
    }

    public static class TrayCellView extends JPanel {
        private final Tray.TrayCell cell;
        private final TileView tileView;

        public TrayCellView(Tray.TrayCell cell) {
            this.cell = cell;
            this.tileView = new TileView(cell.getTile());

            setLayout(new BorderLayout());
            add(tileView, BorderLayout.CENTER);
        }

        public TileView getTileView() {
            return tileView;
        }

        public Tray.TrayCell getCell() {
            return cell;
        }
    }
}