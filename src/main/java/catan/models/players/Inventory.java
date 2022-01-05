package catan.models.players;

import catan.models.AbstractModel;
import catan.models.tiles.Resource;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

public class Inventory extends AbstractModel {
    private final LinkedList<Building> buildings;
    private final HashMap<Resource, Integer> resources;

    Inventory(Player player) {
        this.buildings = new LinkedList<>();
        buildings.add(new Building.Colony(player));
        buildings.add(new Building.Colony(player));
        buildings.add(new Building.Road(player));
        buildings.add(new Building.Road(player));
        this.resources = new HashMap<>();
        for(Resource resource : Resource.values()) resources.put(resource, 0);
    }

    public int getPoints() {
        int n = 0;
        for(Building building : buildings) n += building.getPoints();
        return n;
    }

    public Building.Road getRoad() {
        for(Building building : buildings)
            if(building.isRoad() && building.isNotPlaced())
                return (Building.Road) building;
        return null;
    }

    public List<Building.Road> getRoads() {
        return buildings.stream().filter(Building::isRoad).map(building -> (Building.Road) building).collect(Collectors.toList());
    }

    public Building.Colony getColony() {
        for(Building building : buildings)
            if(building.isColony() && building.isNotPlaced())
                return (Building.Colony) building;
        return null;
    }

    public List<Building.Colony> getColonies() {
        return buildings.stream().filter(Building::isColony).map(building -> (Building.Colony) building).collect(Collectors.toList());
    }

    public Building.City getCity() {
        for(Building building : buildings)
            if(building.isCity() && building.isNotPlaced())
                return (Building.City) building;
        return null;
    }

    public List<Building.City> getCities() {
        return buildings.stream().filter(Building::isCity).map(building -> (Building.City) building).collect(Collectors.toList());
    }

    public Integer getResource(Resource resource) {
        return resources.get(resource);
    }

    public Integer removeAll(Resource resource) {
        Integer res = resources.get(resource);
        resources.computeIfPresent(resource, (key, value) -> value = 0);
        return res;
    }

    public void addResource(Resource resource) {
        resources.computeIfPresent(resource, (r, count) -> ++count);
        changed();
    }

    public void add(Building building) {
        buildings.add(building);
        changed();
    }

    private void use(Resource resource) {
        if(resource != null && resources.get(resource) > 0) {
            resources.computeIfPresent(resource, (r, i) -> --i);
            changed();
        }
    }

    void use(Resource... resources) {
        if(resources != null) for(Resource resource : resources) use(resource);
    }

    public boolean canBuildRoad() {
        return resources.get(Resource.Lumber) > 0 && resources.get(Resource.Brick) > 0;
    }

    public boolean canBuildColony() {
        return resources.get(Resource.Brick) > 0 && resources.get(Resource.Grain) > 0 && resources.get(Resource.Wool) > 0 && resources.get(Resource.Lumber) > 0;
    }

    public boolean canBuildCity() {
        return resources.get(Resource.Grain) > 1 && resources.get(Resource.Ore) > 2;
    }

    public boolean canBuyCard() {
        return resources.get(Resource.Ore) > 0 && resources.get(Resource.Grain) > 0 && resources.get(Resource.Wool) > 0;
    }
}
