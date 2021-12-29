package catan.models.tiles;

import catan.models.Building;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.stream.Stream;

public class Tray implements Iterable<Tray.TrayCell> {
    private final LinkedList<LinkedList<TrayCell>> tray;
    private final int height;
    private final int width;

    Tray(LinkedList<Tile> tiles, int width) {
        tray = new LinkedList<>();
        LinkedList<TrayCell> tmp = new LinkedList<>();
        int i = 0, column = 0, row = 0;
        while(i < tiles.size()) {
            while(column < width) tmp.add(new TrayCell(row, column++, tiles.get(i++)));
            tray.add(tmp);
            tmp = new LinkedList<>();
            column = 0;
            ++row;
        }
        height = tray.size();
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public int getWidth() {
        return width;
    }

    public Tile get(int row, int column) {
        return tray.get(row).get(column).tile;
    }

    public boolean placeColony(int row, int column, Building.Colony colony, Tile.Vertex vertex) {
        if(get(row, column).placeColony(colony, vertex)) {
            switch(vertex) {
                case TopLeft -> {
                    if(row > 0) placeColony(row - 1, column, colony, Tile.Vertex.BottomLeft); // au dessut
                    else if(column > 0) get(row, column - 1).placeColony(colony, Tile.Vertex.TopRight); // a gauche
                }
                case TopRight -> {
                    if(column < width) placeColony(row, column + 1, colony, Tile.Vertex.TopLeft); // a droite
                    else if(row > 0) get(row - 1, column).placeColony(colony, Tile.Vertex.BottomRight); // au dessut
                }
                case BottomLeft -> {
                    if(column > 0) placeColony(row, column - 1, colony, Tile.Vertex.BottomRight); // a gauche
                    else if(row < height - 1)
                        get(row + 1, column).placeColony(colony, Tile.Vertex.TopLeft); // en dessout
                }
                case BottomRight -> {
                    if(row < height - 1) placeColony(row + 1, column, colony, Tile.Vertex.TopRight); // en dessout
                    else if(column < width) get(row - 1, column).placeColony(colony, Tile.Vertex.BottomLeft); // droite
                }
            }
            return true;
        }
        return false;
    }

    public boolean placeRoad(int row, int column, Building.Road road, Tile.Edge edge) {
        if(get(row, column).placeRoad(road, edge)) {
            switch(edge) {
                case Top -> {
                    if(row > 0) get(row - 1, column).placeRoad(road, Tile.Edge.Bottom);
                }
                case Left -> {
                    if(column > 0) get(row, column - 1).placeRoad(road, Tile.Edge.Right);
                }
                case Bottom -> {
                    if(row < height - 1) get(row + 1, column).placeRoad(road, Tile.Edge.Top);
                }
                case Right -> {
                    if(column < width) get(row, column + 1).placeRoad(road, Tile.Edge.Left);
                }
            }
            return true;
        }
        return false;
    }

    public boolean isEmpty(int row, int column, Tile.Vertex vertex) {
        return get(row, column).getColony(vertex) == null;
    }

    public boolean isEmpty(int row, int column, Tile.Edge edge) {
        return get(row, column).getRoad(edge) == null;
    }

    public Stream<TrayCell> stream() {
        return tray.stream().flatMap(LinkedList::stream);
    }

    @Override
    public Iterator<TrayCell> iterator() {
        return stream().iterator();
    }

    public class TrayCell {
        private final int row, column;
        private final Tile tile;

        private TrayCell(int row, int column, Tile tile) {
            this.row = row;
            this.column = column;
            this.tile = tile;
        }

        public boolean placeColony(Building.Colony colony, Tile.Vertex vertex) {
            return Tray.this.placeColony(row, column, colony, vertex);
        }

        public boolean placeRoad(Building.Road road, Tile.Edge edge) {
            return Tray.this.placeRoad(row, column, road, edge);
        }

        public Tile getTile() {
            return tile;
        }
    }
}
