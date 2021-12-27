package catan.views;

import catan.models.tiles.Tray;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.util.List;

public class TrayView extends JPanel {
    public TrayView(Tray tray) {
        setLayout(new GridLayout(tray.getHeight(), tray.getWidth()));
        setBorder(new EmptyBorder(5, 5, 5, 5));
        List<TileView> tileViews = tray.getTiles().stream().map(trayCell -> new TileView(trayCell.getTile())).toList();
        for(TileView cell : tileViews) add(cell);
        setPreferredSize(new Dimension(tray.getWidth() * 100, tray.getHeight() * 100));
    }
}