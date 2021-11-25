package catan.tiles;

public class Field extends Tile {
    Field() {
        super();
    }

    private static class Grain implements Resource {
        Grain() {}

        @Override
        public String getName() {
            return "Grain";
        }
    }

    @Override
    public Grain produce() {
        return new Grain();
    }
}