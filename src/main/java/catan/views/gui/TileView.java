package catan.views.gui;

import catan.models.Building;
import catan.models.tiles.Tile;

import javax.swing.*;
import javax.swing.border.AbstractBorder;
import java.awt.*;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Line2D;
import java.awt.geom.Rectangle2D;

public class TileView extends JPanel {
    private final Tile tile;

    public TileView(Tile tile) {
        tile.addChangeListener(this::repaint);
        this.tile = tile;
        setBackground(tile.getColor());
        add(new JLabel(tile.toString(), SwingConstants.CENTER), CENTER_ALIGNMENT);
        setBorder(new TileBorder());
        if(tile.getThief() != null) add(ThiefView.getInstance());
    }

    public Tile getTile() {
        return tile;
    }

    private class TileBorder extends AbstractBorder {
        @Override
        public void paintBorder(Component c, Graphics g, int x, int y, int width, int height) {
            if(g instanceof Graphics2D g2d) {
                int a = x + width, b = y + height;
                Point topLeft = new Point(x, y);
                Point topRight = new Point(a, y);
                Point bottomLeft = new Point(x, b);
                Point bottomRight = new Point(a, b);

                paintRoads(g2d, topLeft, topRight, bottomLeft, bottomRight);
                paintColonies(g2d, topLeft, topRight, bottomLeft, bottomRight);
            }
        }

        private void paintRoads(Graphics2D g2d, Point topLeft, Point topRight, Point bottomLeft, Point bottomRight) {
            g2d.setStroke(new BasicStroke(10));
            paintRoad(g2d, Tile.Edge.Top, topLeft, topRight);
            paintRoad(g2d, Tile.Edge.Bottom, bottomLeft, bottomRight);
            paintRoad(g2d, Tile.Edge.Right, topRight, bottomRight);
            paintRoad(g2d, Tile.Edge.Left, topLeft, bottomLeft);
        }

        private void paintRoad(Graphics2D g2d, Tile.Edge edge, Point p1, Point p2) {
            Line2D line = new Line2D.Float(p1, p2);
            Building.Road road = tile.getRoad(edge);
            g2d.setPaint(road != null ? road.getColor() : Color.BLACK);
            g2d.draw(line);
        }

        private void paintColonies(Graphics2D g2d, Point topLeft, Point topRight, Point bottomLeft, Point bottomRight) {
            g2d.setStroke(new BasicStroke(5));
            paintColony(g2d, Tile.Vertex.TopLeft, topLeft);
            paintColony(g2d, Tile.Vertex.TopRight, topRight);
            paintColony(g2d, Tile.Vertex.BottomLeft, bottomLeft);
            paintColony(g2d, Tile.Vertex.BottomRight, bottomRight);
        }

        private void paintColony(Graphics2D g2d, Tile.Vertex vertex, Point point) {
            Building.Colony colony = tile.getColony(vertex);
            if(colony != null) {
                Shape shape;
                if(colony instanceof Building.City) shape = new Rectangle2D.Float(point.x - 10, point.y - 10, 20, 20);
                else shape = new Ellipse2D.Float(point.x - 10, point.y - 10, 20, 20);
                g2d.setPaint(Color.BLACK);
                g2d.draw(shape);
                g2d.setPaint(colony.getColor());
                g2d.fill(shape);
            }
        }
    }
}