package catan.models.cards;

import catan.models.exceptions.CardAlreadyUsed;

public class KnightCard {
    private boolean used = false;

    public KnightCard() {
    }

    public void use() throws CardAlreadyUsed {
        if(!used) used = true;
        else throw new CardAlreadyUsed();
    }

    public boolean isUsed() {
        return used;
    }
}
