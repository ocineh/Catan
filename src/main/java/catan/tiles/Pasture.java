package catan.tiles;

public class Pasture extends Tile {
    Pasture() {
        super();
    }

    private static class Wool implements Resource {
        Wool() {}

        @Override
        public String getName() {
            return "Wool";
        }
    }

    @Override
    public Wool produce() {
        return new Wool();
    }
}