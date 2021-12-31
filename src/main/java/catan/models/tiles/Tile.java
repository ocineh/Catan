package catan.models.tiles;

import catan.models.AbstractModel;
import catan.models.Building;

import java.util.Random;

/**
 * The type Tile represents a tile of a tray.
 *
 * @author Hocine
 */
public abstract class Tile extends AbstractModel {
    private static final Random random = new Random();
    private final Building.Colony[] colonies = new Building.Colony[4];
    private final Building.Road[] roads = new Building.Road[4];
    private final int number;

    /**
     * Instantiates a new Tile with a random number.
     */
    Tile() {
        this.number = random.nextInt(2, 13);
    }

    /**
     * Get the tile number.
     *
     * @return the number
     */
    public int getNumber() {
        return number;
    }

    /**
     * Place a colony on a vertex of the tile if it is empty.
     *
     * @param colony the colony to place
     * @param vertex the vertex on which to place the colony
     * @return if the colony has been placed
     */
    public boolean placeColony(Building.Colony colony, Vertex vertex) {
        if(colonies[vertex.pos] == null) {
            colonies[vertex.pos] = colony;
            changed();
            return true;
        }
        return false;
    }

    /**
     * Get the colony on one of the vertices.
     *
     * @param vertex a vertex of the tile
     * @return the colony on the vertex
     */
    public Building.Colony getColony(Vertex vertex) {
        return colonies[vertex.pos];
    }

    /**
     * Place a road on one edge of the tile if it is empty
     *
     * @param road the road to place
     * @param edge an edge of the tile
     * @return if the road has been placed
     */
    public boolean placeRoad(Building.Road road, Edge edge) {
        if(roads[edge.pos] == null) {
            roads[edge.pos] = road;
            changed();
            return true;
        }
        return false;
    }

    /**
     * Gets road on one of the edges.
     *
     * @param edge the edge
     * @return the road
     */
    public Building.Road getRoad(Edge edge) {
        return roads[edge.pos];
    }

    /**
     * Product resource associated with the tile.
     *
     * @return the resource produced
     */
    protected abstract Resource produce();

    /**
     * Each colony or city on the tile harvests a resource
     */
    public void harvest() {
        for(var c : colonies) if(c != null) c.harvest(produce());
        changed();
    }

    @Override
    public String toString() {
        return "Tile: " + number;
    }

    /**
     * The enum Edge represents the edges of a tile.
     */
    public enum Edge {
        Top(0), Left(1), Bottom(2), Right(3);
        private final int pos;

        Edge(int pos) {
            this.pos = pos;
        }

        @Override
        public String toString() {
            return switch(this) {
                case Top -> "Top";
                case Left -> "Left";
                case Bottom -> "Bottom";
                case Right -> "Right";
            };
        }
    }

    /**
     * The enum Vertex represents the vertices of a tile.
     */
    public enum Vertex {
        TopLeft(0), TopRight(1), BottomLeft(2), BottomRight(3);
        private final int pos;

        Vertex(int pos) {
            this.pos = pos;
        }

        @Override
        public String toString() {
            return switch(this) {
                case TopLeft -> "Top left";
                case TopRight -> "Top right";
                case BottomLeft -> "Bottom left";
                case BottomRight -> "Bottom right";
            };
        }
    }

    /**
     * The type Desert represents a tile that does not produce any resources.
     *
     * @author Hocine
     * @see Tile
     */
    public static class Desert extends Tile {
        @Override
        public int getNumber() {
            return -1;
        }

        @Override
        protected Resource produce() {
            return null;
        }
    }

    /**
     * The type Field represents a tile that produces grain.
     *
     * @author Hocine
     * @see Tile
     */
    public static class Field extends Tile {
        @Override
        protected Resource produce() {
            return Resource.Grain;
        }
    }

    /**
     * The type Forest represents a tile that produces lumber.
     *
     * @author Hocine
     * @see Tile
     */
    public static class Forest extends Tile {
        @Override
        protected Resource produce() {
            return Resource.Lumber;
        }
    }

    /**
     * The type Hill represents a tile that produces brick.
     *
     * @author Hocine
     * @see Tile
     */
    public static class Hill extends Tile {
        @Override
        protected Resource produce() {
            return Resource.Brick;
        }
    }

    /**
     * The type Mountain represents a tile that produces ore.
     *
     * @author Hocine
     * @see Tile
     */
    public static class Mountain extends Tile {
        @Override
        protected Resource produce() {
            return Resource.Ore;
        }
    }

    /**
     * The type Pasture represents a tile that produces Wool.
     *
     * @author Hocine
     * @see Tile
     */
    public static class Pasture extends Tile {
        @Override
        protected Resource produce() {
            return Resource.Wool;
        }
    }
}