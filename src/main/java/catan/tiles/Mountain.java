package catan.tiles;

public class Mountain extends Tile {
    Mountain() {
        super();
    }

    private static class Ore implements Resource {
        Ore() {}

        @Override
        public String getName() {
            return "Ore";
        }
    }

    @Override
    public Ore produce() {
        return new Ore();
    }
}