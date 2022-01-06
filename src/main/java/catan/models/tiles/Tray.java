package catan.models.tiles;

import catan.models.AbstractModel;
import catan.models.players.Building;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Tray extends AbstractModel implements Iterable<Tray.TrayCell> {
    private final LinkedList<LinkedList<TrayCell>> tray;
    private final int height;
    private final int width;
    private final Random random = new Random();

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

    private Tile get(int row, int column) {
        return tray.get(row).get(column).tile;
    }

    private void placeColony(int row, int column, Building.Colony colony, Tile.Vertex vertex) {
        if(get(row, column).placeColony(colony, vertex)) {
            get(row, column).changed();
            colony.setTile(get(row, column));
            switch(vertex) {
                case TopLeft:
                    if(row > 0) placeColony(row - 1, column, colony, Tile.Vertex.BottomLeft);
                    else if(column > 0) get(row, column - 1).placeColony(colony, Tile.Vertex.TopRight);
                    break;
                case TopRight:
                    if(column < width - 1) placeColony(row, column + 1, colony, Tile.Vertex.TopLeft);
                    else if(row > 0) get(row - 1, column).placeColony(colony, Tile.Vertex.BottomRight);
                    break;
                case BottomLeft:
                    if(column > 0) placeColony(row, column - 1, colony, Tile.Vertex.BottomRight);
                    else if(row < height - 1) get(row + 1, column).placeColony(colony, Tile.Vertex.TopLeft);
                    break;
                case BottomRight:
                    if(row < height - 1) placeColony(row + 1, column, colony, Tile.Vertex.TopRight);
                    else if(column < width - 1) get(row, column + 1).placeColony(colony, Tile.Vertex.BottomLeft);
                    break;
            }
        }
    }

    private void placeRoad(int row, int column, Building.Road road, Tile.Edge edge) {
        if(get(row, column).placeRoad(road, edge)) {
            get(row, column).changed();
            road.setTile(get(row, column));
            switch(edge) {
                case Top: if(row > 0) get(row - 1, column).placeRoad(road, Tile.Edge.Bottom);
                    break;
                case Left: if(column > 0) get(row, column - 1).placeRoad(road, Tile.Edge.Right);
                    break;
                case Bottom: if(row < height - 1) get(row + 1, column).placeRoad(road, Tile.Edge.Top);
                    break;
                case Right: if(column < width - 1) get(row, column + 1).placeRoad(road, Tile.Edge.Left);
                    break;
            }
        }
    }

    public Stream<TrayCell> stream() {
        return tray.stream().flatMap(LinkedList::stream);
    }

    @Override
    public Iterator<TrayCell> iterator() {
        return stream().iterator();
    }

    public void harvest(int number) {
        for(var cell: this) if(cell.getTile().getNumber() == number) cell.getTile().harvest();
    }

    public TrayCell getRandomTrayCell() {
        List<TrayCell> tiles = tray.stream().flatMap(LinkedList::stream).collect(Collectors.toList());
        return tiles.get(random.nextInt(tiles.size()));
    }

    public Tile getRandomTile() {
        return getRandomTrayCell().getTile();
    }

    public class TrayCell {
        private final int row, column;
        private final Tile tile;

        private TrayCell(int row, int column, Tile tile) {
            this.row = row;
            this.column = column;
            this.tile = tile;
        }

        public boolean isEmpty(Tile.Vertex vertex) {
            return tile.getColony(vertex) == null;
        }

        public boolean isEmpty(Tile.Edge edge) {
            return tile.getRoad(edge) == null;
        }

        public void placeColony(Building.Colony colony, Tile.Vertex vertex) {
            Tray.this.placeColony(row, column, colony, vertex);
        }

        public void placeRoad(Building.Road road, Tile.Edge edge) {
            Tray.this.placeRoad(row, column, road, edge);
        }

        public Tile getTile() {
            return tile;
        }
    }
}
