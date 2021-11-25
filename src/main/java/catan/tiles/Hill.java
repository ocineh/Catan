package catan.tiles;

public class Hill extends Tile {
    Hill() {
        super();
    }

    private static class Brick implements Resource {
        Brick() {}

        @Override
        public String getName() {
            return "Brick";
        }
    }

    @Override
    public Brick produce() {
        return new Brick();
    }
}