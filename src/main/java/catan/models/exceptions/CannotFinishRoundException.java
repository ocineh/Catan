package catan.models.exceptions;

public class CannotFinishRoundException  extends  Exception{
    public CannotFinishRoundException(String message) {
        super("You cannot finish your round because " + message);
    }
}