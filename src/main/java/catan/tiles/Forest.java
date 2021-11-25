package catan.tiles;

public class Forest extends Tile {
    Forest() {
        super();
    }

    private static class Lumber implements Resource {
        Lumber() {}

        @Override
        public String getName() {
            return "Lumber";
        }
    }

    @Override
    public Lumber produce() {
        return new Lumber();
    }
}