package catan.models;

import catan.models.tiles.Resource;

import java.util.HashMap;
import java.util.LinkedList;

public class Player {
    private final Color color;
    private final HashMap<Card, Integer> cards;
    private final LinkedList<Building> buildings;
    private final HashMap<Resource, Integer> resources;

    public Player(Color color) {
        this.color = color;
        this.buildings = new LinkedList<>();
        this.cards = new HashMap<>();
        this.resources = new HashMap<>();
        for(Card card : Card.values()) cards.put(card, 0);
        for(Resource resource : Resource.values()) resources.put(resource, 0);
    }

    public Color getColor() {
        return color;
    }

    public int getPoints() {
        int n = 0;
        for(Card card : cards.keySet()) n += card.getPoints();
        for(Building building : buildings) n += building.getPoints();
        return n;
    }

    public void addResource(Resource resource) {
        resources.computeIfPresent(resource, (r, count) -> ++count);
    }

    private void use(Resource resource) {
        if(resource != null && resources.get(resource) > 0) resources.computeIfPresent(resource, (r, i) -> --i);
    }

    private void use(Resource... resources) {
        if(resources != null) for(Resource resource : resources) use(resource);
    }

    public boolean canBuildRoad() {
        return resources.get(Resource.Lumber) > 0 && resources.get(Resource.Brick) > 0;
    }

    public void buildRoad() {
        if(canBuildRoad()) {
            use(Resource.Lumber, Resource.Brick);
            buildings.add(new Building.Road(color));
        }
    }

    public boolean canBuildColony() {
        return resources.get(Resource.Brick) > 0 &&
                resources.get(Resource.Grain) > 0 &&
                resources.get(Resource.Wool) > 0 &&
                resources.get(Resource.Lumber) > 0;
    }

    public void buildColony() {
        if(canBuildColony()) {
            use(Resource.Lumber, Resource.Brick, Resource.Grain, Resource.Wool);
            buildings.add(new Building.Colony(color));
        }
    }

    public boolean canUpgradeColonyToCity(Building.Colony colony) {
        return buildings.contains(colony) && resources.get(Resource.Grain) > 1 && resources.get(Resource.Ore) > 2;
    }

    public void upgradeColonyToCity(Building.Colony colony) {
        if(canUpgradeColonyToCity(colony)) {
            use(Resource.Grain, Resource.Grain, Resource.Ore, Resource.Ore);
            buildings.remove(colony);
            buildings.add(new Building.City(color));
        }
    }

    public boolean canBuyCard() {
        return resources.get(Resource.Ore) > 0 && resources.get(Resource.Grain) > 0 && resources.get(Resource.Wool) > 0;
    }

    public void buyCard() {
        if(canBuyCard()) {
            use(Resource.Grain, Resource.Ore, Resource.Wool);
            cards.computeIfPresent(Card.pickRandom(), (c, count) -> ++count);
        }
    }
}