package catan.models.players;

import catan.models.AbstractModel;
import catan.models.tiles.Building;
import catan.models.tiles.Resource;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

public class Player extends AbstractModel {
    private final static LinkedList<Player> players = new LinkedList<>();
    private final Color color;
    private final CardDeck cards;
    private final LinkedList<Building> buildings;
    private final HashMap<Resource, Integer> resources;

    public Player(Color color) {
        this.color = color;
        this.buildings = new LinkedList<>();
        this.resources = new HashMap<>();
        this.cards = new CardDeck(this);
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

    public CardDeck getCards() {
        return cards;
    }

    public int getPoints() {
        int n = cards.getTotalPoints();
        for(Building building : buildings) n += building.getPoints();
        return n;
    }

    public Building.Road getRoad() {
        for(int i = 0; i < buildings.size(); i++) {
            if(buildings.get(i).isRoad()) {
                Building.Road road = (Building.Road) buildings.remove(i);
                changed();
                return road;
            }
        }
        return null;
    }

    public List<Building.Road> getRoads() {
        return buildings.stream().filter(Building::isRoad).map(building -> (Building.Road) building).collect(Collectors.toList());
    }

    public Building.Colony getColony() {
        for(int i = 0; i < buildings.size(); i++) {
            if(buildings.get(i).isColony()) {
                Building.Colony colony = (Building.Colony) buildings.remove(i);
                changed();
                return colony;
            }
        }
        return null;
    }

    public List<Building.Colony> getColonies() {
        return buildings.stream().filter(Building::isColony).map(building -> (Building.Colony) building).collect(Collectors.toList());
    }

    public Building.City getCity() {
        for(int i = 0; i < buildings.size(); i++) {
            if(buildings.get(i).isCity()) {
                Building.City city = (Building.City) buildings.remove(i);
                changed();
                return city;
            }
        }
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
        changed();
    }

    private void use(Resource resource) {
        if(resource != null && resources.get(resource) > 0) {
            resources.computeIfPresent(resource, (r, i) -> --i);
            changed();
        }
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
            changed();
        }
    }

    public void buildColony() {
        if(canBuildColony()) {
            use(Resource.Lumber, Resource.Brick, Resource.Grain, Resource.Wool);
            buildings.add(new Building.Colony(this));
            changed();
        }
    }

    public void buildCity() {
        if(canBuildCity()) {
            use(Resource.Grain, Resource.Grain, Resource.Ore, Resource.Ore);
            buildings.add(new Building.City(this));
            changed();
        }
    }

    public void buyCard() {
        if(canBuyCard()) {
            use(Resource.Grain, Resource.Ore, Resource.Wool);
            cards.addRandomCard();
            changed();
        }
    }
}