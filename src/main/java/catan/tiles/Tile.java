package catan.tiles;

import catan.buildings.Building;

import java.util.*;

public abstract class Tile {
    private static final Random random = new Random();
    private final Building.Colony[] colonies = new Building.Colony[4];
    private final Building.Road[] roads = new Building.Road[4];
    private final int number;

    Tile() {
        this.number = random.nextInt(2, 13);
    }

    public int getNumber() {
        return number;
    }

    public Iterator<Building.Colony> getColonies() {
        return Arrays.stream(colonies).filter(Objects::nonNull).iterator();
    }

    public Iterator<Building.Road> getRoads() {
        return Arrays.stream(roads).filter(Objects::nonNull).iterator();
    }

    public enum Edge {
        Top(0), Left(1), Bottom(2), Right(3);
        private final int pos;

        Edge(int pos) {this.pos = pos;}
    }

    public boolean placeBuilding(Building.Colony colony, Edge edge) {
        if(colonies[edge.pos] == null) {
            colonies[edge.pos] = colony;
            return true;
        }
        return false;
    }

    public Building.Colony getBuilding(Edge edge) {
        return colonies[edge.pos];
    }

    public enum Vertex {
        TopLeft(0), TopRight(1), BottomLeft(2), BottomRight(3);
        private final int pos;

        Vertex(int pos) {this.pos = pos;}
    }

    public boolean placeRoad(Building.Road road, Vertex vertex) {
        if(roads[vertex.pos] == null) {
            roads[vertex.pos] = road;
            return true;
        }
        return false;
    }

    public Building.Road getRoad(Vertex vertex) {
        return roads[vertex.pos];
    }

    interface Resource {
        String getName();
    }

    public abstract Resource produce();
}