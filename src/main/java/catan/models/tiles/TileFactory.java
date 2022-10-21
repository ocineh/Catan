package catan.models.tiles;

import java.awt.*;
import java.util.Optional;

public class TileFactory {
    private TileFactory() {}

    private static Tile createCustom(
            String name, Color color, Resource resource, int number
    ) {
        return new Tile(color) {
            @Override
            public int getNumber() {return number;}

            @Override
            protected Optional<Resource> produce() {
                return Optional.ofNullable(resource);
            }

            @Override
            public String toString() {
                return name;
            }
        };
    }

    /**
     * Create a desert tile that does not produce any resource.
     *
     * @return The desert tile
     */
    public static Tile createDesert() {
        return createCustom("Desert", Color.GRAY, null, -1);
    }

    /**
     * Create a field tile that produces grain.
     *
     * @return The field tile
     */
    public static Tile createField() {
        return createCustom(
                "Field",
                new Color(244, 255, 4),
                Resource.Grain,
                4
        );
    }

    /**
     * Create a forest tile that produces lumber.
     *
     * @return The forest tile
     */
    public static Tile createForest() {
        return createCustom(
                "Forest",
                new Color(0, 150, 0),
                Resource.Lumber,
                11
        );
    }

    /**
     * Create a hill tile that produces brick.
     *
     * @return The hill tile
     */
    public static Tile createHill() {
        return createCustom(
                "Hill",
                new Color(255, 106, 0),
                Resource.Brick,
                5
        );
    }

    /**
     * Create a mountain tile that produces ore.
     *
     * @return The mountain tile
     */
    public static Tile createMountain() {
        return createCustom(
                "Mountain",
                Color.DARK_GRAY,
                Resource.Ore,
                9
        );
    }

    /**
     * Create a pasture tile that produces wool.
     *
     * @return The pasture tile
     */
    public static Tile createPasture() {
        return createCustom(
                "Pasture",
                new Color(0, 255, 0),
                Resource.Wool,
                10
        );
    }
}
