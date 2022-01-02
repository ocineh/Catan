package catan.models.exceptions;

public class NoCardAvailableException extends Exception {
    public NoCardAvailableException() {
        super("No card available.");
    }
}