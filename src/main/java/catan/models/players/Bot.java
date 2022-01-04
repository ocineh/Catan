package catan.models.players;

import java.util.Random;

public class Bot extends Player {
    public Bot(Color color) {
        super(color);
    }

    @Override
    public boolean isBot() {
        return true;
    }

    public Action getRandomAction() {
        Random random = new Random();
        Action[] actions = Action.values();
        return actions[random.nextInt(actions.length)];
    }

    public enum Action {
        BuildRoad, BuildCity, BuildColony, UseKnightCard, UseProgressCard, PlaceRoad, PlaceColony, PlaceCity
    }
}