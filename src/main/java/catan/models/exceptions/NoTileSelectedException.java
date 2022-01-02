package catan.models.exceptions;

public class NoTileSelectedException extends Exception {
    public NoTileSelectedException() {
        super("No tile selected.");
    }
}