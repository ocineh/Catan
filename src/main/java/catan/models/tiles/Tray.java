package catan.models.tiles;

import catan.models.Building;

import java.util.LinkedList;

public class Tray {
    private final LinkedList<LinkedList<Tile>> board;
    private final int height;
    private final int width;

    Tray(LinkedList<Tile> tiles, int width) {
        board = new LinkedList<>();
        LinkedList<Tile> tmp = new LinkedList<>();
        int i = 0, j = 0;
        while(i < tiles.size()) {
            while(j++ < width) tmp.add(tiles.get(i++));
            board.add(tmp);
            tmp = new LinkedList<>();
            j = 0;
        }
        height = board.size();
        this.width = width-1;
    }

    public int getHeight() {
        return height;
    }

    public int getWidth() {
        return width;
    }

    public Tile get(int row, int column) {
        return board.get(row).get(column);
    }

    public boolean placeColony(int row, int column, Building.Colony colony, Tile.Vertex vertex) {
        if(get(row, column).placeColony(colony, vertex)) {
            switch(vertex) {
                case TopLeft -> {
                    if(row > 0) placeColony(row - 1, column, colony, Tile.Vertex.BottomLeft); // au dessut
                    else if(column > 0) get(row, column - 1).placeColony(colony, Tile.Vertex.TopRight); // a gauche
                }
                case TopRight -> {
                    if(column < width-1) placeColony(row, column + 1, colony, Tile.Vertex.TopLeft); // a droite
                    else if(row > 0) get(row - 1, column).placeColony(colony, Tile.Vertex.BottomRight); // au dessut
                }
                case BottomLeft -> {
                    if(column > 0) placeColony(row, column - 1, colony, Tile.Vertex.BottomRight); // a gauche
                    else if(row < height-1) get(row + 1, column).placeColony(colony, Tile.Vertex.TopLeft); // en dessout
                }
                case BottomRight -> {
                    if(row < height-1) placeColony(row + 1, column, colony, Tile.Vertex.TopRight); // en dessout
                    else if(column < width-1) get(row - 1, column).placeColony(colony, Tile.Vertex.BottomLeft); // droite
                }
            }
            return true;
        }
        return false;
    }

    public boolean placeRoad(int row, int column, Building.Road road, Tile.Edge edge) {
        boolean result = get(row, column).placeRoad(road, edge);
        switch(edge) {
            case Top -> --row;
            case Left -> --column;
            case Bottom -> ++row;
            case Right -> ++column;
        }
        get(row, column).placeRoad(road, edge);
        return result;
    }

    public boolean isEmpty(int row, int column, Tile.Vertex vertex) {
        return get(row, column).getColony(vertex) == null;
    }

    public boolean isEmpty(int row, int column, Tile.Edge edge) {
        return get(row, column).getRoad(edge) == null;
    }
}
