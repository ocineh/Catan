package catan.buildings;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Predicate;

public class BuildingDeck {
    private final HashMap<String, Integer> buildings;

    public BuildingDeck() {
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
        return new Building.Colony();
    }

    public Building.City getCity() {
        if(buildings.get("City") == 0) return null;
        buildings.compute("City", ((c, integer) -> integer--));
        return new Building.City();
    }

    public Building.Road getRoad() {
        if(buildings.get("Road") == 0) return null;
        buildings.compute("Road", ((c, integer) -> integer--));
        return new Building.Road();
    }
}