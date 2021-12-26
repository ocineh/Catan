package catan.models;

public enum Color {
    Blue, White, Red;

    public java.awt.Color toAwtColor() {
        return switch(this) {
            case Blue -> java.awt.Color.BLUE;
            case White -> java.awt.Color.WHITE;
            case Red -> java.awt.Color.RED;
        };
    }
}
