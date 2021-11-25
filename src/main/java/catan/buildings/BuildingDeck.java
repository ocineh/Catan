package catan.buildings;

import catan.Color;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Predicate;

public class BuildingDeck {
    private final Color color;
    private final HashMap<String, Integer> buildings;

    public BuildingDeck(Color color) {
        this.color = color;
        buildings = new HashMap<>();
        buildings.put("Colony", 2);
        buildings.put("City", 0);
        buildings.put("Road", 2);
    }

    public int countBuilding() {
        return buildings.values().stream().mapToInt(i -> i).sum();
    }

    private int countBuilding(Predicate<Map.Entry<String, Integer>> predicate) {
        return buildings.entrySet().stream().filter(predicate)
                .mapToInt(Map.Entry::getValue).sum();
    }

    public int countColony() {
        return countBuilding(a -> a.getKey().equals("Colony"));
    }

    public int countCity() {
        return countBuilding(a -> a.getKey().equals("City"));
    }

    public int countRoad() {
        return countBuilding(a -> a.getKey().equals("Road"));
    }

    public Building.Colony getColony() {
        if(buildings.get("Colony") == 0) return null;
        buildings.compute("Colony", ((c, integer) -> integer--));
        return new Building.Colony(color);
    }

    public Building.City getCity() {
        if(buildings.get("City") == 0) return null;
        buildings.compute("City", ((c, integer) -> integer--));
        return new Building.City(color);
    }

    public Building.Road getRoad() {
        if(buildings.get("Road") == 0) return null;
        buildings.compute("Road", ((c, integer) -> integer--));
        return new Building.Road(color);
    }
}