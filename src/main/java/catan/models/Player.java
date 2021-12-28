package catan.models;

import catan.models.tiles.Resource;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

public class Player {
    private final static LinkedList<Player> players = new LinkedList<>();
    private final Color color;
    private final CardDeck cards;
    private final LinkedList<Building> buildings;
    private final HashMap<Resource, Integer> resources;

    public Player(Color color) {
        this.color = color;
        this.buildings = new LinkedList<>();
        this.resources = new HashMap<>();
        this.cards = new CardDeck();
        for(Resource resource : Resource.values()) resources.put(resource, 0);
        players.add(this);
    }

    public static Player playerWithTheBiggestArmy() {
        if(players.size() == 0) return null;
        Player player = null;
        boolean tmp = false;
        for(Player p : players) {
            if(player == null) player = p;
            else if(p.cards.countKnightCard() > player.cards.countKnightCard()) {
                player = p;
                tmp = false;
            } else if(p.cards.countKnightCard() == player.cards.countKnightCard()) {
                tmp = true;
            }
        }
        if(!tmp) return player;
        return null;
    }

    public Color getColor() {
        return color;
    }

    public int getPoints() {
        int n = cards.getTotalPoints();
        for(Building building : buildings) n += building.getPoints();
        return n;
    }

    public Building.Road getRoad() {
        for(Building building : buildings)
            if(building instanceof Building.Road road) return road;
        return null;
    }

    public List<Building.Road> getRoads() {
        return buildings.stream().filter(Building::isRoad).map(building -> (Building.Road) building).collect(Collectors.toList());
    }

    public Building.Colony getColony() {
        for(Building building : buildings) if(building instanceof Building.Colony colony) return colony;
        return null;
    }

    public List<Building.Colony> getColonies() {
        return buildings.stream().filter(Building::isColony).map(building -> (Building.Colony) building).collect(Collectors.toList());
    }

    public Building.City getCity() {
        for(Building building : buildings) if(building instanceof Building.City city) return city;
        return null;
    }

    public List<Building.City> getCities() {
        return buildings.stream().filter(Building::isCity).map(building -> (Building.City) building).collect(Collectors.toList());
    }

    public Integer getResource(Resource resource) {
        return resources.get(resource);
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

    public boolean canBuildColony() {
        return resources.get(Resource.Brick) > 0 && resources.get(Resource.Grain) > 0 && resources.get(Resource.Wool) > 0 && resources.get(Resource.Lumber) > 0;
    }

    public boolean canBuildCity() {
        return resources.get(Resource.Grain) > 1 && resources.get(Resource.Ore) > 2;
    }

    public boolean canBuyCard() {
        return resources.get(Resource.Ore) > 0 && resources.get(Resource.Grain) > 0 && resources.get(Resource.Wool) > 0;
    }

    public void buildRoad() {
        if(canBuildRoad()) {
            use(Resource.Lumber, Resource.Brick);
            buildings.add(new Building.Road(this));
        }
    }

    public void buildColony() {
        if(canBuildColony()) {
            use(Resource.Lumber, Resource.Brick, Resource.Grain, Resource.Wool);
            buildings.add(new Building.Colony(this));
        }
    }

    public void buildCity() {
        if(canBuildCity()) {
            use(Resource.Grain, Resource.Grain, Resource.Ore, Resource.Ore);
            buildings.add(new Building.City(this));
        }
    }

    public void buyCard() {
        if(canBuyCard()) {
            use(Resource.Grain, Resource.Ore, Resource.Wool);
            cards.addRandomCard();
        }
    }
}