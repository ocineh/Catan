package catan.models.exceptions;

public class CardAlreadyUsed extends Exception {
    public CardAlreadyUsed() {
        super("Card already used.");
    }
}