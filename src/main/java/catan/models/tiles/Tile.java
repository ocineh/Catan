package catan.models.tiles;

import catan.models.AbstractModel;
import catan.models.players.Building;
import catan.models.players.Thief;

import java.awt.*;
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
    private final Color color;
    private Thief thief;

    /**
     * Instantiates a new Tile with a random number.
     */
    Tile(Color color) {
        this.color = color;
        this.number = 2 + random.nextInt(11);
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
     * Get the thief if present on the tile
     *
     * @return The thief
     */
    public Thief getThief() {
        return thief;
    }

    /**
     * The thief is removed from the tile he is on and placed on this one
     *
     * @param thief The thief to place on the tile
     */
    public void setThief(Thief thief) {
        this.thief = thief;
    }

    /**
     * Gets the color of the tile
     *
     * @return a color
     */
    public Color getColor() {
        return color;
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
        if(thief == null) {
            for(var c : colonies) if(c != null) c.harvest(produce());
            changed();
        }
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
            switch(this) {
                case Top: return "Top";
                case Left: return "Left";
                case Bottom: return "Bottom";
                case Right: return "Right";
            }
            return null;
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
            switch(this) {
                case TopLeft: return "Top left";
                case TopRight: return "Top right";
                case BottomLeft: return "Bottom left";
                case BottomRight: return "Bottom right";
            }
            return null;
        }
    }

    /**
     * The type Desert represents a tile that does not produce any resources.
     *
     * @author Hocine
     * @see Tile
     */
    public static class Desert extends Tile {
        private static final Color color = Color.YELLOW;

        Desert() {
            super(color);
        }

        @Override
        public int getNumber() {
            return -1;
        }

        @Override
        protected Resource produce() {
            return null;
        }

        @Override
        public String toString() {
            return "Desert";
        }
    }

    /**
     * The type Field represents a tile that produces grain.
     *
     * @author Hocine
     * @see Tile
     */
    public static class Field extends Tile {
        private static final Color color = new Color(244, 255, 4);

        Field() {
            super(color);
        }

        @Override
        protected Resource produce() {
            return Resource.Grain;
        }

        @Override
        public String toString() {
            return "Field";
        }
    }

    /**
     * The type Forest represents a tile that produces lumber.
     *
     * @author Hocine
     * @see Tile
     */
    public static class Forest extends Tile {
        private static final Color color = new Color(0, 150, 0);

        Forest() {
            super(color);
        }

        @Override
        protected Resource produce() {
            return Resource.Lumber;
        }

        @Override
        public String toString() {
            return "Forest";
        }
    }

    /**
     * The type Hill represents a tile that produces brick.
     *
     * @author Hocine
     * @see Tile
     */
    public static class Hill extends Tile {
        private static final Color color = new Color(255, 106, 0);

        Hill() {
            super(color);
        }

        @Override
        protected Resource produce() {
            return Resource.Brick;
        }

        @Override
        public String toString() {
            return "Hill";
        }
    }

    /**
     * The type Mountain represents a tile that produces ore.
     *
     * @author Hocine
     * @see Tile
     */
    public static class Mountain extends Tile {
        private static final Color color = Color.DARK_GRAY;

        Mountain() {
            super(color);
        }

        @Override
        protected Resource produce() {
            return Resource.Ore;
        }

        @Override
        public String toString() {
            return "Mountain";
        }
    }

    /**
     * The type Pasture represents a tile that produces Wool.
     *
     * @author Hocine
     * @see Tile
     */
    public static class Pasture extends Tile {
        private static final Color color = new Color(0, 255, 0);

        Pasture() {
            super(color);
        }

        @Override
        protected Resource produce() {
            return Resource.Wool;
        }

        @Override
        public String toString() {
            return "Pasture";
        }
    }
}